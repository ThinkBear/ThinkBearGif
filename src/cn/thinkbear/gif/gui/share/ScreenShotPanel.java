package cn.thinkbear.gif.gui.share;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiPara;
import cn.thinkbear.gif.utils.ConfigUtils;
import cn.thinkbear.gif.vo.Frame;
import cn.thinkbear.gif.vo.GifConfig;
import cn.thinkbear.gif.vo.UiConfig;

/**
 * 屏幕录制面板<br>
 * 
 * <img src="doc-files/ScreenShotPanel.png" alt="屏幕录制面板" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class ScreenShotPanel extends BaseView {
	private JPanel mainPanel = null;
	private JLabel waterMark = null;
	private OnCallback onCallback = null;
	private Thread task = null;
	private UiConfig config = null;
	public ScreenShotPanel() {
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.waterMark = new JLabel();
	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.waterMark, BorderLayout.SOUTH);

	}

	@Override
	public void doSet() {
		this.mainPanel.setOpaque(false);
		this.waterMark.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 5));
		this.waterMark.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	/**
	 * 更新视图操作，主要更新边框样式、水印样式
	 */
	public void updateUI() {
		if (this.config != null) {
			this.mainPanel.setBorder(BorderFactory.createLineBorder(this.config.getScreenShotBorderColor(),
					this.config.getScreenShotBorderSize()));
			this.waterMark.setForeground(this.config.getWaterMarkColor());
			this.waterMark.setFont(new Font(GuiPara.FONT_NAME, Font.PLAIN, this.config.getWaterMarkSize()));
			this.waterMark.setVisible(this.config.isShowWateMark());
			this.waterMark.setText(this.config.getWaterMark());
		}
	}

	public UiConfig getUiConfig() {
		return config;
	}

	public void setUiConfig(UiConfig config) {
		this.config = config;
		this.updateUI();
	}
	/**
	 * 开始屏幕录制
	 */
	public void doStartScreenShot() {
		if (this.onCallback == null) {
			return;
		}
		if (this.task != null && this.task.getState() != Thread.State.TERMINATED) {
			return;
		}
		try {
			this.task = new Thread(new Task());
			this.task.start();
		} catch (AWTException e) {
			JOptionPane.showMessageDialog(this.mainPanel, "获取屏幕图片失败，生成Gif失败!");
		}
	}
	/**
	 * 停止屏幕录制
	 */
	public void doStopScreenShot() {
		if (this.task != null) {
			this.task.interrupt();
		}
	}

	public void setOnCallback(OnCallback onCallback) {
		this.onCallback = onCallback;
	}
	/**
	 * 取得录制的区域矩形:x,y,width,height
	 * @return 矩形对象
	 */
	private Rectangle getFrameSize() {
		int space = 0;
		if (!ConfigUtils.getInstance().isHasScreenShotBorder()) {//是否含边框录制
			space = ConfigUtils.getInstance().getScreenShotBorderSize();
		}
		Rectangle rect = new Rectangle();
		Point point = this.mainPanel.getLocationOnScreen();
		rect.setBounds(point.x + space, point.y + space, this.mainPanel.getWidth() - space * 2,
				this.mainPanel.getHeight() - space * 2);
		return rect;
	}
	/**
	 * 线程任务：屏幕录制操作<br>
	 * 
	 * @see Robot
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月24日
	 */
	private class Task implements Runnable {
		private Robot robot = null;
		private Rectangle rect = null;
		public Task() throws AWTException {
			this.robot = new Robot();
			this.rect = getFrameSize();
		}
		

		@Override
		public void run() {
			onCallback.screenShotStart();
			String name = String.valueOf(System.currentTimeMillis());//取得当前时间截，用于设置gif和帧图片父目录名称
			File saveDir = new File(ConfigUtils.getInstance().getGifSaveDir(), name);
			saveDir.mkdirs();
			int scale = ConfigUtils.getInstance().getScreenShotScale();
			int height = 0;
			int width = 0;
			if (scale != 100 && scale >= ConfigUtils.MIN_SCREEN_SHOT_SCALE
					&& scale <= ConfigUtils.MAX_SCREEN_SHOT_SCALE) {//是否进行放大缩小操作
				height = this.rect.height * scale / 100;
				width = this.rect.width * scale / 100;
			}
			boolean isScale = height > 0 && width > 0;//如果高和宽大于0，则表示进行放大缩小操作
			boolean flag = true;
			List<Frame> data = new ArrayList<Frame>();
			int index = 0;
			while (flag) {
				BufferedImage bfImage = robot.createScreenCapture(this.rect);
				if (isScale) {
					Image image = bfImage.getScaledInstance(width, height, BufferedImage.SCALE_FAST);
					bfImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					bfImage.getGraphics().drawImage(image, 0, 0, width, height, null);
				}
				File output = new File(saveDir, index + ".jpg");
				try {
					ImageIO.write(bfImage, "jpg", output);
					Frame frame = new Frame();
					frame.setFile(output);
					data.add(frame);
					index++;
				} catch (IOException e) {
				}

				try {
					Thread.sleep(ConfigUtils.getInstance().getScreenShotDelay());
				} catch (InterruptedException e) {
					flag = false;
				}
			}
			GifConfig gifConfig = new GifConfig();
			gifConfig.setWidth(isScale ? width : rect.width);
			gifConfig.setHeight(isScale ? height : rect.height);
			gifConfig.setData(data);
			gifConfig.setFrameDir(saveDir);
			gifConfig.setOutputFile(new File(ConfigUtils.getInstance().getGifSaveDir(), name + ".gif"));
			onCallback.screenshotFinish(gifConfig);
		}

	}
	/**
	 * 回调接口，开始和结束的事件通知
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月24日
	 */
	public interface OnCallback {
		/**
		 * 开始屏幕截图
		 */
		public void screenShotStart();
		/**
		 * 屏幕截图结束
		 * @param gifConfig 截图后，封装的数据对象
		 */
		public void screenshotFinish(GifConfig gifConfig);

	}

	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}

}

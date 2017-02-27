package cn.thinkbear.gif.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.thinkbear.gif.gui.BaseDialog;
import cn.thinkbear.gif.gui.dialog.lookgif.LookGifDialog;
import cn.thinkbear.gif.gui.dialog.set.SetDialog;
import cn.thinkbear.gif.gui.share.ScreenShotPanel;
import cn.thinkbear.gif.gui.share.TitleBarPanel;
import cn.thinkbear.gif.gui.tray.GifTray;
import cn.thinkbear.gif.utils.ClipboardUtils;
import cn.thinkbear.gif.utils.ConfigUtils;
import cn.thinkbear.gif.vo.GifConfig;
import cn.thinkbear.gif.vo.UiConfig;

/**
 * 软件的主界面<br>
 * 
 * <img src="doc-files/MainDialog.png" alt="软件的主界面" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class MainDialog extends BaseDialog {

	private JPanel mainPanel = null;
	private TitleBarPanel titleBarPanel = null;
	private ScreenShotPanel screenShotPanel = null;
	private File gifFile = null;
	private GifTray gifTray = null;

	public MainDialog() {
		super.doCreateDialog();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.screenShotPanel = new ScreenShotPanel();
		this.titleBarPanel = new TitleBarPanel();
		this.gifTray = new GifTray();

	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.titleBarPanel.getRootPane(), BorderLayout.SOUTH);
		this.mainPanel.add(this.screenShotPanel.getRootPane(), BorderLayout.CENTER);
		super.getDialog().add(this.mainPanel);
	}

	@Override
	public void doSet() {

		this.updateUI();
		super.getDialog().getRootPane().setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.mainPanel.setCursor(Cursor.getDefaultCursor());

		this.titleBarPanel.setOnCallback(new IcoCallback());
		this.screenShotPanel.setOnCallback(new ScreenShotCallback());
		this.gifTray.setOnCallback(new TrayCallback());

		this.mainPanel.setOpaque(false);

		MouseMoveEvent moveEvent = new MouseMoveEvent();
		this.mainPanel.addMouseMotionListener(moveEvent);
		this.mainPanel.addMouseListener(moveEvent);

		super.getDialog().addMouseMotionListener(new MouseResizeEvent());
		super.getDialog().setUndecorated(true);
		super.getDialog().setBounds(ConfigUtils.getInstance().getScreenRect());
		super.getDialog().setBackground(new Color(0, 0, 0, 1));
		super.getDialog().setVisible(true);

	}
	/**
	 * 更新视图
	 */
	public void updateUI() {
		UiConfig config = ConfigUtils.getInstance().getUiConfig();
		this.screenShotPanel.setUiConfig(config);
		this.titleBarPanel.setUiConfig(config);
	}

	public void close() {
		ConfigUtils.getInstance().setScreenRect(super.getDialog().getBounds());
		ConfigUtils.getInstance().save();
		System.exit(0);
	}

	private class TrayCallback implements cn.thinkbear.gif.gui.tray.GifTray.OnCallback {

		@Override
		public void requestClose() {
			close();
		}

		@Override
		public void requestOpen() {
			getDialog().setVisible(true);
		}

	}

	private class IcoCallback implements cn.thinkbear.gif.gui.share.TitleBarPanel.OnCallback {

		@Override
		public void requestStart() {
			screenShotPanel.doStartScreenShot();
		}

		@Override
		public void requestStop() {
			screenShotPanel.doStopScreenShot();
		}

		@Override
		public void requestSet() {
			if (new SetDialog(mainPanel).getResult() == SetDialog.OK) {
				updateUI();
			}
		}

		@Override
		public void requestMin() {
			getDialog().setVisible(false);
		}

		@Override
		public void requestFull(boolean full) {
			if (full) {
				ConfigUtils.getInstance().setScreenRect(getDialog().getBounds());

				getDialog().setLocation(0, 0);
				getDialog().setSize(Toolkit.getDefaultToolkit().getScreenSize());
			} else {
				getDialog().setBounds(ConfigUtils.getInstance().getScreenRect());

			}
		}

		@Override
		public void requestLook() {

			File[] gifFiles = ConfigUtils.getInstance().getGifSaveDir().listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".gif") || name.endsWith(".GIF");
				}
			});

			if (gifFiles == null || gifFiles.length == 0) {
				JOptionPane.showMessageDialog(mainPanel, "在输出目录没有找到相关的Gif图片，无法进入Gif浏览！");
				return;
			}

			new LookGifDialog(mainPanel, gifFiles, gifFile);
		}

		@Override
		public void requestClose() {
			close();
		}

	}

	private class ScreenShotCallback implements cn.thinkbear.gif.gui.share.ScreenShotPanel.OnCallback {

		@Override
		public void screenShotStart() {
			titleBarPanel.startRecord();
		}

		@Override
		public void screenshotFinish(GifConfig gifConfig) {
			titleBarPanel.stopRecord();
			ProgressDialog progressDialog = new ProgressDialog(mainPanel, gifConfig);
			if (progressDialog.getResult() == ProgressDialog.OK) {
				gifFile = gifConfig.getOutputFile();
				if (ConfigUtils.getInstance().isAutoCopyGif()) {
					try {
						ClipboardUtils.getInstance(gifFile).doCopy();
						new PromptDialog(mainPanel, "复制Gif成功！", 2000);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(mainPanel, "复制Gif失败！\n" + e.getMessage());
					}
				}

			} else {
				JOptionPane.showMessageDialog(mainPanel, "生成Gif文件失败！\n" + progressDialog.getExceptionInfo());
			}

		}

	}

	/**
	 * 
	 * 通过此类实现鼠标移动软件
	 */
	private class MouseMoveEvent extends MouseAdapter {
		private int click_x;
		private int click_y;

		@Override
		public void mouseDragged(MouseEvent e) {
			if (e.getSource().equals(mainPanel)) {
				Point point = e.getLocationOnScreen();
				getDialog().setLocation(point.x - this.click_x, point.y - this.click_y);
			}
		}

		// 鼠标按钮在组件上按下时调用
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource().equals(mainPanel)) {
				this.click_x = e.getX();// 取得点击时的x坐标
				this.click_y = e.getY();// 取得点击时的y坐标
			}
		}
	}

	/**
	 * 通过此类实现鼠标拉大缩小软件界面
	 */
	private class MouseResizeEvent extends MouseAdapter {

		private boolean isTopLeft;// 是否处于左上角调整窗口状态
		private boolean isTop;// 是否处于上边界调整窗口状态
		private boolean isTopRight;// 是否处于右上角调整窗口状态
		private boolean isRight;// 是否处于右边界调整窗口状态
		private boolean isBottomRight;// 是否处于右下角调整窗口状态
		private boolean isBottom;// 是否处于下边界调整窗口状态
		private boolean isBottomLeft;// 是否处于左下角调整窗口状态
		private boolean isLeft;// 是否处于左边界调整窗口状态
		private final static int RESIZE_WIDTH = 3;// 判定是否为调整窗口状态的范围与边界距离

		@Override
		public void mouseMoved(MouseEvent event) {

			int x = event.getX();
			int y = event.getY();
			int width = getDialog().getWidth();
			int height = getDialog().getHeight();

			int cursorType = Cursor.DEFAULT_CURSOR;// 鼠标光标初始为默认类型，若未进入调整窗口状态，保持默认类型
			// 先将所有调整窗口状态重置
			isTopLeft = isTop = isTopRight = isRight = isBottomRight = isBottom = isBottomLeft = isLeft = false;
			if (y <= RESIZE_WIDTH) {
				if (x <= RESIZE_WIDTH) {// 左上角调整窗口状态
					isTopLeft = true;
					cursorType = Cursor.NW_RESIZE_CURSOR;
				} else if (x >= width - RESIZE_WIDTH) {// 右上角调整窗口状态
					isTopRight = true;
					cursorType = Cursor.NE_RESIZE_CURSOR;
				} else {// 上边界调整窗口状态
					isTop = true;
					cursorType = Cursor.N_RESIZE_CURSOR;
				}
			} else if (y >= height - RESIZE_WIDTH) {
				if (x <= RESIZE_WIDTH) {// 左下角调整窗口状态
					isBottomLeft = true;
					cursorType = Cursor.SW_RESIZE_CURSOR;
				} else if (x >= width - RESIZE_WIDTH) {// 右下角调整窗口状态
					isBottomRight = true;
					cursorType = Cursor.SE_RESIZE_CURSOR;
				} else {// 下边界调整窗口状态
					isBottom = true;
					cursorType = Cursor.S_RESIZE_CURSOR;
				}
			} else if (x <= RESIZE_WIDTH) {// 左边界调整窗口状态
				isLeft = true;
				cursorType = Cursor.W_RESIZE_CURSOR;
			} else if (x >= width - RESIZE_WIDTH) {// 右边界调整窗口状态
				isRight = true;
				cursorType = Cursor.E_RESIZE_CURSOR;
			}
			// 最后改变鼠标光标
			getDialog().setCursor(new Cursor(cursorType));
		}

		@Override
		public void mouseDragged(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			int width = getDialog().getWidth();
			int height = getDialog().getHeight();
			// 保存窗口改变后的x、y坐标和宽度、高度，用于预判是否会小于最小宽度、最小高度
			int nextX = getDialog().getX();
			int nextY = getDialog().getY();

			if (Math.abs(x - nextX) <= 2 && Math.abs(y - nextY) <= 2) {
				return;
			}

			int nextWidth = width;
			int nextHeight = height;
			if (isTopLeft || isLeft || isBottomLeft) {// 所有左边调整窗口状态
				nextX += x;
				nextWidth -= x;
			}
			if (isTopLeft || isTop || isTopRight) {// 所有上边调整窗口状态
				nextY += y;
				nextHeight -= y;
			}
			if (isTopRight || isRight || isBottomRight) {// 所有右边调整窗口状态
				nextWidth = x;
			}
			if (isBottomLeft || isBottom || isBottomRight) {// 所有下边调整窗口状态
				nextHeight = y;
			}
			if (nextWidth <= ConfigUtils.MIN_SCREEN_WIDTH) {// 如果窗口改变后的宽度小于最小宽度，则宽度调整到最小宽度
				nextWidth = ConfigUtils.MIN_SCREEN_WIDTH;
				if (isTopLeft || isLeft || isBottomLeft) {// 如果是从左边缩小的窗口，x坐标也要调整
					nextX = getDialog().getX() + width - nextWidth;
				}
			}
			if (nextHeight <= ConfigUtils.MIN_SCREEN_HEIGHT) {// 如果窗口改变后的高度小于最小高度，则高度调整到最小高度
				nextHeight = ConfigUtils.MIN_SCREEN_HEIGHT;
				if (isTopLeft || isTop || isTopRight) {// 如果是从上边缩小的窗口，y坐标也要调整
					nextY = getDialog().getY() + height - nextHeight;
				}
			}
			// 最后统一改变窗口的x、y坐标和宽度、高度，可以防止刷新频繁出现的屏闪情况
			getDialog().setBounds(nextX, nextY, nextWidth, nextHeight);
		}
	}

}

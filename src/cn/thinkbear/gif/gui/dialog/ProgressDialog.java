package cn.thinkbear.gif.gui.dialog;

import java.awt.Dialog.ModalityType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.squareup.gifencoder.GifEncoder;
import com.squareup.gifencoder.ImageOptions;

import cn.thinkbear.gif.gui.BaseDialog;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.utils.ConfigUtils;
import cn.thinkbear.gif.vo.Frame;
import cn.thinkbear.gif.vo.GifConfig;

/**
 * Gif 生成处理等待 对话框<br>
 * 
 * 
 * <img src="doc-files/ProgressDialog.png" alt="Gif 生成处理等待 对话框" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class ProgressDialog extends BaseDialog {

	public static final int OK = 0;
	/**保存错误信息*/
	private String exceptionInfo = null;

	private JComponent parent = null;
	private JLabel infoLabel = null;
	private GifConfig gifConfig = null;

	public ProgressDialog(JComponent parent, GifConfig gifConfig) {
		this.gifConfig = gifConfig;
		this.parent = parent;
		super.doCreateDialog();
	}

	@Override
	public void doCase() {
		this.infoLabel = new JLabel("ThinkBear Gif 启动中...", JLabel.CENTER);
	}

	@Override
	public void doAppend() {
		super.getDialog().add(this.infoLabel);
	}

	@Override
	public void doSet() {
		new Thread(new Task()).start();
		this.infoLabel.setFont(GuiFactory.FONT_BIG_PLAIN_OBJECT);
		super.getDialog().setSize(220, 60);
		super.getDialog().setUndecorated(true);
		super.getDialog().setLocationRelativeTo(this.parent);
		super.getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
		super.getDialog().setAlwaysOnTop(true);
		super.getDialog().setVisible(true);
	}

	public String getExceptionInfo() {
		return this.exceptionInfo;
	}
	/**
	 * 线程任务：生成Gif
	 */
	private class Task implements Runnable {

		@Override
		public void run() {
			try (OutputStream outputStream = new FileOutputStream(gifConfig.getOutputFile())) {
				List<Frame> data = gifConfig.getData();
				int size = data.size();
				GifEncoder encoder = new GifEncoder(outputStream, gifConfig.getWidth(), gifConfig.getHeight(), 0);

				for (int i = 0; i < size; i++) {
					Frame frame = data.get(i);
					ImageOptions options = new ImageOptions();
					options.setDelay(frame.getDelay(), TimeUnit.MILLISECONDS);
					encoder.addImage(this.getRGBData(frame.getFile()), options);

					infoLabel.setText("正在生成Gif...(" + size + "/" + (i + 1) + ")");
					if (ConfigUtils.getInstance().isAutoDeleteFrameImage()) {
						frame.getFile().delete();
					}
				}
				encoder.finishEncoding();
				if (ConfigUtils.getInstance().isAutoDeleteFrameImage()) {
					infoLabel.setText("正在删除帧文件...");
					gifConfig.getFrameDir().delete();
					System.out.println("删除");
				}
				setResult(OK);
			} catch (Exception e) {
				gifConfig.getFrameDir().delete();
				exceptionInfo = e.getMessage();
			}finally{
				getDialog().dispose();
			}
		}

		/**
		 * 取得图片的二维数组像素数据
		 * 
		 * @param file
		 *            图片文件
		 * @return 返回二维数组对象
		 * @throws IOException
		 *             发生异常，交给被调用处处理
		 */
		private int[][] getRGBData(File file) throws IOException {
			BufferedImage bi = ImageIO.read(file);
			int width = bi.getWidth();
			int height = bi.getHeight();
			int minx = bi.getMinX();
			int miny = bi.getMinY();

			int w = width - minx;
			int h = height - miny;
			int[][] data = new int[h][w];

			for (int i = miny; i < height; i++) {
				for (int j = minx; j < width; j++) {
					int pixel = bi.getRGB(j, i); 
					data[i][j] = pixel;
				}
			}
			return data;
		}

	}
}

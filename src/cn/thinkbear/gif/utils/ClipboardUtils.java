package cn.thinkbear.gif.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 实现复制gif文件到系统剪贴板上<br>
 * 
 * 	需同时复制gif缩略图和gif文件，才能在qq上发送gif表情<br>
 * 
 * @see java.awt.datatransfer.Clipboard
 * @see MyTransferable
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class ClipboardUtils {
	private File file = null;
	private ClipboardUtils(File file) {
		this.file = file;
	}

	public static ClipboardUtils getInstance(File file) {
		return new ClipboardUtils(file);
	}
	/**
	 * 复制操作
	 * @throws Exception 发生异常，交给被调用处处理
	 */
	public void doCopy() throws Exception {
		if (file == null || !file.exists()) {
			throw new FileNotFoundException("没有找到文件！");
		}
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new MyTransferable(), null);
	}
	/**
	 * Transferable的实现类<br>
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月24日
	 */
	private class MyTransferable implements Transferable {
		private BufferedImage image = null;

		public MyTransferable() throws IOException {
			this.image = ImageIO.read(file);
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			//传输的数据为图片和文件
			return new DataFlavor[] { DataFlavor.imageFlavor, DataFlavor.javaFileListFlavor };
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return DataFlavor.imageFlavor.equals(flavor) || DataFlavor.javaFileListFlavor.equals(flavor);
		}

		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (isDataFlavorSupported(flavor)) {
				if (DataFlavor.imageFlavor.equals(flavor)) {//传的是图片
					return image;
				} else if (DataFlavor.javaFileListFlavor.equals(flavor)) {//传的是文件
					ArrayList<File> all = new ArrayList<File>();
					all.add(file);
					return all;
				}
			}
			return null;
		}

	}
}

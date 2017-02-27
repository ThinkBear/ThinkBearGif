package cn.thinkbear.gif.gui.dialog.lookgif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cn.thinkbear.gif.gui.BaseDialog;
import cn.thinkbear.gif.gui.dialog.lookgif.GifListPanel.OnCallback;

/**
 * 查看Gif图片对话框<br><br>
 * 动态图效果展示区<br>
 * 动态图复制面板{@link GifCopyPanel}<br>
 * 动态图列表面板{@link GifListPanel}<br>
 * 
 * <img src="doc-files/LookGifDialog.png" alt="查看Gif图片对话框" style="margin: 10px">
 * 
 * @see GifCopyPanel
 * @see GifListPanel
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class LookGifDialog extends BaseDialog {

	private JComponent parent = null;
	private JPanel mainPanel = null;
	private JPanel bottomPanel = null;
	private GifListPanel gifListPanel = null;// 列表面板
	private GifCopyPanel gifCopyPanel = null;// 复制面板
	private JLabel gifLabel = null;
	/** 输出目录下所有的gif文件 */
	private File[] gifFiles = null;
	private File showGifFile = null;

	/**
	 * 构造方法
	 * @param parent 父级容器
	 * @param gifFiles 输出目录下所有的gif文件 
	 * @param showGifFile 要显示的动态图文件，可为空
	 */
	public LookGifDialog(JComponent parent, File[] gifFiles, File showGifFile) {
		this.parent = parent;
		this.gifFiles = gifFiles;
		this.showGifFile = showGifFile;
		super.doCreateDialog();

	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout(5, 5));
		this.bottomPanel = new JPanel(new BorderLayout(5, 5));
		this.gifLabel = new JLabel();
		this.gifListPanel = new GifListPanel(this.gifFiles);
		this.gifCopyPanel = new GifCopyPanel();
	}

	@Override
	public void doAppend() {
		this.bottomPanel.add(this.gifListPanel.getRootPane(), BorderLayout.CENTER);
		this.bottomPanel.add(this.gifCopyPanel.getRootPane(), BorderLayout.NORTH);
		this.mainPanel.add(this.gifLabel, BorderLayout.CENTER);
		this.mainPanel.add(this.bottomPanel, BorderLayout.SOUTH);
		super.getDialog().add(this.mainPanel);
	}

	/**
	 * 显示gif动态图，并更新复制面板信息
	 * 
	 * @param file
	 *            要显示的gif图片
	 */
	public void showGif(File file) {
		if (file == null) {
			return;
		}
		this.gifLabel.setIcon(new ImageIcon(file.getAbsolutePath()));
		this.gifCopyPanel.setGifFile(file);
	}

	@Override
	public void doSet() {

		// 设置默认显示的gif图片
		if (showGifFile != null && showGifFile.exists()) {
			this.showGif(showGifFile);
		} else if (this.gifFiles.length > 0) {
			this.showGif(this.gifFiles[0]);
		}

		this.bottomPanel.setOpaque(false);
		this.mainPanel.setBackground(Color.WHITE);
		this.gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.gifLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.gifListPanel.setOnCallback(new MyCallback());
		super.getDialog().setTitle("Gif浏览");
		super.getDialog().setSize(650, 550);
		super.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		super.getDialog().setLocationRelativeTo(this.parent);
		super.getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
		super.getDialog().setVisible(true);
	}

	private class MyCallback implements OnCallback {
		@Override
		public void itemSelected(int index) {
			showGif(gifFiles[index]);
		}
	}

}

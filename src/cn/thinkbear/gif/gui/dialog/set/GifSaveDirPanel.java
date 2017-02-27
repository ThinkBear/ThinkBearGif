package cn.thinkbear.gif.gui.dialog.set;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.utils.ConfigUtils;

/**
 * 输出目录设置<br>
 * 
 * <img src="doc-files/GifSaveDirPanel.png" alt="输出目录设置" style="margin: 10px">
 * 
 * @see #setGifSaveDir(File)
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class GifSaveDirPanel extends BaseView {

	private JPanel mainPanel = null;
	private JTextField gifSaveDirT = null;
	private JTextField frameSaveDirT = null;
	private JLabel alterLabel = null;
	private JLabel gifLabel = null;
	private JLabel frameLabel = null;
	private JPanel gifPanel = null;
	private JPanel framePanel = null;
	private File gifSaveDir = null;

	public GifSaveDirPanel() {
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		this.gifSaveDirT = GuiFactory.getTextField();
		this.frameSaveDirT = GuiFactory.getTextField();
		this.gifLabel = GuiFactory.getAmongPlainLabel("Gif目录");
		this.frameLabel = GuiFactory.getAmongPlainLabel("帧目录");
		this.alterLabel = new JLabel(GuiFactory.getImageToIco("set_dir.png", 32));
		this.gifPanel = new JPanel(new BorderLayout(5, 5));
		this.framePanel = new JPanel(new BorderLayout(5, 5));
	}

	@Override
	public void doAppend() {
		this.gifPanel.add(this.gifLabel, BorderLayout.WEST);
		this.gifPanel.add(this.gifSaveDirT, BorderLayout.CENTER);
		this.gifPanel.add(this.alterLabel, BorderLayout.EAST);
		this.framePanel.add(this.frameLabel, BorderLayout.WEST);
		this.framePanel.add(this.frameSaveDirT, BorderLayout.CENTER);
		this.mainPanel.add(this.gifPanel);
		this.mainPanel.add(this.framePanel);
	}

	@Override
	public void doSet() {
		this.gifLabel.setPreferredSize(new Dimension(50, 25));
		this.frameLabel.setPreferredSize(new Dimension(50, 25));
		this.gifSaveDirT.setForeground(Color.GRAY);
		this.frameSaveDirT.setForeground(Color.GRAY);
		this.gifLabel.setForeground(Color.GRAY);
		this.frameLabel.setForeground(Color.GRAY);
		this.alterLabel.addMouseListener(new MyMouseEvent());
		this.gifSaveDirT.setEditable(false);
		this.frameSaveDirT.setEditable(false);
		this.frameSaveDirT.setBackground(Color.WHITE);
		this.gifSaveDirT.setBackground(Color.WHITE);
		this.framePanel.setOpaque(false);
		this.gifPanel.setOpaque(false);
		this.mainPanel.setOpaque(false);
	}

	/**
	 * 设置gif图片保存目录
	 * 
	 * @param gifSaveDir
	 *            gif目录对象
	 */
	public void setGifSaveDir(File gifSaveDir) {
		this.gifSaveDir = gifSaveDir;
		this.gifSaveDirT.setText(this.gifSaveDir.getAbsolutePath());
		String str = new File(gifSaveDir, "14***********").getAbsolutePath();
		this.frameSaveDirT.setText(str);
		this.gifSaveDirT.select(0, 0);
		this.frameSaveDirT.select(0, 0);
	}

	/**
	 * 取得gif的保存目录
	 * 
	 * @return 当前设置的gif目录对象
	 */
	public File getGifSaveDir() {
		return this.gifSaveDir;
	}

	private class MyMouseEvent extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == alterLabel) {
				JFileChooser chooser = new JFileChooser(ConfigUtils.getInstance().getGifSaveDir());

				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogTitle("请选择Gif输出目录");// 设置文件选择器的标题
				int returnVal = chooser.showDialog(mainPanel, "确定");// 弹出文件选择器对话框，让用户进行文件的选择
				if (returnVal == JFileChooser.APPROVE_OPTION) {// 如果用户点击了确定
					File file = chooser.getSelectedFile();// 取得选择的文件的File对象
					setGifSaveDir(file);
				}
			}
		}
	}

	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}
}

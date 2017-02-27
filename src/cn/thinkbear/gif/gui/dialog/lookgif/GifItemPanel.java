package cn.thinkbear.gif.gui.dialog.lookgif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;

/**
 * Gif的缩略图面板<br>
 * 
 * <img src="doc-files/GifItemPanel.png" alt="Gif的缩略图面板" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class GifItemPanel extends BaseView {

	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;
	private JPanel mainPanel = null;
	private JLabel gifLabel = null;
	private JLabel nameLabel = null;

	private File gifFile = null;

	public GifItemPanel(File gifFile) {
		this.gifFile = gifFile;
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.gifLabel = new JLabel();
		this.nameLabel = GuiFactory.getAmongPlainLabel(this.gifFile.getName());
	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.gifLabel, BorderLayout.CENTER);
		this.mainPanel.add(this.nameLabel, BorderLayout.SOUTH);
		this.mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void doSet() {
		try {
			// 设置缩略图操作
			BufferedImage bufferedImage = ImageIO.read(this.gifFile);
			this.gifLabel.setIcon(
					new ImageIcon(bufferedImage.getScaledInstance(WIDTH, HEIGHT - 20, BufferedImage.SCALE_FAST)));
		} catch (Exception e) {
		}
	}

	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}
}

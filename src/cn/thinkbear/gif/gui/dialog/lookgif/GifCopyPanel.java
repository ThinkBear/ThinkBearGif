package cn.thinkbear.gif.gui.dialog.lookgif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.gui.dialog.PromptDialog;
import cn.thinkbear.gif.utils.ClipboardUtils;
/**
 * 动态图复制面板<br>
 * 
 * <img src="doc-files/GifCopyPanel.png" alt="动态图复制面板" style="margin: 10px">
 * 
 * @see ClipboardUtils
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class GifCopyPanel extends BaseView {

	private JPanel mainPanel = null;
	private JLabel copyLabel = null;
	private JLabel gifLabel = null;
	private MyMouseEvent mouseEvent = null;
	private File gifFile = null;

	public GifCopyPanel() {
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout(5, 5));
		this.copyLabel = new JLabel(GuiFactory.getImageToIco("ico_copy.png", 35));
		this.gifLabel = GuiFactory.getBigPlainLabel("");
		this.mouseEvent = new MyMouseEvent();
	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.copyLabel, BorderLayout.EAST);
		this.mainPanel.add(this.gifLabel, BorderLayout.CENTER);
	}

	@Override
	public void doSet() {
		this.gifLabel.setForeground(Color.GRAY);
		this.mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.gifLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.copyLabel.addMouseListener(this.mouseEvent);
		this.copyLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.mainPanel.setVisible(false);
	}
	/**
	 * 设置当前gif动态图文件的信息
	 * @param gifFile 
	 */
	public void setGifFile(File gifFile) {
		this.gifFile = gifFile;
		if (this.gifFile == null) {
			this.mainPanel.setVisible(false);
		} else {
			this.gifLabel.setText("<html><u>" + this.gifFile.getAbsolutePath() + "</u></html>");
			this.mainPanel.setVisible(true);
		}
	}

	private class MyMouseEvent extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (gifFile != null) {
				if (e.getSource() == copyLabel) {
					try {
						ClipboardUtils.getInstance(gifFile).doCopy();
						new PromptDialog(mainPanel,"复制Gif成功！",2000);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(mainPanel, "复制Gif失败！\n" + e1.getMessage());
					}
				}
			}

		}

	}

	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}
}

package cn.thinkbear.gif.gui.dialog.set;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;

/**
 * Gif生成后的操作<br>
 * 
 * <img src="doc-files/GifFinishRadioPanel.png" alt="Gif生成后的操作" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class GifFinishRadioPanel extends BaseView {
	private JPanel mainPanel = null;
	private JRadioButton copyRadio = null;
	private JRadioButton deleteRadio = null;

	public GifFinishRadioPanel() {
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		this.copyRadio = new JRadioButton("自动进行复制操作");
		this.deleteRadio = new JRadioButton("自动删除本地帧图片");
	}
	/**
	 * 设置自动复制是否选中
	 * @param autoCopyGif true 表示选中 ，false 表示不选中
	 */
	public void setAutoCopyGif(boolean autoCopyGif) {
		this.copyRadio.setSelected(autoCopyGif);
	}
	/**
	 * 设置自动删除是否选中
	 * @param autoDeleteFrameImage true 表示选中，false 表示不选中
	 */
	public void setAutoDeleteFrameImage(boolean autoDeleteFrameImage) {
		this.deleteRadio.setSelected(autoDeleteFrameImage);
	}
	/**
	 * 取得自动复制的选中状态
	 * @return true 选中 ，false 不选中
	 */
	public boolean isAutoCopyGif() {
		return this.copyRadio.isSelected();
	}
	/**
	 * 取得自动删除的选中状态
	 * @return true 选中，false 不选中
	 */
	public boolean isAutoDeleteFrameImage() {
		return this.deleteRadio.isSelected();
	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.copyRadio);
		this.mainPanel.add(this.deleteRadio);
	}

	@Override
	public void doSet() {
		this.copyRadio.setFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		this.copyRadio.setForeground(Color.GRAY);
		this.deleteRadio.setFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		this.deleteRadio.setForeground(Color.GRAY);
		this.copyRadio.setOpaque(false);
		this.deleteRadio.setOpaque(false);
		this.mainPanel.setOpaque(false);
	}

	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}

}

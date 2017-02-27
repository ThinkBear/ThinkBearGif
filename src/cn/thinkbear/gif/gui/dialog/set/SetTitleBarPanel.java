package cn.thinkbear.gif.gui.dialog.set;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.gui.dialog.set.ExamplePanel.OnCallback;
import cn.thinkbear.gif.utils.ConfigUtils;
import cn.thinkbear.gif.vo.UiConfig;

/**
 * 标题栏样式设置面板<br>
 * 
 * <img src="doc-files/SetTitleBarPanel.png" alt="标题栏样式设置面板" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class SetTitleBarPanel extends BaseView {
	private JPanel mainPanel = null;
	private JPanel titleBarColorPanel = null;
	private JLabel titleBarColorLabel = null;
	private JLabel selectedTitleBarColorLabel = null;
	private JPanel icoSizePanel = null;
	private JLabel icoSizeLabel = null;
	private JComboBox<Integer> icoSizeBox = null;
	private UiConfig config = null;
	private OnCallback onCallback = null;

	public void setOnCallback(OnCallback onCallback) {
		this.onCallback = onCallback;
	}

	public UiConfig getConfig() {
		return config;
	}

	public SetTitleBarPanel(UiConfig config) {
		this.config = config;
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		this.titleBarColorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		this.icoSizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		this.titleBarColorLabel = GuiFactory.getAmongPlainLabel("标题栏背景色");
		this.icoSizeLabel = GuiFactory.getAmongPlainLabel("图标大小");
		this.selectedTitleBarColorLabel = new JLabel(
				GuiFactory.getImageToIco("set_color.png", ConfigUtils.DEFAULT_ICO_SIZE));
		this.icoSizeBox = new JComboBox<Integer>();
	}

	@Override
	public void doAppend() {
		this.titleBarColorPanel.add(this.titleBarColorLabel);
		this.titleBarColorPanel.add(this.selectedTitleBarColorLabel);
		this.icoSizePanel.add(this.icoSizeLabel);
		this.icoSizePanel.add(this.icoSizeBox);
		this.mainPanel.add(this.titleBarColorPanel);
		this.mainPanel.add(this.icoSizePanel);
	}

	@Override
	public void doSet() {
		this.icoSizeBox.setModel(GuiFactory.getModel(ConfigUtils.MAX_ICO_SIZE, ConfigUtils.MIN_ICO_SIZE));
		this.icoSizeBox.setFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		this.mainPanel.setOpaque(false);
		this.icoSizePanel.setOpaque(false);
		this.titleBarColorPanel.setOpaque(false);
		this.icoSizeLabel.setForeground(Color.GRAY);
		this.titleBarColorLabel.setForeground(Color.GRAY);
		this.icoSizeBox.setSelectedItem(this.config.getIcoSize());
		this.selectedTitleBarColorLabel.addMouseListener(new MyMouseEvent());
		this.icoSizeBox.addItemListener(new MyItemEvent());
	}

	/**
	 * 设置默认值
	 */
	public void setDefaultValue() {
		this.config.setTitleBarColor(ConfigUtils.DEFAULT_TITLE_BAR_COLOR);
		this.config.setIcoSize(ConfigUtils.DEFAULT_ICO_SIZE);
		this.icoSizeBox.setSelectedItem(ConfigUtils.DEFAULT_ICO_SIZE);
	}

	private class MyMouseEvent extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == selectedTitleBarColorLabel) {
				Color newColor = JColorChooser.showDialog(mainPanel, "设置标题栏颜色", config.getScreenShotBorderColor());
				if (newColor != null) {
					config.setTitleBarColor(newColor);
					onCallback.requestUpdateUI();
				}
			}
		}

	}

	private class MyItemEvent implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == icoSizeBox) {
				if (e.getStateChange() == ItemEvent.SELECTED) {// 选中
					config.setIcoSize((Integer) icoSizeBox.getSelectedItem());
					if (onCallback != null) {
						onCallback.requestUpdateUI();
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

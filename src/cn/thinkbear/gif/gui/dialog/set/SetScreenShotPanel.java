package cn.thinkbear.gif.gui.dialog.set;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.gui.dialog.set.ExamplePanel.OnCallback;
import cn.thinkbear.gif.utils.ConfigUtils;
import cn.thinkbear.gif.vo.UiConfig;
/**
 * 录制样式设置面板 <br>
 * 
 * <img src="doc-files/SetScreenShotPanel.png" alt="录制样式设置面板" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class SetScreenShotPanel extends BaseView {
	private JPanel mainPanel = null;
	private JPanel borderColorPanel = null;
	private JLabel borderColorLabel = null;
	private JLabel selectedBorderColorLabel = null;
	private JPanel borderSizePanel = null;
	private JLabel borderSizeLabel = null;
	private JComboBox<Integer> borderSizeBox = null;
	private JPanel waterMarkColorPanel = null;
	private JLabel waterMarkColorLabel = null;
	private JLabel selectedWaterMarkColorLabel = null;
	private JPanel waterMarkSizePanel = null;
	private JLabel waterMarkSizeLabel = null;
	private JComboBox<Integer> waterMarkSizeBox = null;
	private JPanel waterMarkPanel = null;
	private JLabel waterMarkLabel = null;
	private JLabel setWaterMarkLabel = null;
	private JRadioButton hasBorderRadio = null;
	private JRadioButton showWateMarkRadio = null;
	private UiConfig config = null;
	private OnCallback onCallback = null;
	private MyItemEvent itemEvent = null;
	private MyMouseEvent mouseEvent = null;
	private MyChangeEvent changeEvent = null;

	public void setOnCallback(OnCallback onCallback) {
		this.onCallback = onCallback;
	}

	public UiConfig getConfig() {
		return config;
	}

	public SetScreenShotPanel(UiConfig config) {
		this.config = config;
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new GridLayout(3, 3, 5, 5));
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT,5,0);
		this.borderColorPanel = new JPanel(flowLayout);
		this.borderSizePanel = new JPanel(flowLayout);
		this.waterMarkColorPanel = new JPanel(flowLayout);
		this.waterMarkSizePanel = new JPanel(flowLayout);
		this.waterMarkPanel = new JPanel(flowLayout);
		this.borderColorLabel = GuiFactory.getAmongPlainLabel("边框颜色");
		this.borderSizeLabel = GuiFactory.getAmongPlainLabel("边框大小");
		this.selectedBorderColorLabel = new JLabel(
				GuiFactory.getImageToIco("set_color.png", ConfigUtils.DEFAULT_ICO_SIZE));
		this.borderSizeBox = new JComboBox<Integer>();
		this.waterMarkColorLabel = GuiFactory.getAmongPlainLabel("水印颜色");
		this.waterMarkSizeLabel = GuiFactory.getAmongPlainLabel("水印大小");
		this.waterMarkLabel = GuiFactory.getAmongPlainLabel("水印文字");
		this.selectedWaterMarkColorLabel = new JLabel(
				GuiFactory.getImageToIco("set_color.png", ConfigUtils.DEFAULT_ICO_SIZE));
		this.setWaterMarkLabel = new JLabel("更改", JLabel.CENTER);
		this.waterMarkSizeBox = new JComboBox<Integer>();
		this.hasBorderRadio = new JRadioButton("含边框录制");
		this.showWateMarkRadio = new JRadioButton("显示水印");
		this.itemEvent = new MyItemEvent();
		this.mouseEvent = new MyMouseEvent();
		this.changeEvent = new MyChangeEvent();
	}

	@Override
	public void doAppend() {
		this.borderColorPanel.add(this.borderColorLabel);
		this.borderColorPanel.add(this.selectedBorderColorLabel);
		this.borderSizePanel.add(this.borderSizeLabel);
		this.borderSizePanel.add(this.borderSizeBox);
		this.waterMarkColorPanel.add(this.waterMarkColorLabel);
		this.waterMarkColorPanel.add(this.selectedWaterMarkColorLabel);
		this.waterMarkSizePanel.add(this.waterMarkSizeLabel);
		this.waterMarkSizePanel.add(this.waterMarkSizeBox);
		this.waterMarkPanel.add(this.waterMarkLabel);
		this.waterMarkPanel.add(this.setWaterMarkLabel);
		this.mainPanel.add(this.borderColorPanel);
		this.mainPanel.add(this.borderSizePanel);
		this.mainPanel.add(this.hasBorderRadio);
		this.mainPanel.add(this.waterMarkColorPanel);
		this.mainPanel.add(this.waterMarkSizePanel);
		this.mainPanel.add(this.showWateMarkRadio);
		this.mainPanel.add(this.waterMarkPanel);
	}

	@Override
	public void doSet() {
		this.borderSizeBox.setFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		this.waterMarkSizeBox.setFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		this.showWateMarkRadio.setFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		this.hasBorderRadio.setFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		this.setWaterMarkLabel.setFont(GuiFactory.FONT_AMONG_BOLD_OBJECT);
		this.setWaterMarkLabel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		this.mainPanel.setOpaque(false);
		this.borderColorPanel.setOpaque(false);
		this.borderSizePanel.setOpaque(false);
		this.waterMarkColorPanel.setOpaque(false);
		this.waterMarkSizePanel.setOpaque(false);
		this.waterMarkPanel.setOpaque(false);
		this.showWateMarkRadio.setOpaque(false);
		this.hasBorderRadio.setOpaque(false);
		this.setWaterMarkLabel.setOpaque(true);
		this.borderSizeLabel.setForeground(Color.GRAY);
		this.borderColorLabel.setForeground(Color.GRAY);
		this.waterMarkSizeLabel.setForeground(Color.GRAY);
		this.waterMarkColorLabel.setForeground(Color.GRAY);
		this.showWateMarkRadio.setForeground(Color.GRAY);
		this.waterMarkLabel.setForeground(Color.GRAY);
		this.hasBorderRadio.setForeground(Color.GRAY);
		this.setWaterMarkLabel.setForeground(Color.BLUE);
		this.selectedBorderColorLabel.addMouseListener(this.mouseEvent);
		this.selectedWaterMarkColorLabel.addMouseListener(this.mouseEvent);
		this.setWaterMarkLabel.addMouseListener(this.mouseEvent);
		this.borderSizeBox.setModel(
				GuiFactory.getModel(ConfigUtils.MAX_SCREEN_SHOT_BORDER_SIZE, ConfigUtils.MIN_SCREEN_SHOT_BORDER_SIZE));
		this.waterMarkSizeBox
				.setModel(GuiFactory.getModel(ConfigUtils.MAX_WATER_MARK_SIZE, ConfigUtils.MIN_WATER_MARK_SIZE));
		this.borderSizeBox.setSelectedItem(this.config.getScreenShotBorderSize());
		this.waterMarkSizeBox.setSelectedItem(this.config.getWaterMarkSize());
		this.showWateMarkRadio.setSelected(this.config.isShowWateMark());
		this.hasBorderRadio.setSelected(this.config.isHasScreenShotBorder());
		this.borderSizeBox.addItemListener(this.itemEvent);
		this.waterMarkSizeBox.addItemListener(this.itemEvent);
		this.showWateMarkRadio.addChangeListener(this.changeEvent);
		this.hasBorderRadio.addChangeListener(this.changeEvent);
	}
	/**
	 * 设置默认值
	 */
	public void setDefaultValue() {
		this.config.setScreenShotBorderColor(ConfigUtils.DEFAULT_SCREEN_SHOT_BORDER_COLOR);
		this.config.setWaterMarkColor(ConfigUtils.DEFAULT_WATER_MARK_COLOR);
		this.config.setScreenShotBorderSize(ConfigUtils.DEFAULT_SCREEN_SHOT_BORDER_SIZE);
		this.config.setWaterMarkSize(ConfigUtils.DEFAULT_WATER_MARK_SIZE);
		this.config.setHasScreenShotBorder(false);
		this.config.setShowWateMark(true);
		this.borderSizeBox.setSelectedItem(ConfigUtils.DEFAULT_SCREEN_SHOT_BORDER_SIZE);
		this.waterMarkSizeBox.setSelectedItem(ConfigUtils.DEFAULT_WATER_MARK_SIZE);
		this.hasBorderRadio.setSelected(false);
		this.showWateMarkRadio.setSelected(true);
	}

	private class MyChangeEvent implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == showWateMarkRadio) {
				config.setShowWateMark(showWateMarkRadio.isSelected());
				if (onCallback != null) {
					onCallback.requestUpdateUI();
				}
			} else if (e.getSource() == hasBorderRadio) {
				config.setHasScreenShotBorder(hasBorderRadio.isSelected());
			}
		}

	}

	private class MyMouseEvent extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == selectedBorderColorLabel) {
				Color newColor = JColorChooser.showDialog(mainPanel, "设置边框颜色", config.getScreenShotBorderColor());
				if (newColor != null) {
					config.setScreenShotBorderColor(newColor);
					if (onCallback != null) {
						onCallback.requestUpdateUI();
					}
				}
			} else if (e.getSource() == selectedWaterMarkColorLabel) {
				Color newColor = JColorChooser.showDialog(mainPanel, "设置水印颜色", config.getWaterMarkColor());
				if (newColor != null) {
					config.setWaterMarkColor(newColor);
					if (onCallback != null) {
						onCallback.requestUpdateUI();
					}
				}
			} else if (e.getSource() == setWaterMarkLabel) {
				String result = JOptionPane.showInputDialog(mainPanel, "当前为：" + config.getWaterMark(), "请输入新的水印文本",
						JOptionPane.PLAIN_MESSAGE);
				if (result != null && result.trim().length() > 0) {
					config.setWaterMark(result);
					if (onCallback != null) {
						onCallback.requestUpdateUI();
					}
				} else {
					JOptionPane.showMessageDialog(mainPanel, "输入水印文本为空，设置失败");
				}
			}
		}

	}

	private class MyItemEvent implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {// 选中
				if (e.getSource() == borderSizeBox) {
					config.setScreenShotBorderSize((Integer) borderSizeBox.getSelectedItem());
					if (onCallback != null) {
						onCallback.requestUpdateUI();
					}
				} else if (e.getSource() == waterMarkSizeBox) {
					config.setWaterMarkSize((Integer) waterMarkSizeBox.getSelectedItem());
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

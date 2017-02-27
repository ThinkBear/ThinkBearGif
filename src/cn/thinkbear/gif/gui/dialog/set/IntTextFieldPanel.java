package cn.thinkbear.gif.gui.dialog.set;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.gui.share.IntTextField;

/**
 * 整数输入框面板<br>
 * 
 * <img src="doc-files/IntTextFieldPanel.png" alt="示例图" style="margin: 10px">
 * 
 * @see #initPara(String, String, int, int, int, int)
 * @see IntTextField
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class IntTextFieldPanel extends BaseView {

	private JPanel mainPanel = null;
	private IntTextField input = null;
	private JLabel titleLabel = null;
	private JLabel promptLabel = null;
	private FlowLayout flowLayout = null;

	public IntTextFieldPanel() {
		super.doCreateGui();

	}

	@Override
	public void doCase() {
		this.flowLayout = new FlowLayout(FlowLayout.LEFT);
		this.mainPanel = new JPanel();
		this.input = new IntTextField();
		this.titleLabel = GuiFactory.getAmongPlainLabel("");
		this.promptLabel = GuiFactory.getAmongPlainLabel("");
	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.titleLabel);
		this.mainPanel.add(this.input);
		this.mainPanel.add(this.promptLabel);
	}

	@Override
	public void doSet() {
		this.flowLayout.setHgap(5);
		this.flowLayout.setVgap(0);
		this.mainPanel.setLayout(this.flowLayout);
		this.titleLabel.setForeground(Color.GRAY);
		this.promptLabel.setForeground(Color.GRAY);
		this.mainPanel.setOpaque(false);
	}
	/**
	 * 初始化参数
	 * @param title 小标题信息
	 * @param unit 单位信息
	 * @param minValue 支持的最小值
	 * @param maxValue 支持的最大值
	 * @param addValue 每次增加或减少的量
	 * @param value 初始值
	 */
	public void initPara(String title, String unit, int minValue, int maxValue, int addValue, int value) {
		this.input.setMinAndMax(minValue, maxValue, addValue);
		this.input.setValue(value);

		this.titleLabel.setText(title);

		this.promptLabel.setText(unit + "(" + minValue + "-" + maxValue + ")");
	}
	/**
	 * 设置的文本信息
	 * @see IntTextField#setText(String)
	 * @param value 整数值
	 */
	public void setValue(int value) {
		this.input.setText(String.valueOf(value));
	}
	/**
	 * 取得当前的数据值
	 * @see IntTextField#getValue()
	 * @return 整数值
	 */
	public int getValue() {
		return this.input.getValue();
	}

	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}

}

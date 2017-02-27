package cn.thinkbear.gif.gui.share;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.gui.IView;

/**
 * 自定义输入框<br>
 * 
 * 特性说明：<br>
 * 1.只支持整数输入<br>
 * 2.组件右边有增加和减少图标，可通过点击进行值的增加和减少操作<br>
 * 
 * <img src="doc-files/IntTextField.png" alt="自定义输入框" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class IntTextField extends JTextField implements IView {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 70;
	public static final int HEIGHT = 28;
	private static final int ICO_WIDTH = 23;
	private static final int ICO_HEIGHT = 14;
	private JLabel upLabel = null;
	private JLabel downLabel = null;
	private MyMouseEvent mouseEvent = null;
	private int minValue = 1;
	private int maxValue = 10000;
	private int addValue = 100;

	public IntTextField() {
		this.doCase();
		this.doAppend();
		this.doSet();
	}

	@Override
	public void doCase() {
		this.upLabel = new JLabel(GuiFactory.getImage("set_up.png", ICO_WIDTH, ICO_HEIGHT));
		this.downLabel = new JLabel(GuiFactory.getImage("set_down.png", ICO_WIDTH, ICO_HEIGHT));
		this.mouseEvent = new MyMouseEvent();
	}

	@Override
	public void doAppend() {
		super.add(this.upLabel);
		super.add(this.downLabel);
		this.upLabel.setBounds(WIDTH - ICO_WIDTH, 0, ICO_WIDTH, ICO_HEIGHT);
		this.downLabel.setBounds(WIDTH - ICO_WIDTH, ICO_HEIGHT, ICO_WIDTH, ICO_HEIGHT);
	}

	@Override
	public void doSet() {
		super.setLayout(null);
		this.upLabel.setCursor(Cursor.getDefaultCursor());
		this.downLabel.setCursor(Cursor.getDefaultCursor());
		this.upLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.downLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		super.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		super.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
				BorderFactory.createEmptyBorder(3, 3, 3, 3)));
		super.setFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		this.upLabel.setBackground(Color.WHITE);
		this.downLabel.setBackground(Color.WHITE);

		this.upLabel.addMouseListener(this.mouseEvent);
		this.downLabel.addMouseListener(this.mouseEvent);

		super.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				int k = e.getKeyChar();
				if (k < KeyEvent.VK_0 || k > KeyEvent.VK_9) {
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	/**
	 * 设置值范围、增量
	 * 
	 * @param minValue
	 *            最小值
	 * @param maxValue
	 *            最大值
	 * @param addValue
	 *            增量
	 */
	public void setMinAndMax(int minValue, int maxValue, int addValue) {
		if (minValue >= maxValue) {// 如果最小值大于或等于最大值
			return;
		}
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.addValue = addValue;
	}

	/**
	 * 给输入框设置数据值
	 * 
	 * @param value
	 *            要设置数据值
	 */
	public void setValue(int value) {
		value = this.checkValue(value);
		super.setText(String.valueOf(value));
	}

	/**
	 * 判断是否超出范围值，或超出则进行赋值操作
	 * 
	 * @param value
	 *            要判断的值
	 * @return 符合要求的值
	 */
	public int checkValue(int value) {
		if (value > this.maxValue) {// 如果大于最大值
			value = this.maxValue;// 设置为最大值
		} else if (value < this.minValue) {// 如果小于最小值
			value = this.minValue;// 设置为最小值
		}
		return value;
	}

	/**
	 * 取得输入框设置的数据值
	 * 
	 * @return 当前输入框的数据值
	 */
	public int getValue() {
		int value = 0;
		try {
			value = Integer.valueOf(getText());
		} catch (NumberFormatException ee) {
		}
		value = this.checkValue(value);
		return value;
	}

	private class MyMouseEvent extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			int value = 0;
			try {
				value = Integer.valueOf(getText());
			} catch (NumberFormatException ee) {

			}
			value = checkValue(value);
			if (e.getSource() == upLabel) {
				setValue(value + addValue);
			} else if (e.getSource() == downLabel) {
				setValue(value - addValue);
			}
		}

	}

}

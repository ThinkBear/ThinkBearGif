package cn.thinkbear.gif.gui.share;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import cn.thinkbear.gif.gui.GuiFactory;

/**
 * 自定义标题组件<br>
 * 
 * 
 * <img src="doc-files/TitleLabel.png" alt="自定义标题组件" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class TitleLabel extends JComponent {
	private static final long serialVersionUID = 1L;
	private String title = null;
	private final int SPACE = 5;
	private Font font = GuiFactory.FONT_AMONG_BOLD_OBJECT;
	private Color textColor = Color.DARK_GRAY;
	private Color lineColor = Color.LIGHT_GRAY;

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            标题内容
	 */
	public TitleLabel(String title) {
		this.setTitle(title);
	}

	/**
	 * 设置标题内容
	 * 
	 * @param title
	 *            标题内容
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 设置字体
	 * 
	 * @param font
	 *            字体
	 */
	public void setTextFont(Font font) {
		this.font = font;
	}

	/**
	 * 设置字体颜色
	 * 
	 * @param textColor
	 *            颜色
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;

	}

	/**
	 * 设置线的颜色
	 * 
	 * @param lineColor
	 *            颜色
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// 解决文本文字模糊
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics metrics = g.getFontMetrics(this.font);
		g.setFont(this.font);
		g.setColor(this.textColor);
		// 取得字符串显示在界面上的宽度
		int strWidth = metrics.stringWidth(this.title);
		int strAscent = metrics.getAscent();
		g2d.drawString(this.title, SPACE, getHeight() / 2 + strAscent / 2 - metrics.getDescent() / 2);
		g.setColor(this.lineColor);
		g2d.fillRect(strWidth + SPACE * 2, getHeight() / 2, getWidth() - (strWidth + SPACE * 3), 2);
	}

}

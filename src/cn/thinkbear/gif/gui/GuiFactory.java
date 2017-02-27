package cn.thinkbear.gif.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


/**
 * Gui工厂类 取得颜色值对象，字体对象，等常用的组件对象
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月23日
 */
public class GuiFactory {
	/** 大号、粗体 */
	public static final Font FONT_BIG_BOLD_OBJECT = new Font(GuiPara.FONT_NAME, Font.BOLD, GuiPara.FONT_BIG);
	/** 大号 */
	public static final Font FONT_BIG_PLAIN_OBJECT = new Font(GuiPara.FONT_NAME, Font.PLAIN, GuiPara.FONT_BIG);
	/** 中号、粗体 */
	public static final Font FONT_AMONG_BOLD_OBJECT = new Font(GuiPara.FONT_NAME, Font.BOLD, GuiPara.FONT_AMONG);
	/** 中号 */
	public static final Font FONT_AMONG_PLAIN_OBJECT = new Font(GuiPara.FONT_NAME, Font.PLAIN, GuiPara.FONT_AMONG);
	/** 小号、粗体 */
	public static final Font FONT_SMALL_BOLD_OBJECT = new Font(GuiPara.FONT_NAME, Font.BOLD, GuiPara.FONT_SMALL);
	/** 小号 */
	public static final Font FONT_SMALL_PLAIN_OBJECT = new Font(GuiPara.FONT_NAME, Font.PLAIN, GuiPara.FONT_SMALL);
	
	public static JLabel getAmongPlainLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(FONT_AMONG_PLAIN_OBJECT);
		return label;
	}

	public static JLabel getBigBoldLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(FONT_BIG_BOLD_OBJECT);
		return label;
	}

	public static JLabel getBigPlainLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(FONT_BIG_PLAIN_OBJECT);
		return label;
	}

	public static JLabel getAmongBoldLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(FONT_AMONG_BOLD_OBJECT);
		return label;
	}

	public static JButton getButton(String text, Object listener) {
		JButton newButton = new JButton(text);
		newButton.setFont(FONT_AMONG_PLAIN_OBJECT);
		newButton.addActionListener((ActionListener) listener);
		newButton.setOpaque(false);
		return newButton;
	}

	public static JButton getBigButton(String text, Object listener) {
		JButton newButton = new JButton(text);
		newButton.setFont(new Font(GuiPara.FONT_NAME, Font.BOLD, GuiPara.FONT_BIG));
		newButton.addActionListener((ActionListener) listener);
		newButton.setOpaque(false);
		return newButton;
	}

	public static Border getTitleBorder(String title) {
		TitledBorder titleBorder = new TitledBorder(BorderFactory.createEtchedBorder());
		titleBorder.setTitle(title);
		titleBorder.setTitleFont(GuiFactory.FONT_AMONG_PLAIN_OBJECT);
		titleBorder.setTitleColor(Color.BLACK);
		titleBorder.setTitleJustification(TitledBorder.LEFT);
		return titleBorder;
	}

	public static Border getTitleBorder(String title, int inPadd) {
		Border titleBorder = getTitleBorder(title);
		Border insideBorder = BorderFactory.createEmptyBorder(inPadd, inPadd, inPadd, inPadd);
		return BorderFactory.createCompoundBorder(titleBorder, insideBorder);
	}

	public static JTextField getTextField() {
		return getTextField(null, 0, 0);
	}

	public static JTextField getTextField(int columns, int padd) {
		return getTextField(null, columns, padd);
	}

	public static JTextField getTextField(String text) {
		return getTextField(text, 0, 0);
	}

	public static JTextField getTextField(String text, int columns, int padd) {
		JTextField field = new JTextField();

		field.setFont(FONT_AMONG_PLAIN_OBJECT);
		if (text != null) {
			field.setText(text);
		}
		if (columns != 0) {
			field.setColumns(columns);
		}

		if (padd != 0) {
			field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
					BorderFactory.createEmptyBorder(padd, padd, padd, padd)));
		}
		return field;
	}

	/**
	 * 取得指定大小的图片
	 * 
	 * @param image
	 *            图片对象
	 * @param width
	 *            图片的宽度
	 * @param height
	 *            图片的高度
	 * @return 返回一个指定大小的图片对象
	 */
	public static ImageIcon getZoomImage(ImageIcon image, int width, int height) {
		ImageIcon newImage = new ImageIcon();
		// 对传入的图片对象转为Image对象，并通过此对象的getScaledInstance方法，创建此图像的缩放版本。
		// 返回一个新的 Image对象后设置给newImage
		newImage.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return newImage;
	}

	public static ImageIcon getImage(String name, int width, int height) {
		URL url = GuiFactory.class.getResource("/res/image/" + name);
		if (url == null) {
			return null;
		} else {
			ImageIcon image = new ImageIcon(url);
			return getZoomImage(image, width, height);
		}
	}

	public static ImageIcon getImageToIco(String name,int size) {
		return getImage(name, size, size);
	}

	/**
	 * 取得指定范围的数据模型
	 * @param max 最大值
	 * @param min 最小值
	 * @return 整数类型的数据模型对象
	 */
	public static ComboBoxModel<Integer> getModel(int max, int min) {
		Integer[] data = new Integer[max - min + 1];
		for (int i = 0; i < data.length; i++) {
			data[i] = min + i;
		}
		return new DefaultComboBoxModel<>(data);
	}
}

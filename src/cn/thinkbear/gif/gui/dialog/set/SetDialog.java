package cn.thinkbear.gif.gui.dialog.set;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cn.thinkbear.gif.gui.BaseDialog;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.utils.ConfigUtils;

/**
 * 设置对话框<br><br>
 * 基本设置： {@link BaseSetPanel}<br>
 * 界面设置：{@link UiSetPanel}<br>
 * 
 * 
 * 
 * <img src="doc-files/SetDialog.png" alt="设置对话框" style="margin: 10px">
 * 
 * @see BaseSetPanel
 * @see UiSetPanel
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class SetDialog extends BaseDialog {

	private JComponent parent = null;
	private JPanel mainPanel = null;
	private JPanel titleBarPanel = null;
	private JTabbedPane mainTabbed = null;
	private BaseSetPanel baseSetPanel = null;
	private UiSetPanel uiSetPanel = null;
	private JPanel btnPanel = null;

	private JLabel closeLabel = null;
	private JLabel applyLabel = null;
	private JLabel cancelLabel = null;

	private MyMouseEvent mouseEvent = null;

	public SetDialog(JComponent parent) {
		this.parent = parent;
		super.doCreateDialog();
	}

	@Override
	public void doCase() {
		this.titleBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		this.closeLabel = new JLabel(GuiFactory.getImageToIco("ico_close.png", ConfigUtils.getInstance().getIcoSize()));
		this.mainPanel = new JPanel(new BorderLayout());
		this.mainTabbed = new JTabbedPane(JTabbedPane.LEFT);
		this.baseSetPanel = new BaseSetPanel();
		this.uiSetPanel = new UiSetPanel();
		this.btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		this.applyLabel = GuiFactory.getBigBoldLabel("应用");
		this.cancelLabel = GuiFactory.getBigBoldLabel("取消");
		this.mouseEvent = new MyMouseEvent();
	}

	@Override
	public void doAppend() {

		this.titleBarPanel.add(this.closeLabel);
		this.mainTabbed.addTab("基本设置", this.baseSetPanel.getRootPane());
		this.mainTabbed.addTab("界面设置", this.uiSetPanel.getRootPane());

		this.btnPanel.add(this.applyLabel);
		this.btnPanel.add(new JLabel(" "));
		this.btnPanel.add(this.cancelLabel);
		this.mainPanel.add(this.titleBarPanel, BorderLayout.NORTH);
		this.mainPanel.add(this.mainTabbed, BorderLayout.CENTER);
		this.mainPanel.add(this.btnPanel, BorderLayout.SOUTH);
		super.getDialog().add(this.mainPanel);
	}

	@Override
	public void doSet() {

		MouseMoveEvent moveEvent = new MouseMoveEvent();
		this.titleBarPanel.addMouseMotionListener(moveEvent);
		this.titleBarPanel.addMouseListener(moveEvent);

		this.closeLabel.addMouseListener(this.mouseEvent);
		this.applyLabel.addMouseListener(this.mouseEvent);
		this.cancelLabel.addMouseListener(this.mouseEvent);

		this.applyLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
		this.cancelLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));

		this.applyLabel.setBackground(Color.WHITE);
		this.cancelLabel.setBackground(Color.WHITE);
		this.applyLabel.setForeground(Color.BLUE);
		this.cancelLabel.setForeground(Color.GRAY);
		this.applyLabel.setOpaque(true);
		this.cancelLabel.setOpaque(true);

		this.mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.mainTabbed.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.mainTabbed.setOpaque(false);
		this.mainPanel.setBackground(Color.WHITE);

		this.mainTabbed.setFont(GuiFactory.FONT_BIG_BOLD_OBJECT);

		super.getDialog().setUndecorated(true);
		super.getDialog().setSize(510, 540);
		super.getDialog().setLocationRelativeTo(this.parent);
		super.getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
		super.getDialog().setVisible(true);
	}

	private class MyMouseEvent extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == closeLabel) {
				getDialog().dispose();
			} else if (e.getSource() == applyLabel) {
				baseSetPanel.save();
				uiSetPanel.save();
				if (ConfigUtils.getInstance().save()) {
					setResult(OK);
					getDialog().dispose();
				} else {
					JOptionPane.showMessageDialog(mainPanel, "保存设置失败！");
				}

			} else if (e.getSource() == cancelLabel) {
				getDialog().dispose();
			}
		}
	}

	/**
	 * 通过此类实现鼠标移动软件
	 */
	private class MouseMoveEvent extends MouseAdapter {
		private int click_x;
		private int click_y;

		@Override
		public void mouseDragged(MouseEvent e) {
			if (e.getSource().equals(titleBarPanel)) {
				Point point = e.getLocationOnScreen();
				getDialog().setLocation(point.x - this.click_x, point.y - this.click_y);
			}
		}

		// 鼠标按钮在组件上按下时调用
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource().equals(titleBarPanel)) {
				this.click_x = e.getX();// 取得点击时的x坐标
				this.click_y = e.getY();// 取得点击时的y坐标
			}
		}
	}

}

package cn.thinkbear.gif.gui.dialog;

import java.awt.Color;
import java.awt.Dialog.ModalityType;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;

import cn.thinkbear.gif.gui.BaseDialog;
import cn.thinkbear.gif.gui.GuiFactory;

/**
 * 消息提示对话框<br>
 * 指定时间对话框自动消失<br>
 * 
 * 
 * <img src="doc-files/PromptDialog.png" alt="消息提示对话框" style="margin: 10px">
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class PromptDialog extends BaseDialog {

	private JComponent parent = null;
	private JDialog mainDialog = null;

	private JLabel infoLabel = null;
	private String message = null;
	private int delay = 0;
	/**
	 * 构造函数
	 * @param parent 父级窗口
	 * @param message 要显示的信息
	 * @param delay 延迟时间，自动关闭
	 */
	public PromptDialog(JComponent parent, String message, int delay) {
		this.parent = parent;
		this.message = message;
		this.delay = delay;
		super.doCreateDialog();
	}

	@Override
	public void doCase() {
		this.mainDialog = new JDialog();
		this.infoLabel = new JLabel(this.message, JLabel.CENTER);
	}

	@Override
	public void doAppend() {
		this.mainDialog.add(this.infoLabel);
	}

	@Override
	public void doSet() {
		this.infoLabel.setOpaque(true);
		this.infoLabel.setBackground(Color.BLACK);
		this.infoLabel.setFont(GuiFactory.FONT_BIG_BOLD_OBJECT);
		this.infoLabel.setForeground(Color.WHITE);

		new Thread(new Task()).start();
		
		this.mainDialog.setUndecorated(true);
		this.mainDialog.setSize(200, 60);
		this.mainDialog.setAlwaysOnTop(true);
		this.mainDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		this.mainDialog.setLocationRelativeTo(this.parent);
		this.mainDialog.setVisible(true);
	}

	/**
	 * 线程任务：一定时间后 自动关闭对话框
	 */
	private class Task implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}

			mainDialog.dispose();
		}

	}
}

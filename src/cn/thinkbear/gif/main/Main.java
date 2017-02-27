package cn.thinkbear.gif.main;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import cn.thinkbear.gif.gui.dialog.MainDialog;
/**
 * 主类
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月23日
 */
public class Main {

	public static void main(String[] args) throws IOException {
		try {
			// 如果有实现本机系统外观的 LookAndFeel 类的名称，则返回该名称；否则返回默认的跨平台 LookAndFeel 类的名称
			String name = UIManager.getSystemLookAndFeelClassName();
			// 加载给定类名称所指定的 LookAndFeel,从而实现软件窗口的外观设置
			UIManager.setLookAndFeel(name);
		} catch (Exception e) {
			// 出现异常时，弹出提示窗，告诉用户信息
			JOptionPane.showMessageDialog(null, "系统皮肤加载失败，已采用默认皮肤！");
		}
		new MainDialog();
	}

}

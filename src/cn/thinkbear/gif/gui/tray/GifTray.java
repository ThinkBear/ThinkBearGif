package cn.thinkbear.gif.gui.tray;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;

/**
 * 系统托盘，进行任务状态栏显示和隐藏图标操作<br>
 * 
 * 
 * <img src="doc-files/GifTray.png" alt="系统托盘" style="margin: 10px">
 * 
 * @see SystemTray
 * @see TrayIcon
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class GifTray extends BaseView implements ActionListener {

	private SystemTray tray = null;
	private PopupMenu popupMenu = null;
	private MenuItem openItem = null;
	private MenuItem exitItem = null;
	private TrayIcon trayIcon = null;
	private OnCallback onCallback = null;

	public void setOnCallback(OnCallback onCallback) {
		this.onCallback = onCallback;
	}

	public GifTray() {
		if(SystemTray.isSupported()){
			super.doCreateGui();	
		}
	}

	@Override
	public void doCase() {
		this.tray = SystemTray.getSystemTray();
		// 创建托盘图标的右键弹出菜单，Open与Exit.
		this.popupMenu = new PopupMenu();
		this.openItem = new MenuItem("Open");
		this.exitItem = new MenuItem("Exit");

		this.trayIcon = new TrayIcon(GuiFactory.getImageToIco("logo_30.png", 30).getImage(), "Gif生成器", this.popupMenu);
	}

	@Override
	public void doAppend() {
		this.popupMenu.add(this.openItem);
		this.popupMenu.addSeparator();
		this.popupMenu.add(this.exitItem);
	}

	@Override
	public void doSet() {
		this.trayIcon.setImageAutoSize(true);
		this.openItem.setFont(GuiFactory.FONT_BIG_BOLD_OBJECT);
		this.exitItem.setFont(GuiFactory.FONT_BIG_PLAIN_OBJECT);
		this.exitItem.addActionListener(this);
		this.openItem.addActionListener(this);
		this.trayIcon.addActionListener(this);
		try {
			this.tray.add(this.trayIcon);
		} catch (AWTException e) {
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.openItem || e.getSource() == this.trayIcon) {
			onCallback.requestOpen();
		} else if (e.getSource() == this.exitItem) {
			onCallback.requestClose();

		}
	}

	/**
	 * 回调接口：接收双击任务栏图标、及点击菜单项事件
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月26日
	 */
	public interface OnCallback {
		/**
		 * 请求关闭程序
		 */
		public void requestClose();

		/**
		 * 请求打开软件
		 */
		public void requestOpen();

	}

	@Override
	public JComponent getRootPane() {
		return null;
	}

}

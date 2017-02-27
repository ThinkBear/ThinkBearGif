package cn.thinkbear.gif.gui.dialog.set;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.share.ScreenShotPanel;
import cn.thinkbear.gif.gui.share.TitleBarPanel;
import cn.thinkbear.gif.vo.UiConfig;
/**
 * 
 * 示例面板<br><br>
 * 	标题栏面板{@link TitleBarPanel}<br>
 * 	屏幕录制面板{@link ScreenShotPanel}<br>
 * 
 * 
 * <img src="doc-files/ExamplePanel.png" alt="示例面板" style="margin: 10px">
 * 
 * @see TitleBarPanel
 * @see ScreenShotPanel
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class ExamplePanel extends BaseView {

	private JPanel mainPanel = null;
	private TitleBarPanel titleBarPanel = null;
	private ScreenShotPanel screenShotPanel = null;
	private UiConfig config = null;

	public ExamplePanel(UiConfig config) {
		this.config = config;
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.titleBarPanel = new TitleBarPanel();
		this.screenShotPanel = new ScreenShotPanel();
	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.titleBarPanel.getRootPane(),BorderLayout.SOUTH);
		this.mainPanel.add(this.screenShotPanel.getRootPane(),BorderLayout.CENTER);

	}

	@Override
	public void doSet() {
		this.mainPanel.setOpaque(false);
		this.titleBarPanel.setDisabled();//设置禁用
		this.titleBarPanel.setUiConfig(config);
		this.screenShotPanel.setUiConfig(config);
	}
	/**
	 * 更新视图
	 */
	public void updateUI(){
		this.titleBarPanel.updateUI();
		this.screenShotPanel.updateUI();
	}

	/**
	 * 回调接口：示例面板需进行更新视图操作
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月24日
	 */
	public interface OnCallback {
		/**
		 * 请求更新视图
		 */
		public void requestUpdateUI();
	}


	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}
}

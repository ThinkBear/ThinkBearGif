package cn.thinkbear.gif.gui.dialog.set;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.gui.dialog.set.ExamplePanel.OnCallback;
import cn.thinkbear.gif.gui.share.TitleLabel;
import cn.thinkbear.gif.utils.ConfigUtils;
import cn.thinkbear.gif.vo.UiConfig;
/**
 * 界面设置<br><br>
 * 	示例面板 {@link ExamplePanel}<br>
 * 	标题栏样式设置面板 {@link SetTitleBarPanel}<br>
 * 	录制样式设置面板 {@link SetScreenShotPanel}<br>
 * 
 * 
 * <img src="doc-files/UiSetPanel.png" alt="界面设置" style="margin: 10px">
 * 
 * @see ExamplePanel
 * @see SetTitleBarPanel
 * @see SetScreenShotPanel
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class UiSetPanel extends BaseView {

	public static final int WIDTH = 400;
	public static final int SPACE = 5;
	private JPanel mainPanel = null;
	private ExamplePanel examplePanel = null;
	private SetTitleBarPanel setTitleBarPanel = null;
	private SetScreenShotPanel setScreenShotPanel = null;
	private JLabel bgExample = null;
	private TitleLabel titleBarStyleLabel = null;
	private TitleLabel borderStyleLabel = null;
	private UiConfig config = null;
	private JLabel defaultLabel = null;
	private MyCallback callback = null;

	public UiSetPanel() {
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.config = ConfigUtils.getInstance().getUiConfig();
		this.mainPanel = new JPanel();
		this.titleBarStyleLabel = new TitleLabel("标题栏样式设置");
		this.borderStyleLabel = new TitleLabel("录制样式设置");
		this.examplePanel = new ExamplePanel(this.config);
		this.setTitleBarPanel = new SetTitleBarPanel(this.config);
		this.setScreenShotPanel = new SetScreenShotPanel(this.config);
		this.callback = new MyCallback();
		this.defaultLabel = new JLabel("默认值", JLabel.CENTER);
		this.bgExample = new JLabel();
	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.examplePanel.getRootPane());
		this.mainPanel.add(this.setTitleBarPanel.getRootPane());
		this.mainPanel.add(this.setScreenShotPanel.getRootPane());
		this.mainPanel.add(this.titleBarStyleLabel);
		this.mainPanel.add(this.borderStyleLabel);
		this.mainPanel.add(this.defaultLabel);
		this.mainPanel.add(this.bgExample);
		int y = SPACE * 2;
		int title_w = WIDTH - SPACE * 2;
		int child_x = SPACE * 3;
		int child_w = WIDTH - SPACE * 5;
		int exampleWidth = 230;
		int exampleHeight = 165;
		this.examplePanel.getRootPane().setBounds((WIDTH - exampleWidth) / 2, y, exampleWidth, exampleHeight);
		int bgWidth = exampleWidth + SPACE * 6;
		int bgHeight = exampleHeight + SPACE * 2;
		this.bgExample.setIcon(GuiFactory.getImage("bg_example.jpg", bgWidth, bgHeight));
		this.bgExample.setBounds((WIDTH - bgWidth) / 2, SPACE, bgWidth, bgHeight);
		y = SPACE + y + bgHeight;
		this.titleBarStyleLabel.setBounds(SPACE, y, title_w, 30);
		y = SPACE + y + 30;
		this.setTitleBarPanel.getRootPane().setBounds(child_x, y, child_w, 30);
		y = SPACE + y + 30;
		this.borderStyleLabel.setBounds(SPACE, y, title_w, 30);
		y = SPACE + y + 30;
		this.setScreenShotPanel.getRootPane().setBounds(child_x, y, child_w, 90);
		y = SPACE * 2 + y + 90;
		this.defaultLabel.setBounds(WIDTH - SPACE * 2 - 60, y, 60, 28);
	}

	@Override
	public void doSet() {
		this.mainPanel.setLayout(null);
		this.mainPanel.setOpaque(false);
		this.defaultLabel.setOpaque(true);
		this.defaultLabel.setForeground(Color.BLACK);
		this.defaultLabel.setFont(GuiFactory.FONT_AMONG_BOLD_OBJECT);
		this.defaultLabel.addMouseListener(new MyMouseEvent());
		this.setScreenShotPanel.setOnCallback(this.callback);
		this.setTitleBarPanel.setOnCallback(this.callback);
	}
	/**
	 * 保存设置操作
	 */
	public void save() {
		ConfigUtils.getInstance().setUiConfig(this.config);
	}

	private class MyMouseEvent extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == defaultLabel) {
				setTitleBarPanel.setDefaultValue();
				setScreenShotPanel.setDefaultValue();
				examplePanel.updateUI();
			}
		}
	}
	/**
	 * 回调接口：标题栏样式、录制样式有设置的时候会调用
	 * 
	 * @see OnCallback#requestUpdateUI()
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月26日
	 */
	private class MyCallback implements OnCallback {

		@Override
		public void requestUpdateUI() {
			examplePanel.updateUI();
		}

	}


	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}
}

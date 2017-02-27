package cn.thinkbear.gif.gui.share;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.vo.UiConfig;

/**
 * 标题栏面板<br>
 * 
 * <img src="doc-files/TitleBarPanel.png" alt="标题栏面板" style="margin: 10px">
 * 
 * @see #startRecord()
 * @see #stopRecord()
 * @see OnCallback
 * @see TimingLabel
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class TitleBarPanel extends BaseView {
	private JPanel mainPanel = null;
	private JPanel leftPanel = null;
	private JPanel rightPanel = null;
	private JLabel closeLabel = null;
	private JLabel minLabel = null;
	private JLabel setLabel = null;
	private JLabel recordLabel = null;
	private JLabel fullLabel = null;
	private JLabel lookLabel = null;
	private TimingLabel timingLabel = null;
	private MyMouseEvent mouseEvent = null;
	private OnCallback onCallback = null;
	private UiConfig config = null;
	/** 是否正在录制 */
	private boolean isRecording = false;
	/** 是否为全屏 */
	private boolean full = false;

	public void setOnCallback(OnCallback onCallback) {
		this.onCallback = onCallback;
	}

	public TitleBarPanel() {
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.closeLabel = new JLabel();
		this.setLabel = new JLabel();
		this.recordLabel = new JLabel();
		this.lookLabel = new JLabel();
		this.fullLabel = new JLabel();
		this.minLabel = new JLabel();
		this.timingLabel = new TimingLabel();
		this.mouseEvent = new MyMouseEvent();
	}

	@Override
	public void doAppend() {
		this.leftPanel.add(this.recordLabel);
		this.leftPanel.add(this.timingLabel);
		this.rightPanel.add(this.lookLabel);
		this.rightPanel.add(this.fullLabel);
		this.rightPanel.add(this.setLabel);
		this.rightPanel.add(this.minLabel);
		this.rightPanel.add(this.closeLabel);
		this.mainPanel.add(this.leftPanel, BorderLayout.WEST);
		this.mainPanel.add(this.rightPanel, BorderLayout.EAST);
	}

	@Override
	public void doSet() {
		this.closeLabel.setToolTipText("关闭程序");
		this.setLabel.setToolTipText("打开设置");
		this.minLabel.setToolTipText("最小化");
		this.lookLabel.setToolTipText("Gif浏览");	
		
		this.closeLabel.addMouseListener(this.mouseEvent);
		this.setLabel.addMouseListener(this.mouseEvent);
		this.recordLabel.addMouseListener(this.mouseEvent);
		this.lookLabel.addMouseListener(this.mouseEvent);
		this.fullLabel.addMouseListener(this.mouseEvent);
		this.minLabel.addMouseListener(this.mouseEvent);
		this.timingLabel.setFont(GuiFactory.FONT_BIG_PLAIN_OBJECT);
		this.timingLabel.setForeground(Color.RED);
		this.rightPanel.setOpaque(false);
		this.mainPanel.setCursor(Cursor.getDefaultCursor());
	}
	/**
	 * 更新视图操作，主要更新图标大小、标题栏背景颜色
	 */
	public void updateUI() {
		if (this.config != null) {
			int icoSize = this.config.getIcoSize();
			this.closeLabel.setIcon(GuiFactory.getImageToIco("ico_close.png", icoSize));
			this.setLabel.setIcon(GuiFactory.getImageToIco("ico_set.png", icoSize));
			this.fullLabel.setIcon(GuiFactory.getImageToIco("ico_full.png", icoSize));
			this.lookLabel.setIcon(GuiFactory.getImageToIco("ico_look.png", icoSize));
			this.minLabel.setIcon(GuiFactory.getImageToIco("ico_min.png", icoSize));
			this.mainPanel.setBackground(this.config.getTitleBarColor());
			this.leftPanel.setBackground(this.config.getTitleBarColor());
			this.updateRecordUI(icoSize);
			this.updateFullUI(icoSize);
		}

	}

	public UiConfig getUiConfig() {
		return this.config;
	}
	/**
	 * 设置配置数据
	 * @param config
	 */
	public void setUiConfig(UiConfig config) {
		this.config = config;
		this.updateUI();
	}
	/**
	 * 设置禁用模式，清除图标的点击事件
	 */
	public void setDisabled() {
		this.closeLabel.removeMouseListener(this.mouseEvent);
		this.setLabel.removeMouseListener(this.mouseEvent);
		this.recordLabel.removeMouseListener(this.mouseEvent);
		this.lookLabel.removeMouseListener(this.mouseEvent);
		this.minLabel.removeMouseListener(this.mouseEvent);
		this.fullLabel.removeMouseListener(this.mouseEvent);
	}
	/**
	 * 更新全屏图标及提示文本
	 * @param icoSize 图标大小
	 */
	public void updateFullUI(int icoSize) {
		if (this.full) {
			this.fullLabel.setIcon(GuiFactory.getImageToIco("ico_un_full.png", icoSize));
			this.fullLabel.setToolTipText("取消全屏");
		} else {
			this.fullLabel.setIcon(GuiFactory.getImageToIco("ico_full.png", icoSize));
			this.fullLabel.setToolTipText("全屏");
		}
	}
	/**
	 * 更新录制图标及提示文本
	 * @param icoSize 图标大小
	 */
	public void updateRecordUI(int icoSize) {
		if (this.isRecording) {
			this.recordLabel.setIcon(GuiFactory.getImageToIco("ico_stop.png", icoSize));
			this.fullLabel.setToolTipText("停止");
		} else {
			this.recordLabel.setIcon(GuiFactory.getImageToIco("ico_start.png", icoSize));
			this.fullLabel.setToolTipText("开始");
		}
	}

	/**
	 * 是否正在录制
	 * 
	 * @return
	 */
	public boolean isRecording() {
		return this.isRecording;
	}

	/**
	 * 开始录制方法
	 */
	public void startRecord() {
		this.isRecording = true;
		this.timingLabel.setVisible(true);
		this.timingLabel.startTiming();
		this.updateRecordUI(this.config.getIcoSize());
	}

	/**
	 * 停止录制方法
	 */
	public void stopRecord() {
		this.isRecording = false;
		this.timingLabel.setVisible(false);
		this.timingLabel.stopTiming();
		this.updateRecordUI(this.config.getIcoSize());
	}

	private class MyMouseEvent extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == closeLabel) {
				onCallback.requestClose();
			} else if (e.getSource() == setLabel) {
				onCallback.requestSet();
			} else if (e.getSource() == recordLabel) {
				if (onCallback != null) {
					if (isRecording) {
						onCallback.requestStop();
					} else {
						onCallback.requestStart();
					}
				}
			} else if (e.getSource() == lookLabel) {
				onCallback.requestLook();
			} else if (e.getSource() == fullLabel) {
				full = !full;
				onCallback.requestFull(full);
				updateFullUI(config.getIcoSize());
			} else if (e.getSource() == minLabel) {
				onCallback.requestMin();
			}

		}
	}

	/**
	 * 回调接口：图标点击事件
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月24日
	 */
	public interface OnCallback {
		/**
		 * 请求开始录制
		 */
		public void requestStart();

		/**
		 * 请求停止录制
		 */
		public void requestStop();

		/**
		 * 请求最小化
		 */
		public void requestMin();

		/**
		 * 请求全屏或退出全屏
		 * 
		 * @param full
		 */
		public void requestFull(boolean full);

		/**
		 * 请求打开设置对话框
		 */
		public void requestSet();

		/**
		 * 请求打开gif浏览对话框
		 */
		public void requestLook();

		/**
		 * 请求退出程序
		 */
		public void requestClose();
	}

	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}

}

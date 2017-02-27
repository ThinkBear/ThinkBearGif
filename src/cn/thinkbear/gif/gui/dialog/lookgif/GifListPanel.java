package cn.thinkbear.gif.gui.dialog.lookgif;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.thinkbear.gif.gui.BaseView;
/**
 * gif图片列表面板<br>
 * 
 * <img src="doc-files/GifListPanel.png" alt="gif图片列表面板" style="margin: 10px">
 * 
 * @see GifItemPanel
 * 
 * @author ThinkBear
 * @date 2017年2月25日 下午6:41:03
 */
public class GifListPanel extends BaseView {

	private JScrollPane mainScroll = null;
	private JPanel mainPanel = null;
	private OnCallback onCallback = null;
	private File[] gifFiles = null;

	public void setOnCallback(OnCallback onCallback) {
		this.onCallback = onCallback;
	}

	public GifListPanel(File[] gifFiles) {
		this.gifFiles = gifFiles;
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		this.mainScroll = new JScrollPane(this.mainPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	}

	@Override
	public void doAppend() {
		//向列表面板添加子面板数据
		for (int i = 0; i < this.gifFiles.length; i++) {
			GifItemPanel gifItemPanel = new GifItemPanel(gifFiles[i]);
			
			gifItemPanel.getRootPane().addMouseListener(new MyMouseEvent(i));
			this.mainPanel.add(gifItemPanel.getRootPane());
		}
	}

	@Override
	public void doSet() {
		this.mainPanel.setOpaque(false);
		this.mainScroll.getViewport().setOpaque(false);
		this.mainScroll.setOpaque(false);
		this.mainScroll.setBorder(BorderFactory.createEmptyBorder());
		this.mainScroll.getHorizontalScrollBar().setUnitIncrement(GifItemPanel.WIDTH);
	}

	private class MyMouseEvent extends MouseAdapter {
		private int index = 0;

		public MyMouseEvent(int index) {
			this.index = index;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			onCallback.itemSelected(index);
		}
	}
	/**
	 * 回调接口：当列表中的图片被点击
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月24日
	 */
	public interface OnCallback {
		/**
		 * 选中了某张图片
		 * @param index 图片的下标值
		 */
		public void itemSelected(int index);
	}

	@Override
	public JComponent getRootPane() {
		return this.mainScroll;
	}

}

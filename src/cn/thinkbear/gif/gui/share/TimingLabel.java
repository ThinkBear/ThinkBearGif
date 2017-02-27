package cn.thinkbear.gif.gui.share;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
/**
 * 计时器<br>
 * 
 * <img src="doc-files/TimingLabel.png" alt="计时器" style="margin: 10px">
 * 
 * @see #startTiming()
 * @see #stopTiming()
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class TimingLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private Thread task = null;
	public TimingLabel() {
		super.setHorizontalAlignment(SwingConstants.CENTER);
		super.setVerticalAlignment(SwingConstants.CENTER);
		
	}
	/**
	 * 是否正在计时
	 * @return true 表示正在计时，false 表示没有
	 */
	public boolean isTiming(){
		return this.task != null && this.task.getState() != Thread.State.TERMINATED;
	}
	/**
	 * 开始计时
	 */
	public void startTiming() {
		if(this.isTiming()){
			return;
		}
		this.task = new Thread(new Task());
		this.task.start();
	}
	/**
	 * 停止计时
	 */
	public void stopTiming() {
		if(this.task!=null){
			this.task.interrupt();
		}
	}
	/**
	 * 线程任务：更新视图，显示已用时间
	 * 
	 * @author ThinkBear
	 * @version 1.0.0
	 * @date 2017年2月24日
	 */
	private class Task implements Runnable {

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			while (true) {
				String info = (System.currentTimeMillis() - startTime) / 1000f + "'s";
				TimingLabel.this.setText(info);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					break;
				}
			}
		}

	}
}

package cn.thinkbear.gif.vo;

import java.io.File;

import cn.thinkbear.gif.utils.ConfigUtils;

/**
 * GIF 帧 对象
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月23日
 */
public class Frame {
	/**帧文件对象*/
	private File file;
	/**延迟时间*/
	private int delay = ConfigUtils.getInstance().getFrameDelay();
	public File getFile() {
		return this.file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
}

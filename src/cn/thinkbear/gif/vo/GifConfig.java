package cn.thinkbear.gif.vo;

import java.io.File;
import java.util.List;
/**
 * 帧采集结束后生成的数据对象
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class GifConfig {
	/**生成gif图片的宽*/
	private int width;
	/**生成gif图片的高*/
	private int height;
	/**gif图片输出文件*/
	private File outputFile;
	/**帧图片的存放目录*/
	private File frameDir;
	/**所有的帧图片对象数据*/
	private List<Frame> data;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public File getFrameDir() {
		return frameDir;
	}

	public void setFrameDir(File frameDir) {
		this.frameDir = frameDir;
	}

	public List<Frame> getData() {
		return data;
	}

	public void setData(List<Frame> data) {
		this.data = data;
	}
	
}

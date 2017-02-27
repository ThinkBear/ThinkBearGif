package cn.thinkbear.gif.gui;

import javax.swing.JComponent;

/**
 * 视图接口的抽象类<br>
 * 项目中含有组件的类都extends此抽象类<br>
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月23日
 */
public abstract class BaseView implements IView{
	/**
	 * 完成视图的创建工作
	 */
	public void doCreateGui(){
		this.doCase();
		this.doAppend();
		this.doSet();
	}
	/**
	 * 取得根容器
	 * @return 容器对象
	 */
	public abstract JComponent getRootPane();
}

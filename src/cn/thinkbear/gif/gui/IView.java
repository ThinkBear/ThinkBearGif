package cn.thinkbear.gif.gui;


/**
 * 视图接口
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月23日
 */
public interface IView {
	/**
	 * 完成所有组件初始化
	 */
	public void doCase();
	/**
	 * 将组件添加到面板
	 */
	public void doAppend();
	/**
	 * 组件的属性设置
	 */
	public void doSet();

}

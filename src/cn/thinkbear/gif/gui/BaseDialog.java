package cn.thinkbear.gif.gui;

import javax.swing.JDialog;

/**
 * 
 * 视图接口的抽象类<br>
 * 项目中含的对话框类都extends此抽象类<br>
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public abstract class BaseDialog implements IView {
	/**结果标记常量值*/
	public static final int OK = 0;
	/**记录结果标记*/
	private int result = -1;
	
	private JDialog dialog = null;

	public BaseDialog() {
		this.dialog = new JDialog();
		this.dialog.setIconImage(GuiFactory.getImageToIco("logo_30.png", 30).getImage());
	}

	public JDialog getDialog() {
		return this.dialog;
	}
	/**
	 * 取得结果值
	 * @return 当前的结果值
	 */
	public int getResult(){
		return this.result;
	}
	/**
	 * 设置结果值
	 * @param result 新的结果值
	 */
	public void setResult(int result){
		this.result = result;
	}

	/**
	 * 完成对话框的创建工作
	 */
	public void doCreateDialog() {
		this.doCase();
		this.doAppend();
		this.doSet();
	}
}

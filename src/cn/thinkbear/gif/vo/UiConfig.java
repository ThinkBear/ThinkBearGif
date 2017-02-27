package cn.thinkbear.gif.vo;

import java.awt.Color;
/**
 * 界面设置所用到的参数信息<br>
 * 
 * @see cn.thinkbear.gif.gui.dialog.set.UiSetPanel
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class UiConfig {
	private String waterMark;
	private boolean showWateMark;
	private Color waterMarkColor;
	private int waterMarkSize;
	
	private Color screenShotBorderColor;
	private int screenShotBorderSize;
	private boolean hasScreenShotBorder;
	
	private Color titleBarColor;
	private int icoSize;
	public String getWaterMark() {
		return waterMark;
	}
	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
	}
	public boolean isShowWateMark() {
		return showWateMark;
	}
	public void setShowWateMark(boolean showWateMark) {
		this.showWateMark = showWateMark;
	}
	public Color getWaterMarkColor() {
		return waterMarkColor;
	}
	public void setWaterMarkColor(Color waterMarkColor) {
		this.waterMarkColor = waterMarkColor;
	}
	public int getWaterMarkSize() {
		return waterMarkSize;
	}
	public void setWaterMarkSize(int waterMarkSize) {
		this.waterMarkSize = waterMarkSize;
	}
	public Color getScreenShotBorderColor() {
		return screenShotBorderColor;
	}
	public void setScreenShotBorderColor(Color screenShotBorderColor) {
		this.screenShotBorderColor = screenShotBorderColor;
	}
	public int getScreenShotBorderSize() {
		return screenShotBorderSize;
	}
	public void setScreenShotBorderSize(int screenShotBorderSize) {
		this.screenShotBorderSize = screenShotBorderSize;
	}
	public Color getTitleBarColor() {
		return titleBarColor;
	}
	public void setTitleBarColor(Color titleBarColor) {
		this.titleBarColor = titleBarColor;
	}
	public int getIcoSize() {
		return icoSize;
	}
	public void setIcoSize(int icoSize) {
		this.icoSize = icoSize;
	}
	public boolean isHasScreenShotBorder() {
		return hasScreenShotBorder;
	}
	public void setHasScreenShotBorder(boolean hasScreenShotBorder) {
		this.hasScreenShotBorder = hasScreenShotBorder;
	}
	
	
}

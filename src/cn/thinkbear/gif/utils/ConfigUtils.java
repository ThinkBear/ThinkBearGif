package cn.thinkbear.gif.utils;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import cn.thinkbear.gif.vo.UiConfig;

/**
 * 
 * 通过此工具类，取得相关配置参数<br>
 * 
 * 单例模式，线程安全<br>
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月23日
 */
public class ConfigUtils {
	private final String FILE_PATH = "source" + File.separator + "Config.properties";
	private Properties properties = null;

	public final static int MIN_SCREEN_WIDTH = 120;// 窗口最小宽度
	public final static int MIN_SCREEN_HEIGHT = 100;// 窗口最小高度

	private final String SCREEN_WIDTH = "screen_width";// app界面的宽
	private final String SCREEN_HEIGHT = "screen_height";// app界面的高
	private final String SCREEN_X = "screen_x";// app显示的x坐标
	private final String SCREEN_Y = "screen_y";// app显示的y坐标

	private final String SCREEN_SHOT_DELAY = "screen_shot_delay";// 截屏的时间间隔，单位毫秒
	private final String FRAME_DELAY = "frame_delay";// 默认帧动画延迟，单位毫秒
	private final String GIF_SAVE_DIR = "gif_save_dir";// gif保存目录
	private final String ICO_SIZE = "ico_size";// 图标大小
	private final String SCREEN_SHOT_SCALE = "screen_shot_scale";// 帧大小比例
	private final String AUTO_COPY_GIF = "auto_copy_gif";// 自动进行复制操作
	private final String AUTO_DELETE_FRAME_IMAGE = "auto_delete_frame_image";// 自动删除本地帧图片
	private final String TITLE_BAR_COLOR = "title_bar_color";// 标题栏背景颜色
	private final String SCREEN_SHOT_BORDER_COLOR = "screen_shot_border_color";// 帧采集区域的边框颜色
	private final String SCREEN_SHOT_BORDER_SIZE = "screen_shot_border_size";// 帧采集区域的边框大小
	private final String HAS_SCREEN_SHOT_BORDER = "has_screen_shot_border";// 是否含边框录制
	private final String WATER_MARK = "water_mark"; // 右下角水印文本
	private final String WATER_MARK_COLOR = "water_mark_color";// 水印文本颜色
	private final String WATER_MARK_SIZE = "water_mark_size";// 水印文本大小
	private final String SHOW_WATER_MARK = "show_water_mark";// 是否显示水印文本

	public static final String DEFAULT_GIF_SAVE_DIR = "output" + File.separator;
	public static final int DEFAULT_SCREEN_SHOT_DELAY = 200;
	public static final int DEFAULT_SCREEN_SHOT_SCALE = 100;
	public static final int DEFAULT_ICO_SIZE = 25;
	public static final int DEFAULT_WATER_MARK_SIZE = 10;
	public static final int DEFAULT_SCREEN_SHOT_BORDER_SIZE = 3;
	public static final int DEFAULT_FRAME_DELAY = 200;

	public static final int MIN_SCREEN_SHOT_DELAY = 0;
	public static final int MAX_SCREEN_SHOT_DELAY = 10000;

	public static final int MIN_SCREEN_SHOT_SCALE = 20;
	public static final int MAX_SCREEN_SHOT_SCALE = 200;

	public static final int MIN_FRAME_DELAY = 0;
	public static final int MAX_FRAME_DELAY = 5000;

	public static final int MIN_SCREEN_SHOT_BORDER_SIZE = 1;
	public static final int MAX_SCREEN_SHOT_BORDER_SIZE = 10;

	public static final int MIN_WATER_MARK_SIZE = 5;
	public static final int MAX_WATER_MARK_SIZE = 20;

	public static final int MIN_ICO_SIZE = 15;
	public static final int MAX_ICO_SIZE = 35;

	public static final Color DEFAULT_SCREEN_SHOT_BORDER_COLOR = Color.BLACK;
	public static final Color DEFAULT_TITLE_BAR_COLOR = Color.WHITE;
	public static final Color DEFAULT_WATER_MARK_COLOR = Color.WHITE;

	private Color screenShotBorderColor = null;
	private Color titleBarColor = null;
	private Color waterMarkColor = null;
	private boolean autoCopyGif = false;
	private boolean autoDeleteFrameImage = false;
	private boolean hasScreenShotBorder = false;
	private boolean showWaterMark = false;
	private int screenWidth = -1;
	private int screenHeight = -1;
	private int screenX = -1;
	private int screenY = -1;
	private int screenShotDelay = -1;
	private int screenShotScale = -1;
	private int screenShotBorderSize = -1;
	private int frameDelay = -1;
	private int icoSize = -1;
	private int waterMarkSize = -1;
	private String waterMark = null;
	private File gifSaveDir = null;

	private volatile static ConfigUtils instance = null;

	public static ConfigUtils getInstance() {
		if (instance == null) {
			synchronized (ConfigUtils.class) {
				if (instance == null) {
					instance = new ConfigUtils();
				}
			}
		}

		return instance;
	}

	private ConfigUtils() {
		this.properties = new Properties();
		try {
			this.properties.load(new FileInputStream(new File(FILE_PATH)));
		} catch (Exception e) {
		}
	}

	public String getValue(String key) {
		return this.properties.getProperty(key);
	}

	public int getValueByStringToInt(String value) {
		if (value == null) {
			return -1;
		}
		int num = 0;
		try {
			num = Integer.valueOf(value);
		} catch (NumberFormatException e) {
		}
		return num;
	}

	public int getScreenWidth() {
		if (this.screenWidth == -1) {
			this.screenWidth = this.getValueByStringToInt(this.getValue(SCREEN_WIDTH));
			this.screenWidth = this.screenWidth < MIN_SCREEN_WIDTH ? MIN_SCREEN_WIDTH : this.screenWidth;
		}
		return this.screenWidth;
	}

	public int getScreenHeight() {
		if (this.screenHeight == -1) {
			this.screenHeight = this.getValueByStringToInt(this.getValue(SCREEN_HEIGHT));
			this.screenHeight = this.screenHeight < MIN_SCREEN_HEIGHT ? MIN_SCREEN_HEIGHT : this.screenHeight;
		}

		return this.screenHeight;
	}

	public int getScreenX() {
		if (this.screenX == -1) {
			this.screenX = this.getValueByStringToInt(this.getValue(SCREEN_X));
			this.screenX = this.screenX < 0 ? 0 : this.screenX;
		}
		return screenX;
	}

	public void setScreenX(int screenX) {
		this.screenX = screenX;
		this.properties.setProperty(SCREEN_X, String.valueOf(this.screenX));
	}

	public int getScreenY() {
		if (this.screenY == -1) {
			this.screenY = this.getValueByStringToInt(this.getValue(SCREEN_Y));
			this.screenY = this.screenY < 0 ? 0 : this.screenY;
		}
		return screenY;
	}

	public void setScreenY(int screenY) {
		this.screenY = screenY;
		this.properties.setProperty(SCREEN_Y, String.valueOf(this.screenY));
	}

	public int getFrameDelay() {
		if (this.frameDelay == -1) {
			this.frameDelay = this.getValueByStringToInt(this.getValue(FRAME_DELAY));
			this.frameDelay = this.frameDelay < MIN_FRAME_DELAY || this.frameDelay > MAX_FRAME_DELAY
					? DEFAULT_FRAME_DELAY : this.frameDelay;
		}
		return this.frameDelay;
	}

	public int getScreenShotDelay() {
		if (this.screenShotDelay == -1) {
			this.screenShotDelay = this.getValueByStringToInt(this.getValue(SCREEN_SHOT_DELAY));
			this.screenShotDelay = this.screenShotDelay < MIN_SCREEN_SHOT_DELAY
					|| this.screenShotDelay > MAX_SCREEN_SHOT_DELAY ? DEFAULT_SCREEN_SHOT_DELAY : this.screenShotDelay;
		}
		return this.screenShotDelay;
	}

	public int getScreenShotScale() {
		if (this.screenShotScale == -1) {
			this.screenShotScale = this.getValueByStringToInt(this.getValue(SCREEN_SHOT_SCALE));
			this.screenShotScale = this.screenShotScale < MIN_SCREEN_SHOT_SCALE
					|| this.screenShotScale > MAX_SCREEN_SHOT_SCALE ? DEFAULT_SCREEN_SHOT_SCALE : this.screenShotScale;
		}
		return this.screenShotScale;
	}

	public File getGifSaveDir() {
		if (this.gifSaveDir == null) {
			String value = this.properties.getProperty(GIF_SAVE_DIR, DEFAULT_GIF_SAVE_DIR);
			this.gifSaveDir = new File(value);
			if (!this.gifSaveDir.isDirectory()) {
				this.gifSaveDir = new File(DEFAULT_GIF_SAVE_DIR);
			}
			if (!this.gifSaveDir.exists()) {
				this.gifSaveDir.mkdirs();
			}
		}

		return this.gifSaveDir;
	}

	public String getWaterMark() {
		if (this.waterMark == null) {
			this.waterMark = this.properties.getProperty(WATER_MARK, "");
		}
		return this.waterMark;
	}

	public int getIcoSize() {
		if (this.icoSize == -1) {
			this.icoSize = this.getValueByStringToInt(this.getValue(ICO_SIZE));
			this.icoSize = this.icoSize < MIN_ICO_SIZE || this.icoSize > MAX_ICO_SIZE ? DEFAULT_ICO_SIZE : this.icoSize;
		}
		return this.icoSize;
	}

	public Color getScreenShotBorderColor() {
		if (this.screenShotBorderColor == null) {
			String str = this.getValue(SCREEN_SHOT_BORDER_COLOR);
			this.screenShotBorderColor = str == null ? DEFAULT_SCREEN_SHOT_BORDER_COLOR
					: new Color(this.getValueByStringToInt(str));

		}
		return this.screenShotBorderColor;

	}

	public Color getTitleBarColor() {
		if (this.titleBarColor == null) {
			String str = this.getValue(TITLE_BAR_COLOR);
			this.titleBarColor = str == null ? DEFAULT_TITLE_BAR_COLOR : new Color(this.getValueByStringToInt(str));
		}
		return this.titleBarColor;

	}

	public int getScreenShotBorderSize() {
		if (this.screenShotBorderSize == -1) {
			this.screenShotBorderSize = this.getValueByStringToInt(this.getValue(SCREEN_SHOT_BORDER_SIZE));
			this.screenShotBorderSize = this.screenShotBorderSize < MIN_SCREEN_SHOT_BORDER_SIZE
					|| this.screenShotBorderSize > MAX_SCREEN_SHOT_BORDER_SIZE ? DEFAULT_SCREEN_SHOT_BORDER_SIZE
							: this.screenShotBorderSize;
		}
		return this.screenShotBorderSize;
	}

	public boolean isAutoCopyGif() {
		try {
			this.autoCopyGif = Boolean.valueOf(this.getValue(AUTO_COPY_GIF));
		} catch (NumberFormatException e) {
		}
		return this.autoCopyGif;
	}

	public boolean isAutoDeleteFrameImage() {
		try {
			this.autoDeleteFrameImage = Boolean.valueOf(this.getValue(AUTO_DELETE_FRAME_IMAGE));
		} catch (NumberFormatException e) {
		}
		return autoDeleteFrameImage;
	}

	public Color getWaterMarkColor() {
		if (this.waterMarkColor == null) {
			String str = this.getValue(WATER_MARK_COLOR);
			this.waterMarkColor = str == null ? DEFAULT_WATER_MARK_COLOR : new Color(this.getValueByStringToInt(str));
		}
		return this.waterMarkColor;
	}

	public void setWaterMarkColor(Color waterMarkColor) {
		this.waterMarkColor = waterMarkColor;
		this.properties.setProperty(WATER_MARK_COLOR, String.valueOf(this.waterMarkColor.getRGB()));
	}

	public boolean isHasScreenShotBorder() {
		try {
			this.hasScreenShotBorder = Boolean.valueOf(this.getValue(HAS_SCREEN_SHOT_BORDER));
		} catch (NumberFormatException e) {
		}
		return this.hasScreenShotBorder;
	}

	public void setHasScreenShotBorder(boolean hasScreenShotBorder) {
		this.hasScreenShotBorder = hasScreenShotBorder;
		this.properties.setProperty(HAS_SCREEN_SHOT_BORDER, String.valueOf(this.hasScreenShotBorder));
	}

	public boolean isShowWaterMark() {
		try {
			this.showWaterMark = Boolean.valueOf(this.getValue(SHOW_WATER_MARK));
		} catch (NumberFormatException e) {
		}
		return this.showWaterMark;
	}

	public void setShowWaterMark(boolean showWaterMark) {
		this.showWaterMark = showWaterMark;
		this.properties.setProperty(SHOW_WATER_MARK, String.valueOf(this.showWaterMark));
	}

	public int getWaterMarkSize() {
		if (this.waterMarkSize == -1) {
			this.waterMarkSize = this.getValueByStringToInt(this.getValue(WATER_MARK_SIZE));
			this.waterMarkSize = this.waterMarkSize < MIN_WATER_MARK_SIZE || this.waterMarkSize > MAX_WATER_MARK_SIZE
					? DEFAULT_WATER_MARK_SIZE : this.waterMarkSize;
		}
		return this.waterMarkSize;
	}

	public void setWaterMarkSize(int waterMarkSize) {
		this.waterMarkSize = waterMarkSize;
		this.properties.setProperty(WATER_MARK_SIZE, String.valueOf(this.waterMarkSize));
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
		this.properties.setProperty(SCREEN_WIDTH, String.valueOf(this.screenWidth));
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
		this.properties.setProperty(SCREEN_HEIGHT, String.valueOf(this.screenHeight));
	}

	public void setScreenShotDelay(int screenShotDelay) {
		this.screenShotDelay = screenShotDelay;
		this.properties.setProperty(SCREEN_SHOT_DELAY, String.valueOf(this.screenShotDelay));
	}

	public void setScreenShotScale(int screenShotScale) {
		this.screenShotScale = screenShotScale;
		this.properties.setProperty(SCREEN_SHOT_SCALE, String.valueOf(this.screenShotScale));
	}

	public void setFrameDelay(int frameDelay) {
		this.frameDelay = frameDelay;
		this.properties.setProperty(FRAME_DELAY, String.valueOf(this.frameDelay));
	}

	public void setGifSaveDir(File gifSaveDir) {
		this.gifSaveDir = gifSaveDir;
		this.properties.setProperty(GIF_SAVE_DIR, this.gifSaveDir.getAbsolutePath());
	}

	public void setIcoSize(int icoSize) {
		this.icoSize = icoSize;
		this.properties.setProperty(ICO_SIZE, String.valueOf(this.icoSize));
	}

	public void setScreenShotBorderColor(Color screenShotBorderColor) {
		this.screenShotBorderColor = screenShotBorderColor;
		this.properties.setProperty(SCREEN_SHOT_BORDER_COLOR, String.valueOf(this.screenShotBorderColor.getRGB()));
	}

	public void setTitleBarColor(Color titleBarColor) {
		this.titleBarColor = titleBarColor;
		this.properties.setProperty(TITLE_BAR_COLOR, String.valueOf(this.titleBarColor.getRGB()));
	}

	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
		this.properties.setProperty(WATER_MARK, this.waterMark);
	}

	public void setAutoCopyGif(boolean autoCopyGif) {
		this.autoCopyGif = autoCopyGif;
		this.properties.setProperty(AUTO_COPY_GIF, String.valueOf(this.autoCopyGif));
	}

	public void setAutoDeleteFrameImage(boolean autoDeleteFrameImage) {
		this.autoDeleteFrameImage = autoDeleteFrameImage;
		this.properties.setProperty(AUTO_DELETE_FRAME_IMAGE, String.valueOf(this.autoDeleteFrameImage));
	}

	public void setScreenShotBorderSize(int screenShotBorderSize) {
		this.screenShotBorderSize = screenShotBorderSize;
		this.properties.setProperty(SCREEN_SHOT_BORDER_SIZE, String.valueOf(this.screenShotBorderSize));
	}

	public UiConfig getUiConfig() {
		UiConfig config = new UiConfig();
		config.setWaterMark(this.getWaterMark());
		config.setWaterMarkColor(this.getWaterMarkColor());
		config.setWaterMarkSize(this.getWaterMarkSize());
		config.setShowWateMark(this.isShowWaterMark());
		config.setScreenShotBorderSize(this.getScreenShotBorderSize());
		config.setScreenShotBorderColor(this.getScreenShotBorderColor());
		config.setIcoSize(this.getIcoSize());
		config.setTitleBarColor(this.getTitleBarColor());
		config.setHasScreenShotBorder(this.isHasScreenShotBorder());
		return config;
	}

	public void setUiConfig(UiConfig config) {
		this.setWaterMark(config.getWaterMark());
		this.setWaterMarkColor(config.getWaterMarkColor());
		this.setWaterMarkSize(config.getWaterMarkSize());
		this.setShowWaterMark(config.isShowWateMark());
		this.setScreenShotBorderColor(config.getScreenShotBorderColor());
		this.setScreenShotBorderSize(config.getScreenShotBorderSize());
		this.setIcoSize(config.getIcoSize());
		this.setTitleBarColor(config.getTitleBarColor());
		this.setHasScreenShotBorder(config.isHasScreenShotBorder());
	}

	public void setScreenRect(Rectangle screen) {
		this.setScreenX(screen.x);
		this.setScreenY(screen.y);
		this.setScreenWidth(screen.width);
		this.setScreenHeight(screen.height);
	}

	public Rectangle getScreenRect() {
		Rectangle screen = new Rectangle();
		screen.x = this.getScreenX();
		screen.y = this.getScreenY();
		screen.width = this.getScreenWidth();
		screen.height = this.getScreenHeight();
		return screen;
	}

	public boolean save() {
		boolean flag = false;
		try (OutputStream out = new FileOutputStream(new File(FILE_PATH))) {
			this.properties.store(out, "");
			flag = true;
		} catch (Exception e) {

		}
		return flag;

	}
}

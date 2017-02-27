package cn.thinkbear.gif.gui.dialog.set;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.thinkbear.gif.gui.BaseView;
import cn.thinkbear.gif.gui.GuiFactory;
import cn.thinkbear.gif.gui.share.TitleLabel;
import cn.thinkbear.gif.utils.ConfigUtils;
/**
 * 基本设置<br><br>
 * 	帧采集设置 {@link IntTextFieldPanel}<br>
 * 	帧动画设置 {@link IntTextFieldPanel}<br>
 * 	输出目录设置 {@link GifSaveDirPanel}<br>
 * 	Gif生成后的操作 {@link GifFinishRadioPanel}<br>
 * 
 * <img src="doc-files/BaseSetPanel.png" alt="基本设置" style="margin: 10px">
 * 
 * @see IntTextFieldPanel
 * @see GifSaveDirPanel
 * @see GifFinishRadioPanel
 * 
 * @author ThinkBear
 * @version 1.0.0
 * @date 2017年2月24日
 */
public class BaseSetPanel extends BaseView {

	public static final int WIDTH = 400;
	public static final int SPACE = 5;
	
	private JPanel mainPanel = null;
	private IntTextFieldPanel screenShotDelay = null;
	private IntTextFieldPanel frameDelay = null;
	private IntTextFieldPanel screenShotScale = null;
	private GifFinishRadioPanel radioPanel = null;
	private GifSaveDirPanel gifSaveDir = null;
	private TitleLabel screenShotTitle = null;
	private TitleLabel frameTitle = null;
	private TitleLabel gifSaveDirTitle = null;
	private TitleLabel gifFinishTitle = null;
	private JLabel defaultLabel = null;
	private ConfigUtils config = null;


	public BaseSetPanel() {
		super.doCreateGui();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel();
		this.config = ConfigUtils.getInstance();
		this.screenShotDelay = new IntTextFieldPanel();
		this.frameDelay = new IntTextFieldPanel();
		this.screenShotScale = new IntTextFieldPanel();
		this.gifSaveDir = new GifSaveDirPanel();
		this.screenShotTitle = new TitleLabel("帧采集设置");
		this.frameTitle = new TitleLabel("帧动画设置");
		this.gifSaveDirTitle = new TitleLabel("输出目录设置");
		this.gifFinishTitle = new TitleLabel("Gif生成后");
		this.defaultLabel = new JLabel("默认值", JLabel.CENTER);
		this.radioPanel = new GifFinishRadioPanel();
	}

	@Override
	public void doAppend() {

		this.mainPanel.add(this.screenShotTitle);
		this.mainPanel.add(this.screenShotDelay.getRootPane());
		this.mainPanel.add(this.screenShotScale.getRootPane());
		this.mainPanel.add(this.frameTitle);
		this.mainPanel.add(this.frameDelay.getRootPane());
		this.mainPanel.add(this.gifSaveDirTitle);
		this.mainPanel.add(this.gifSaveDir.getRootPane());
		this.mainPanel.add(this.gifFinishTitle);
		this.mainPanel.add(this.radioPanel.getRootPane());
		this.mainPanel.add(this.defaultLabel);
		int y = SPACE;
		int title_w = WIDTH - SPACE * 2;
		int child_x = SPACE * 3;
		int child_w = WIDTH - SPACE * 5;
		this.screenShotTitle.setBounds(SPACE, y, title_w , 30);
		y = SPACE + y + 30;
		this.screenShotDelay.getRootPane().setBounds(child_x, y, child_w, 30);
		y = SPACE + y + 30;
		this.screenShotScale.getRootPane().setBounds(child_x, y, child_w, 30);
		y = SPACE * 2 + y + 30;
		this.frameTitle.setBounds(SPACE, y, title_w, 30);
		y = SPACE + y + 30;
		this.frameDelay.getRootPane().setBounds(child_x, y, child_w, 30);
		y = SPACE * 2 + y + 30;
		this.gifSaveDirTitle.setBounds(SPACE, y, title_w, 30);
		y = SPACE + y + 30;
		this.gifSaveDir.getRootPane().setBounds(SPACE * 3, y, child_w, 60);
		y = SPACE * 2 + y + 60;
		this.gifFinishTitle.setBounds(SPACE, y, title_w, 30);
		y = SPACE + y + 30;
		this.radioPanel.getRootPane().setBounds(child_x, y, child_w, 30);
		y = SPACE * 7 + y + 30;
		this.defaultLabel.setBounds(WIDTH - SPACE * 2 - 60, y, 60, 28);
		
	}

	@Override
	public void doSet() {
		this.mainPanel.setLayout(null);
		this.mainPanel.setOpaque(false);
		this.defaultLabel.setOpaque(true);
		this.defaultLabel.setForeground(Color.BLACK);
		this.defaultLabel.setFont(GuiFactory.FONT_AMONG_BOLD_OBJECT);
		this.defaultLabel.addMouseListener(new MyMouseEvent());
		this.screenShotDelay.initPara("采集延迟", "毫秒", ConfigUtils.MIN_SCREEN_SHOT_DELAY,
				ConfigUtils.MAX_SCREEN_SHOT_DELAY, 100, this.config.getScreenShotDelay());
		this.screenShotScale.initPara("大小比例", "%", ConfigUtils.MIN_SCREEN_SHOT_SCALE, ConfigUtils.MAX_SCREEN_SHOT_SCALE,
				10, this.config.getScreenShotScale());
		this.frameDelay.initPara("动画延迟", "毫秒", ConfigUtils.MIN_FRAME_DELAY, ConfigUtils.MAX_FRAME_DELAY, 100,
				this.config.getFrameDelay());
		this.gifSaveDir.setGifSaveDir(this.config.getGifSaveDir());
		this.radioPanel.setAutoCopyGif(this.config.isAutoCopyGif());
		this.radioPanel.setAutoDeleteFrameImage(this.config.isAutoDeleteFrameImage());
	}

	public void save() {
		this.config.setScreenShotDelay(this.screenShotDelay.getValue());
		this.config.setScreenShotScale(this.screenShotScale.getValue());
		this.config.setFrameDelay(this.frameDelay.getValue());
		this.config.setGifSaveDir(this.gifSaveDir.getGifSaveDir());
		this.config.setAutoCopyGif(this.radioPanel.isAutoCopyGif());
		this.config.setAutoDeleteFrameImage(this.radioPanel.isAutoDeleteFrameImage());
	}

	private class MyMouseEvent extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == defaultLabel) {
				screenShotDelay.setValue(ConfigUtils.DEFAULT_SCREEN_SHOT_DELAY);
				screenShotScale.setValue(ConfigUtils.DEFAULT_SCREEN_SHOT_SCALE);
				frameDelay.setValue(ConfigUtils.DEFAULT_FRAME_DELAY);
				gifSaveDir.setGifSaveDir(new File(ConfigUtils.DEFAULT_GIF_SAVE_DIR));
				radioPanel.setAutoCopyGif(true);
				radioPanel.setAutoDeleteFrameImage(false);
			}
		}
	}

	@Override
	public JComponent getRootPane() {
		return this.mainPanel;
	}

}

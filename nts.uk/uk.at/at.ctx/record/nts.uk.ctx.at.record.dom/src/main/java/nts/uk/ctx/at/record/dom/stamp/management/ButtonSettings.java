package nts.uk.ctx.at.record.dom.stamp.management;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * ボタン詳細設定
 * @author phongtq
 *
 */

public class ButtonSettings {

	/** ボタン位置NO */
	@Getter
	private int buttonPositionNo;
	
	/** ボタンの表示設定 */
	@Getter
	private ButtonDisSet buttonDisSet;
	
	/** ボタン種類 */
	@Getter
	private ButtonType buttonType;
	
	/** 使用区分 */
	@Getter
	private NotUseAtr usrArt;
	
	/** 音声使用方法 */
	@Getter
	private AudioType audioType;
}

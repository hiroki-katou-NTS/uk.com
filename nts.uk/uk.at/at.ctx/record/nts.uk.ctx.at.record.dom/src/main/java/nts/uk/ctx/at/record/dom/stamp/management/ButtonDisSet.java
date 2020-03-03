package nts.uk.ctx.at.record.dom.stamp.management;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * ボタンの表示設定
 * @author phongtq
 *
 */

public class ButtonDisSet {

	/** ボタン名称設定 */
	@Getter
	private ButtonNameSet buttonNameSet;
	
	/** 背景色 */
	@Getter
	private ColorCode backGroundColor;

	public ButtonDisSet(ButtonNameSet buttonNameSet, ColorCode backGroundColor) {
		this.buttonNameSet = buttonNameSet;
		this.backGroundColor = backGroundColor;
	}
}

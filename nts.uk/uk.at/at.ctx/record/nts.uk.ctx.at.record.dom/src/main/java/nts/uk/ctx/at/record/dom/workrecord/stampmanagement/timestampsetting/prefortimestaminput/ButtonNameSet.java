package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * ボタン名称設定
 * @author phongtq
 *
 */
public class ButtonNameSet {

	/** 文字色 */
	@Getter
	private ColorCode textColor;
	
	/** ボタン名称 */
	@Getter
	private Optional<ButtonName> buttonName;

	public ButtonNameSet(ColorCode textColor,ButtonName buttonName) {
		this.textColor = textColor;
		this.buttonName = Optional.ofNullable(buttonName);
	}
}

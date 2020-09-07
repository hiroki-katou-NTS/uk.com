package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;

/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class ButtonNameSetDto {
	/** 文字色 */
	private String textColor;

	/** ボタン名称 */
	private String buttonName;

	public static ButtonNameSetDto fromDomain(ButtonNameSet buttonNameSet) {
		return new ButtonNameSetDto(buttonNameSet.getTextColor().v(),
				buttonNameSet.getButtonName().isPresent() ? buttonNameSet.getButtonName().get().v() : null);
	}
}

package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class ButtonDisSetDto {
	/** ボタン名称設定 */
	private ButtonNameSetDto buttonNameSet;
	
	/** 背景色 */
	private String backGroundColor;

	public static ButtonDisSetDto fromDomain(ButtonDisSet buttonDisSet) {
		return new ButtonDisSetDto(ButtonNameSetDto.fromDomain(buttonDisSet.getButtonNameSet()),
				buttonDisSet.getBackGroundColor().v());
	}
}

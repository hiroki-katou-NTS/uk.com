package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class ButtonDisSetCommand {
	/** ボタン名称設定 */
	private ButtonNameSetCommand buttonNameSet;

	/** 背景色 */
	private String backGroundColor;

	public ButtonDisSetCommand(ButtonNameSetCommand buttonNameSet, String backGroundColor) {
		this.buttonNameSet = buttonNameSet;
		this.backGroundColor = backGroundColor;
	}
	
	public ButtonDisSet toDomain() {
		return new ButtonDisSet(buttonNameSet.toDomain(), new ColorCode(this.backGroundColor));
	}
}

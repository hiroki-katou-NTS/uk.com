package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class ButtonNameSetCommand {
	/** 文字色 */
	private String textColor;
	
	/** ボタン名称 */
	@Getter
	private String buttonName;

	public ButtonNameSetCommand(String textColor, String buttonName) {
		this.textColor = textColor;
		this.buttonName = buttonName;
	}
	
	public ButtonNameSet toDomain() {
		return new ButtonNameSet(new ColorCode(this.textColor), new ButtonName(this.buttonName));
	}
}

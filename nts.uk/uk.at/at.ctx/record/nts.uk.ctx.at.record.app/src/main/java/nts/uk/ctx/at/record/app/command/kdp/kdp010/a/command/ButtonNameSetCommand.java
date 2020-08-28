package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * 
 * @author ThanhPV
 *
 */
@Data
@NoArgsConstructor
public class ButtonNameSetCommand {

	private String textColor;

	private String buttonName;

	public ButtonNameSet toDomain() {
		return new ButtonNameSet(new ColorCode(this.textColor), this.buttonName == null?null : new ButtonName(this.buttonName));
	}
}

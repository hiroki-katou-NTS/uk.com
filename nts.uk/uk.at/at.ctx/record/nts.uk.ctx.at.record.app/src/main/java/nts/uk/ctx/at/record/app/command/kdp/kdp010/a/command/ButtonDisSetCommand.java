package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
/**
 * 
 * @author ThanhPV
 *
 */
@Data
@NoArgsConstructor
public class ButtonDisSetCommand {

	private ButtonNameSetCommand buttonNameSet;
	
	private String backGroundColor;

	public ButtonDisSet toDomain() {
		return new ButtonDisSet(this.buttonNameSet.toDomain(), new ColorCode(this.backGroundColor));
	}
}

package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * @author thanhpv
 *
 */
@NoArgsConstructor
@Data
public class SettingDateTimeColorOfStampScreenCommand {
	
	private String textColor;

	public SettingDateTimeColorOfStampScreen toDomain() {
		return new SettingDateTimeColorOfStampScreen(new ColorCode(this.textColor));
	}
}

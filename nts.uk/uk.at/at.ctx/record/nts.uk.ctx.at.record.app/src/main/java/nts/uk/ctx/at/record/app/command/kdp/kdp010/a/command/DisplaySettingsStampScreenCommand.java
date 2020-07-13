package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;

/**
 * 
 * @author thanhPV
 *
 */
@NoArgsConstructor
@Data
public class DisplaySettingsStampScreenCommand {

	private Integer serverCorrectionInterval;

	private SettingDateTimeColorOfStampScreenCommand settingDateTimeColor;

	private Integer resultDisplayTime;

	public DisplaySettingsStampScreen toDomain() {
		return new DisplaySettingsStampScreen(new CorrectionInterval(this.serverCorrectionInterval), this.settingDateTimeColor.toDomain(), new ResultDisplayTime(this.resultDisplayTime));
	}
}

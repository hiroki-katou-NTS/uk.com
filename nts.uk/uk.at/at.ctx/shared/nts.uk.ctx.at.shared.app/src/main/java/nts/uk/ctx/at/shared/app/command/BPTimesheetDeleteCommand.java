package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPTimesheetDeleteCommand {
	public String companyId;
	public int timeSheetNO;
	public int useAtr;
	public String bonusPaySettingCode;
	public String timeItemId;
	public int startTime;
	public int endTime;
	public int roundingTimeAtr;
	public int roundingAtr;
}

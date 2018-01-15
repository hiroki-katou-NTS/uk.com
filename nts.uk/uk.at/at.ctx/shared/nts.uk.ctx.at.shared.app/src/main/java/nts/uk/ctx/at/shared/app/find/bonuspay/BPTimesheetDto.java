package nts.uk.ctx.at.shared.app.find.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPTimesheetDto {
	public String companyId;
	//sheet id
	public int timeSheetNO;
	public int useAtr;
	public String bonusPaySettingCode;
	//item no
	public int timeItemID;
	public int startTime;
	public int endTime;
	public int roundingTimeAtr;
	public int roundingAtr;
}

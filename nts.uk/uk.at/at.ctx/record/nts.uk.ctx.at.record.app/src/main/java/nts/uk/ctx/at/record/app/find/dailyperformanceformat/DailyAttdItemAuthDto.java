package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class DailyAttdItemAuthDto {
	
	public int attendanceItemId;

	public String authorityId;

	public boolean youCanChangeIt;

	public boolean canBeChangedByOthers;

	public boolean use;

	public boolean userCanSet;


}

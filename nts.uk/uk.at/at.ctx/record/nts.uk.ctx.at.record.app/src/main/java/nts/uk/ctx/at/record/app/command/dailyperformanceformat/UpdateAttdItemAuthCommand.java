package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class UpdateAttdItemAuthCommand {
	
	public int attendanceItemId;

	public String authorityId;

	public boolean youCanChangeIt;

	public boolean canBeChangedByOthers;

	public boolean use;

}

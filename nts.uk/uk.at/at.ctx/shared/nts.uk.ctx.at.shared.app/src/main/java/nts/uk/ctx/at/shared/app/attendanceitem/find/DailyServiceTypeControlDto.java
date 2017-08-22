package nts.uk.ctx.at.shared.app.attendanceitem.find;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class DailyServiceTypeControlDto {
	public int attendanceItemId;
	
	public String attendanceItemName;

	public String businessTypeCode;

	public boolean use;
	
	public boolean youCanChangeIt;

	public boolean canBeChangedByOthers;
	
	public boolean userCanSet;
}

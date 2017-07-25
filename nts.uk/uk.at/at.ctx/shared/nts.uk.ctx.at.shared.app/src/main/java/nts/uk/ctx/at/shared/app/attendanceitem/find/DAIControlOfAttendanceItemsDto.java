package nts.uk.ctx.at.shared.app.attendanceitem.find;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class DAIControlOfAttendanceItemsDto {
	public String attendanceItemId;

	public String workTypeCode;

	public String attendanceItemName;

	public boolean userCanSet;

	public boolean youCanChangeIt;

	public boolean canBeChangedByOthers;
	
	public boolean use;
}

package nts.uk.ctx.at.schedule.app.command.shift.management.shifttable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftTableSaveCommand {
	private int unit;
	private String workplaceId;
	private String workplaceGroupId;
	private String publicDate;
	private String editDate;
	
	public GeneralDate toPublicDate() {
		return GeneralDate.fromString(publicDate, "yyyy/MM/dd");
	}
	
	public GeneralDate toEditDate() {
		if(editDate == null || "".equals(editDate)) {
			return null;
		}
		return GeneralDate.fromString(editDate, "yyyy/MM/dd");
	}

}

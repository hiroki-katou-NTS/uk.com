package nts.uk.ctx.at.record.app.command.remainingnumber.dayoffmanagement;

import java.util.List;

import lombok.Getter;


@Getter
public class DayOffManaCommand {
	
	private List<DayOffManaDto> daysOffMana;
	private String employeeId;
	private String leaveId;
}

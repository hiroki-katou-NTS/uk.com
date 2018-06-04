package nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana;

import java.util.List;

import lombok.Getter;

@Getter
public class LeaveManaComand {
	
	private String employeeId;
	private String comDayOffID;
	private List<LeaveManaDto> leaveManaDtos;
	private String numberDayParam;
	
}

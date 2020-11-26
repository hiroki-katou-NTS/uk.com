package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.List;

import lombok.Getter;

@Getter
public class DeleteLeaveManagementDataCommand {
	private List<String> leaveId;
	private List<String> comDayOffID;
}

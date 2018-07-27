package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeavesManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.LeaveManagementService;

@Stateless
public class LeaveManaCommandHandler extends CommandHandlerWithResult<LeaveManaComand,List<String>> {
	
	@Inject
	private LeaveManagementService leaveManagementService;
	
	@Override
	protected List<String> handle(CommandHandlerContext<LeaveManaComand> context) {
		LeaveManaComand leaveManaComand = context.getCommand();
		LeaveManaData leaveManagementData = new LeaveManaData(
				leaveManaComand.getLeaveManaDtos().stream()
						.map(item -> new LeavesManaData(item.getLeaveManaID(), item.getDayOff(), item.getRemainDays()))
						.collect(Collectors.toList()),
				leaveManaComand.getEmployeeId(), leaveManaComand.getComDayOffID(), leaveManaComand.getNumberDayParam());
		return leaveManagementService.updateDayOff(leaveManagementData);
	} 

}

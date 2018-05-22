/**
 * 
 */
package nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.AddSubHdManagementService;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.SubHdManagementData;

/**
 * @author nam.lh
 *
 */

@Stateless
public class AddSubHdManagementCommandHandler
		extends CommandHandlerWithResult<AddSubHdManagementCommand, List<String>> {
	@Inject
	private ComDayOffManaDataRepository repoComday;
	@Inject
	private LeaveManaDataRepository repoLeaveMana;
	@Inject
	private LeaveComDayOffManaRepository repoLeaveComday;
	@Inject
	private AddSubHdManagementService serviceAddSub;

	@Override
	protected List<String> handle(CommandHandlerContext<AddSubHdManagementCommand> context) {
		List<String> errors = new ArrayList<>();
		AddSubHdManagementCommand command = context.getCommand();
		SubHdManagementData subHdManagementData = new SubHdManagementData(command.getEmployeeId(),
				command.getCheckedHoliday(), command.getDateHoliday(), command.getSelectedCodeHoliday(),
				command.getDuedateHoliday(), command.getCheckedSubHoliday(), command.getDateSubHoliday(),
				command.getSelectedCodeSubHoliday(), command.getCheckedSplit(), command.getDateOptionSubHoliday(),
				command.getSelectedCodeOptionSubHoliday(), command.getDayRemaining(), command.getClosureId());
		
		//代休管理データの新規追加処理
		serviceAddSub.addProcessOfSHManagement(subHdManagementData);
		
		return errors;
	}

}

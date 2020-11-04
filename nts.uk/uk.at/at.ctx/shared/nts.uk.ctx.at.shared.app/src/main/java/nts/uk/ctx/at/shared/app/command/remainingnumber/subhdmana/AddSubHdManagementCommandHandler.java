/**
 * 
 */
package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.AddSubHdManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.SubHdManagementData;

/**
 * @author nam.lh
 *
 */

@Stateless
public class AddSubHdManagementCommandHandler
		extends CommandHandlerWithResult<AddSubHdManagementCommand, List<String>> {

	@Inject
	private AddSubHdManagementService serviceAddSub;

	@Override
	protected List<String> handle(CommandHandlerContext<AddSubHdManagementCommand> context) {
		AddSubHdManagementCommand command = context.getCommand();
		SubHdManagementData subHdManagementData = new SubHdManagementData(command.getEmployeeId(),
				command.getCheckedHoliday(), command.getDateHoliday(), command.getSelectedCodeHoliday(),
				command.getDuedateHoliday(), command.getCheckedSubHoliday(), command.getDateSubHoliday(),
				command.getSelectedCodeSubHoliday(), command.getCheckedSplit(), command.getDateOptionSubHoliday(),
				command.getSelectedCodeOptionSubHoliday(), command.getDayRemaining(), command.getClosureId(), command.getLstLinkingDate());

		// 代休管理データの新規追加処理
		List<String> errors = serviceAddSub.addProcessOfSHManagement(subHdManagementData);
		return errors;
	}
}

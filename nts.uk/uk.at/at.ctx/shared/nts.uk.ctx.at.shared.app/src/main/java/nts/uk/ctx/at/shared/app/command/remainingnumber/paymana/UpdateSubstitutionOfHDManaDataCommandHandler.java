package nts.uk.ctx.at.shared.app.command.remainingnumber.paymana;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataService;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateSubstitutionOfHDManaDataCommandHandler
		extends CommandHandlerWithResult<UpdateSubstitutionOfHDManaDataCommand, List<String>> {
	@Inject
	private SubstitutionOfHDManaDataService substitutionOfHDManaDataService;

	@Override
	protected List<String> handle(CommandHandlerContext<UpdateSubstitutionOfHDManaDataCommand> context) {
		UpdateSubstitutionOfHDManaDataCommand command = context.getCommand();
		String cID = AppContexts.user().companyId();

		SubstitutionOfHDManagementData data = new SubstitutionOfHDManagementData(command.getSubOfHDID(), cID,
				command.getEmployeeId(), command.getDayoffDate() != null ? false: true, command.getDayoffDate(), command.getRequiredDays(),
				command.getRemainDays());
		return substitutionOfHDManaDataService.updateSubOfHD(data, command.getClosureId());
	}

}

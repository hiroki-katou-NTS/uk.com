package nts.uk.ctx.pereg.app.command.addemployee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author sonnlb
 *
 */
@Stateless
public class AddEmployeeCommandHandler extends CommandHandlerWithResult<AddEmployeeCommand, String> {

	@Inject
	private AddEmployeeCommandHelper helper;

	@Inject
	private AddEmployeeCommandFacade addEmployeeProcess;

	@Override
	protected String handle(CommandHandlerContext<AddEmployeeCommand> context) {

		val command = context.getCommand();

		String employeeId = IdentifierUtil.randomUniqueId();

		String userId = IdentifierUtil.randomUniqueId();

		String personId = IdentifierUtil.randomUniqueId();

		String companyId = AppContexts.user().companyId();

		String comHistId = IdentifierUtil.randomUniqueId();

		helper.addBasicData(command, personId, employeeId, comHistId, companyId, userId);

		// call commandFacade
		// helper.inputsProcess(command, personId, employeeId, comHistId);
		addEmployeeProcess.addNewFromInputs(command, personId, employeeId, comHistId);
		// Lai di@@

		return employeeId;

	}

}

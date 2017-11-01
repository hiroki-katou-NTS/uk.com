package nts.uk.ctx.bs.employee.app.command.employee.deletemanagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.deleteEmpmanagement.DeleteEmpManagement;
import nts.uk.ctx.bs.employee.dom.deleteEmpmanagement.DeleteEmpRepository;
import nts.uk.ctx.bs.employee.dom.deleteEmpmanagement.ReasonRemoveEmp;

@Stateless
@Transactional
public class EmployeeDeleteCommandHandler extends CommandHandler<EmployeeDeleteCommand> {

	/** The repository. */
	@Inject
	private DeleteEmpRepository deleteEmpRepo;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteCommand> context) {

		// get command
		EmployeeDeleteCommand command = context.getCommand();

		// getting current date and time using Date class
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateobj = new Date();
		

		if (command != null) {
			// 0 : deleted = false , 1 : deleted = true;
			DeleteEmpManagement domain = new DeleteEmpManagement(0, command.getSId(),
					GeneralDate.fromString(df.format(dateobj), "yyyy-MM-dd HH:mm:ss"), new ReasonRemoveEmp(command.getReason()));
			deleteEmpRepo.insertToDeleteEmpManagemrnt(domain);
		}

	}

}

package nts.uk.ctx.sys.auth.app.command.grant.rolesetperson;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPerson;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RegisterRoleSetGrantedPersonCommandHandler extends CommandHandler<RoleSetGrantedPersonCommand> {
	@Inject
	private RoleSetGrantedPersonRepository roleSetPersonRepo;

	@Override
	protected void handle(CommandHandlerContext<RoleSetGrantedPersonCommand> context) {
		RoleSetGrantedPersonCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		System.out.println("screen mode: " + command.getMode());
		if (command.getMode() == 0) {
			roleSetPersonRepo.insert(new RoleSetGrantedPerson(command.getRoleSetCd(), companyId, command.getStartDate(),
					command.getEndDate(), command.getEmployeeId()));

			// dummy code
			// System.out.println("Adding new RoleSetGrantedPerson: EmpId = " +
			// command.getEmployeeId() + " - RoleSetCode = "
			// + command.getRoleSetCd() + " - period= " + command.getStartDate()
			// + " ~ " + command.getEndDate());
			// System.out.println("Register done!");
		} else {
			roleSetPersonRepo.update(new RoleSetGrantedPerson(command.getRoleSetCd(), companyId, command.getStartDate(),
					command.getEndDate(), command.getEmployeeId()));

			// dummy code
			// System.out.println("Updating RoleSetGrantedPerson: EmpId = " +
			// command.getEmployeeId() + " - RoleSetCode = "
			// + command.getRoleSetCd() + " - period= " + command.getStartDate()
			// + " ~ " + command.getEndDate());
			// System.out.println("Register done!");
		}
	}
}

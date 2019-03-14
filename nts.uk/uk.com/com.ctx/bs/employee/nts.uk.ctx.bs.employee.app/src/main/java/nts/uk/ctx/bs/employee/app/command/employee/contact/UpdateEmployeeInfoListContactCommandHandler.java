package nts.uk.ctx.bs.employee.app.command.employee.contact;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContact;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContactRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateEmployeeInfoListContactCommandHandler  extends CommandHandler<List<UpdateEmployeeInfoContactCommand>>
implements PeregUpdateListCommandHandler<UpdateEmployeeInfoContactCommand>{
	@Inject
	private EmployeeInfoContactRepository employeeInfoContactRepository;
	@Override
	public String targetCategoryCd() {
		return "CS00023";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmployeeInfoContactCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<List<UpdateEmployeeInfoContactCommand>> context) {
		List<UpdateEmployeeInfoContactCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<EmployeeInfoContact> domains = cmd.parallelStream().map(c ->{return new EmployeeInfoContact(cid, c.getSid(), c.getMailAddress(),
				c.getSeatDialIn(), c.getSeatExtensionNo(), c.getPhoneMailAddress(),
				c.getCellPhoneNo());}).collect(Collectors.toList());
		if(domains.isEmpty()) {
			employeeInfoContactRepository.addAll(domains);
		}
	}

}

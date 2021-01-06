package nts.uk.ctx.bs.employee.app.command.employee.contact;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.bs.employee.dom.employee.data.management.contact.EmployeeContactRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateEmployeeInfoListContactCommandHandler  extends CommandHandlerWithResult<List<UpdateEmployeeInfoContactCommand>, List<MyCustomizeException>>
implements PeregUpdateListCommandHandler<UpdateEmployeeInfoContactCommand>{
	@Inject
	private EmployeeContactRepository employeeInfoContactRepository;
	@Override
	public String targetCategoryCd() {
		return "CS00023";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmployeeInfoContactCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateEmployeeInfoContactCommand>> context) {
		List<UpdateEmployeeInfoContactCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		//TODO
//		List<EmployeeInfoContact> domains = cmd.stream().map(c ->{return new EmployeeInfoContact(cid, c.getSid(), c.getMailAddress(),
//				c.getSeatDialIn(), c.getSeatExtensionNo(), c.getPhoneMailAddress(),
//				c.getCellPhoneNo());}).collect(Collectors.toList());
//		if(!domains.isEmpty()) {
//			employeeInfoContactRepository.updateAll(domains);
//		}
		return new ArrayList<MyCustomizeException>();
	}

}
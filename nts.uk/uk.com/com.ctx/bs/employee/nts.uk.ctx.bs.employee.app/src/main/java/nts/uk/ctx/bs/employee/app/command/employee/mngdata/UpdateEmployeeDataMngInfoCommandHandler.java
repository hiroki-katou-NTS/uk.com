package nts.uk.ctx.bs.employee.app.command.employee.mngdata;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateEmployeeDataMngInfoCommandHandler extends CommandHandler<UpdateEmployeeDataMngInfoCommand>
	implements PeregUpdateCommandHandler<UpdateEmployeeDataMngInfoCommand>{
	
	@Inject
	private EmployeeDataMngInfoRepository employeeDataMngInfoRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00001";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmployeeDataMngInfoCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateEmployeeDataMngInfoCommand> context) {
		
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		EmployeeDataMngInfo domain = new EmployeeDataMngInfo(companyId,command.getPersonId(), command.getEmployeeId(),command.getEmployeeCode(),command.getExternalCode());
		
		employeeDataMngInfoRepository.update(domain);
	}

}

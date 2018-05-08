package nts.uk.ctx.sys.assist.app.command.system;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.assist.dom.system.SystemTypeAdapter;
import nts.uk.ctx.sys.assist.dom.system.SystemTypeEnum;
import nts.uk.ctx.sys.assist.dom.system.SystemTypeImport;
import nts.uk.ctx.sys.assist.dom.system.SystemTypeResult;

@Stateless
public class SystemTypeCommandHandler extends CommandHandlerWithResult<SystemTypeCommand, List<SystemTypeResult>> {
	@Inject
	private SystemTypeAdapter systemTypeAdapter;

	@Override
	protected List<SystemTypeResult> handle(CommandHandlerContext<SystemTypeCommand> context) {
	
		SystemTypeImport temp = systemTypeAdapter.getSystemTypeByEmpId();
		List<SystemTypeResult> lstSystemType = new ArrayList<>();

		if(temp.isPersonalInformation()){
			lstSystemType.add(new SystemTypeResult(SystemTypeEnum.POSSIBILITY_SYSTEM.name, SystemTypeEnum.POSSIBILITY_SYSTEM.value));
		}

		if(temp.isHumanResOfficer()){
			lstSystemType.add(new SystemTypeResult(SystemTypeEnum.ATTENDANCE_SYSTEM.name, SystemTypeEnum.ATTENDANCE_SYSTEM.value));
		}

		if(temp.isSalaryProfessional()){
			lstSystemType.add(new SystemTypeResult(SystemTypeEnum.PAYROLL_SYSTEM.name, SystemTypeEnum.PAYROLL_SYSTEM.value));
		}

		if(temp.isOfficeHelperPersonne()){
			lstSystemType.add(new SystemTypeResult(SystemTypeEnum.OFFICE_HELPER.name, SystemTypeEnum.OFFICE_HELPER.value));
		}
		
		return lstSystemType;
	}

}

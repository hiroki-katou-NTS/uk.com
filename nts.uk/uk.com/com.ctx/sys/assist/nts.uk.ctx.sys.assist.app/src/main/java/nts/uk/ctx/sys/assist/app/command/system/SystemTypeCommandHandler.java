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

		if(temp.isHumanResOfficer()){
			lstSystemType.add(new SystemTypeResult(SystemTypeEnum.HUMAN_RES_OFFICER_SYSTEM.name, SystemTypeEnum.HUMAN_RES_OFFICER_SYSTEM.value));
		}

		if(temp.isEmployeeCharge()){
			lstSystemType.add(new SystemTypeResult(SystemTypeEnum.EMPLOYMENT_SYSTEM.name, SystemTypeEnum.EMPLOYMENT_SYSTEM.value));
		}

		if(temp.isSalaryProfessional()){
			lstSystemType.add(new SystemTypeResult(SystemTypeEnum.SALARY_PROFESSIONAL_SYSTEM.name, SystemTypeEnum.SALARY_PROFESSIONAL_SYSTEM.value));
		}

		if(temp.isOfficeHelperPersonne()){
			lstSystemType.add(new SystemTypeResult(SystemTypeEnum.OFFICE_HELPER_SYSTEM.name, SystemTypeEnum.OFFICE_HELPER_SYSTEM.value));
		}
		
		return lstSystemType;
	}

}

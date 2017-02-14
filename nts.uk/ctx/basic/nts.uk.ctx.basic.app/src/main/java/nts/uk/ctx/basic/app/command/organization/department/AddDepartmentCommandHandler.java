package nts.uk.ctx.basic.app.command.organization.department;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentCode;
import nts.uk.ctx.basic.dom.organization.department.DepartmentDomainService;
import nts.uk.ctx.basic.dom.organization.department.DepartmentFullName;
import nts.uk.ctx.basic.dom.organization.department.DepartmentName;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddDepartmentCommandHandler extends CommandHandler<AddDepartmentCommand> {

	@Inject
	private DepartmentDomainService departmentDomainService;

	@Override
	protected void handle(CommandHandlerContext<AddDepartmentCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		Department department = new Department(companyCode,
				new DepartmentCode(context.getCommand().getDepartmentCode()),
				GeneralDate.legacyDate(context.getCommand().getEndDate()),
				new DepartmentCode(context.getCommand().getExternalCode()),
				new DepartmentFullName(context.getCommand().getFullName()),
				new HierarchyCode(context.getCommand().getHierarchyCode()),
				new DepartmentName(context.getCommand().getName()),
				GeneralDate.legacyDate(context.getCommand().getStartDate()));
		departmentDomainService.add(department);

	}

}

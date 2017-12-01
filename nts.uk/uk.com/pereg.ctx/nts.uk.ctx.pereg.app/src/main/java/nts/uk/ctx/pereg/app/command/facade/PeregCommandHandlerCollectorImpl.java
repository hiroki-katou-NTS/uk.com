package nts.uk.ctx.pereg.app.command.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import command.person.currentaddress.AddCurrentAddressCommand;
import command.person.family.AddFamilyCommand;
import command.person.info.UpdatePersonCommand;
import nts.uk.ctx.bs.employee.app.command.department.AddAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.department.AddCurrentAffiDeptCommand;
import nts.uk.ctx.bs.employee.app.command.department.DeleteAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.department.DeleteCurrentAffiDeptCommand;
import nts.uk.ctx.bs.employee.app.command.department.UpdateAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.UpdateAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.mngdata.UpdateEmployeeDataMngInfoCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.UpdateEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.familyrelatedinformation.care.DeleteFamilyCareCommand;
import nts.uk.ctx.bs.employee.app.command.familyrelatedinformation.incometax.DeleteIncomeTaxCommand;
import nts.uk.ctx.bs.employee.app.command.familyrelatedinformation.socialinsurance.DeleteFamilySocialInsuranceCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.AddAffJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.main.DeleteJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.main.UpdateJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.position.jobposition.AddSubJobPositionCommand;
import nts.uk.ctx.bs.employee.app.command.position.jobposition.DeleteSubJobPositionCommand;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.AddTemporaryAbsenceCommand;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.DeleteTemporaryAbsenceCommand;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.UpdateTemporaryAbsenceCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.AddAffWorkplaceHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.DeleteAffWorkplaceHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.UpdateAffWorkplaceHistoryCommand;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregCommandHandlerCollector;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.*;
import nts.uk.ctx.bs.employee.app.command.employee.history.*;
import  nts.uk.ctx.bs.employee.app.command.employment.history.*;

@Stateless
@SuppressWarnings("serial")
public class PeregCommandHandlerCollectorImpl implements PeregCommandHandlerCollector {

	/** Add handlers */
	private static final List<TypeLiteral<?>> ADD_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregAddCommandHandler<AddAffiliationDepartmentCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddSubJobPositionCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffWorkplaceHistoryCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddCurrentAddressCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddPerEmergencyContactCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddFamilyCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddWidowHistoryCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddEmployeeCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffJobTitleMainCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddCurrentAffiDeptCommand>>(){}
			new TypeLiteral<PeregAddCommandHandler<AddEmploymentHistoryCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffCompanyHistoryCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffClassificationCommand>>(){}
			);
	
	/** Update handlers */
	private static final List<TypeLiteral<?>> UPDATE_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffiliationDepartmentCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateJobTitleMainCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateFamilyCareCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateIncomeTaxCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateFamilySocialInsuranceCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateSubJobPositionCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffWorkplaceHistoryCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateCurrentAddressCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdatePerEmergencyContactCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateFamilyCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdatePersonCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateWidowHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmployeeDataMngInfoCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateAssignedWorkplaceCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateCurrentAffiDeptCommand>>(){}
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmploymentHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffCompanyHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffClassificationCommand>>(){}
			);
	
	/** Delete handlers */
	private static final List<TypeLiteral<?>> DELETE_HANDLER_CLASSES = Arrays.asList(
//			new TypeLiteral<PeregDeleteCommandHandler<DeleteFamilyCareCommand>>(){},
//			new TypeLiteral<PeregDeleteCommandHandler<DeleteIncomeTaxCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffiliationDepartmentCommand>>(){},
//			new TypeLiteral<PeregDeleteCommandHandler<DeleteCurrentAffiDeptCommand>>(){},
//			new TypeLiteral<PeregDeleteCommandHandler<DeleteFamilySocialInsuranceCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteJobTitleMainCommand>>(){},
//			new TypeLiteral<PeregDeleteCommandHandler<DeleteSubJobPositionCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffWorkplaceHistoryCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmploymentHistoryCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffCompanyHistoryCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffClassificationCommand>>(){}
			);
	
	@Override
	public Set<PeregAddCommandHandler<?>> collectAddHandlers() {
		return ADD_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregAddCommandHandler<?>)obj)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<PeregUpdateCommandHandler<?>> collectUpdateHandlers() {
		return UPDATE_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregUpdateCommandHandler<?>)obj)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<PeregDeleteCommandHandler<?>> collectDeleteHandlers() {
		return DELETE_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregDeleteCommandHandler<?>)obj)
				.collect(Collectors.toSet());
	}

}

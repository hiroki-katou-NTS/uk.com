package nts.uk.ctx.pereg.app.command.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import command.person.contact.AddPerContactCommand;
import command.person.contact.DeletePerContactCommand;
import command.person.contact.UpdatePerContactCommand;
import command.person.info.AddPersonCommand;
import command.person.info.UpdatePersonCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.AddBusinessWokrTypeOfHistoryCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.DeleteBusinessWorkTypeOfHistoryCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.UpdateBusinessWorkTypeOfHistoryCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.annualeave.AddAnnuaLeaveCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.annualeave.DeleteAnnuaLeaveCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.annualeave.UpdateAnnuaLeaveCommand;
import nts.uk.ctx.at.shared.app.command.shortworktime.AddShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.shortworktime.DeleteShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.shortworktime.UpdateShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.AddWorkingConditionCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.DeleteWorkingConditionCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.UpdateWorkingConditionCommand;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.AddAffClassificationCommand;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.DeleteAffClassificationCommand;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.UpdateAffClassificationCommand;
import nts.uk.ctx.bs.employee.app.command.department.AddAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.department.DeleteAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.department.UpdateAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.employee.contact.AddEmployeeInfoContactCommand;
import nts.uk.ctx.bs.employee.app.command.employee.contact.DeleteEmployeeInfoContactCommand;
import nts.uk.ctx.bs.employee.app.command.employee.contact.UpdateEmployeeInfoContactCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.AddAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.DeleteAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.UpdateAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.mngdata.AddEmployeeDataMngInfoCommand;
import nts.uk.ctx.bs.employee.app.command.employee.mngdata.UpdateEmployeeDataMngInfoCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.AddEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.DeleteEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.UpdateEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.AddAffJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.DeleteAffJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.UpdateAffJobTitleMainCommand;
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

@Stateless
@SuppressWarnings("serial")
public class PeregCommandHandlerCollectorImpl implements PeregCommandHandlerCollector {

	/** Add handlers */
	private static final List<TypeLiteral<?>> ADD_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregAddCommandHandler<AddAffiliationDepartmentCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffWorkplaceHistoryCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffJobTitleMainCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddEmploymentHistoryCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffCompanyHistoryCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddPersonCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddEmployeeDataMngInfoCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffClassificationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddBusinessWokrTypeOfHistoryCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddShortWorkTimeCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddWorkingConditionCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddEmployeeInfoContactCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddPerContactCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAnnuaLeaveCommand>>(){}
			);
	
	/** Update handlers */
	private static final List<TypeLiteral<?>> UPDATE_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffiliationDepartmentCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffJobTitleMainCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffWorkplaceHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdatePersonCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmployeeDataMngInfoCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmploymentHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffCompanyHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffClassificationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateBusinessWorkTypeOfHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateShortWorkTimeCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateWorkingConditionCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmployeeInfoContactCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdatePerContactCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAnnuaLeaveCommand>>(){}
			);
	
	/** Delete handlers */
	private static final List<TypeLiteral<?>> DELETE_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffiliationDepartmentCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffJobTitleMainCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffWorkplaceHistoryCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmploymentHistoryCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffClassificationCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteBusinessWorkTypeOfHistoryCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteShortWorkTimeCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteWorkingConditionCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffCompanyHistoryCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmployeeInfoContactCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeletePerContactCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAnnuaLeaveCommand>>(){}
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

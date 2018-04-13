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
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave10informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave11informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave12informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave13informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave14informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave15informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave16informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave17informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave18informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave19informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave1informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave20informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave2informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave3informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave4informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave5informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave6informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave7informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave8informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave9informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave10informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave11informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave12informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave13informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave14informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave15informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave16informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave17informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave18informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave19informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave1informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave20informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave2informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave3informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave4informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave5informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave6informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave7informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave8informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave9informationCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.AddCareLeaveCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.UpdateCareLeaveCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.otherhdinfo.AddOtherHolidayInfoCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.otherhdinfo.DeleteOtherHolidayInfoCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.otherhdinfo.UpdateOtherHolidayInfoCommand;
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
			new TypeLiteral<PeregAddCommandHandler<AddAnnuaLeaveCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave1informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave2informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave3informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave4informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave5informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave6informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave7informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave8informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave9informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave10informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave11informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave12informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave13informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave14informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave15informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave16informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave17informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave18informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave19informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave20informationCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddOtherHolidayInfoCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddCareLeaveCommand>>(){}
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
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAnnuaLeaveCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave1informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave2informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave3informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave4informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave5informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave6informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave7informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave8informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave9informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave10informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave11informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave12informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave13informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave14informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave15informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave16informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave17informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave18informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave19informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave20informationCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateOtherHolidayInfoCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateCareLeaveCommand>>(){} 
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
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAnnuaLeaveCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteOtherHolidayInfoCommand>>(){} 
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

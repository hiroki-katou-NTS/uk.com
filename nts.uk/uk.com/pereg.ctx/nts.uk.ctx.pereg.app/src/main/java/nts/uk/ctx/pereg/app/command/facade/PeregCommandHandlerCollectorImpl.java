package nts.uk.ctx.pereg.app.command.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import nts.uk.ctx.bs.employee.app.command.department.*;
import nts.uk.ctx.bs.employee.app.command.employee.*;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregCommandHandlerCollector;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;
import nts.uk.ctx.bs.employee.app.command.familyrelatedinformation.care.*;
import nts.uk.ctx.bs.employee.app.command.familyrelatedinformation.incometax.*;
import nts.uk.ctx.bs.employee.app.command.familyrelatedinformation.socialinsurance.*;
import nts.uk.ctx.bs.employee.app.command.jobtitle.main.*;
import nts.uk.ctx.bs.employee.app.command.position.jobposition.*;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.*;
import command.person.currentaddress.*;
import command.person.emergencycontact.*;
import command.person.family.*;
import command.person.info.*;
import command.person.widowhistory.*;
import nts.uk.ctx.bs.employee.app.command.workplace.assigned.UpdateAssignedWorkplaceCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.*;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.*;

@Stateless
@SuppressWarnings("serial")
public class PeregCommandHandlerCollectorImpl implements PeregCommandHandlerCollector {

	/** Add handlers */
	private static final List<TypeLiteral<?>> ADD_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregAddCommandHandler<AddAffiliationDepartmentCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddSubJobPositionCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffWorkplaceHistoryCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddCurrentAddressCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddPerEmergencyContactCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddFamilyCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddWidowHistoryCommand>>(){},
//			new TypeLiteral<PeregAddCommandHandler<AddEmployeeCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddAffJobTitleMainCommand>>(){},
			new TypeLiteral<PeregAddCommandHandler<AddCurrentAffiDeptCommand>>(){}
			);
	
	/** Update handlers */
	private static final List<TypeLiteral<?>> UPDATE_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffiliationDepartmentCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateJobTitleMainCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateFamilyCareCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateIncomeTaxCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateFamilySocialInsuranceCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSubJobPositionCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffWorkplaceHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateCurrentAddressCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdatePerEmergencyContactCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateFamilyCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdatePersonCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateWidowHistoryCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmployeeCommand>>(){},
//			new TypeLiteral<PeregUpdateCommandHandler<UpdateAssignedWorkplaceCommand>>(){},
			new TypeLiteral<PeregUpdateCommandHandler<UpdateCurrentAffiDeptCommand>>(){}
			);
	
	/** Delete handlers */
	private static final List<TypeLiteral<?>> DELETE_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregDeleteCommandHandler<DeleteFamilyCareCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteIncomeTaxCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffiliationDepartmentCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteCurrentAffiDeptCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteFamilySocialInsuranceCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteJobTitleMainCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteSubJobPositionCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteTemporaryAbsenceCommand>>(){},
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffWorkplaceHistoryCommand>>(){}
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

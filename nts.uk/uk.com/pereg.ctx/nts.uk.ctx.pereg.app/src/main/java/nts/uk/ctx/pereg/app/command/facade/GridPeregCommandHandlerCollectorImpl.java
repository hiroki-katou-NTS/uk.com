package nts.uk.ctx.pereg.app.command.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import command.person.contact.UpdatePerContactCommand;
import command.person.info.UpdatePersonCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.AddBusinessWokrTypeOfHistoryCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.UpdateBusinessWorkTypeOfHistoryCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave.AddAnnuaLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave.UpdateAnnuaLeaveCommand;
import nts.uk.ctx.at.shared.app.command.shortworktime.AddShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.shortworktime.UpdateShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.AddWorkingConditionCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.UpdateWorkingCondition2Command;
import nts.uk.ctx.at.shared.app.command.workingcondition.UpdateWorkingConditionCommand;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.AddAffClassificationCommand;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.UpdateAffClassificationCommand;
import nts.uk.ctx.bs.employee.app.command.employee.contact.AddEmployeeInfoContactCommand;
import nts.uk.ctx.bs.employee.app.command.employee.contact.UpdateEmployeeInfoContactCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.AddAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.UpdateAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.mngdata.UpdateEmployeeDataMngInfoCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.AddEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.UpdateEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.AddAffJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.UpdateAffJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.AddTemporaryAbsenceCommand;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.UpdateTemporaryAbsenceCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.AddAffWorkplaceHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.UpdateAffWorkplaceHistoryCommand;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
import nts.uk.shr.pereg.app.command.PeregListCommandHandlerCollector;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;

@Stateless
@SuppressWarnings("serial")
public class GridPeregCommandHandlerCollectorImpl implements PeregListCommandHandlerCollector {

	/** Add handlers */
	private static final List<TypeLiteral<?>> ADD_HANDLER_CLASSES = Arrays.asList(
			//CS00003	所属会社履歴
			new TypeLiteral<PeregAddListCommandHandler<AddAffCompanyHistoryCommand>>(){},
			//CS00004	分類１
			new TypeLiteral<PeregAddListCommandHandler<AddAffClassificationCommand>>(){},
			//CS00014	雇用
			new TypeLiteral<PeregAddListCommandHandler<AddEmploymentHistoryCommand>>(){},
			//CS00016	職位本務
			new TypeLiteral<PeregAddListCommandHandler<AddAffJobTitleMainCommand>>(){},
			//CS00017	職場
			new TypeLiteral<PeregAddListCommandHandler<AddAffWorkplaceHistoryCommand>>(){},
			//CS00018	休職休業
			new TypeLiteral<PeregAddListCommandHandler<AddTemporaryAbsenceCommand>>(){},
			//CS00019	短時間勤務
			new TypeLiteral<PeregAddListCommandHandler<AddShortWorkTimeCommand>>(){},
			//CS00020	労働条件
			new TypeLiteral<PeregAddListCommandHandler<AddWorkingConditionCommand>>(){},
			//CS00021	勤務種別
			new TypeLiteral<PeregAddListCommandHandler<AddBusinessWokrTypeOfHistoryCommand>>(){},
			//CS00022	個人連絡先
			new TypeLiteral<PeregAddListCommandHandler<UpdatePerContactCommand>>(){},
			//CS00023	社員連絡先
			new TypeLiteral<PeregAddListCommandHandler<AddEmployeeInfoContactCommand>>(){},
			//CS00024	年休情報
			new TypeLiteral<PeregAddListCommandHandler<AddAnnuaLeaveCommand>>(){}
			
			);
	/** Update handlers */
	private static final List<TypeLiteral<?>> UPDATE_HANDLER_CLASSES = Arrays.asList(
			//CS00001	社員データ管理
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateEmployeeDataMngInfoCommand>>(){},
			//CS00002	個人基本情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdatePersonCommand>>(){},
			//CS00003	所属会社履歴
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateAffCompanyHistoryCommand>>(){},
			//CS00004	分類１
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateAffClassificationCommand>>(){},
			//CS00014	雇用
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateEmploymentHistoryCommand>>(){},
			//CS00016	職位本務
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateAffJobTitleMainCommand>>(){},
			//CS00017	職場
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateAffWorkplaceHistoryCommand>>(){},
			//CS00018	休職休業
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateTemporaryAbsenceCommand>>(){},
			//CS00019	短時間勤務
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateShortWorkTimeCommand>>(){},
			//CS00020	労働条件
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateWorkingConditionCommand>>(){},
			//CS00021	勤務種別
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateBusinessWorkTypeOfHistoryCommand>>(){},
			//CS00022	個人連絡先
			new TypeLiteral<PeregUpdateListCommandHandler<UpdatePerContactCommand>>(){},
			//CS00023	社員連絡先
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateEmployeeInfoContactCommand>>(){},
			//CS00024	年休情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateAnnuaLeaveCommand>>(){},
			//CS00070  労働条件2
			new TypeLiteral<PeregUpdateCommandHandler<UpdateWorkingCondition2Command>>(){}
			);
	
	@Override
	public Set<PeregAddListCommandHandler<?>> collectAddHandlers() {
		return ADD_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregAddListCommandHandler<?>)obj)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<PeregUpdateListCommandHandler<?>> collectUpdateHandlers() {
		return UPDATE_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregUpdateListCommandHandler<?>)obj)
				.collect(Collectors.toSet());
	}

}
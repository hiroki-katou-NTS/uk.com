package nts.uk.ctx.pereg.app.command.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import command.person.contact.AddPerContactCommand;
import command.person.contact.UpdatePerContactCommand;
import command.person.info.UpdatePersonCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.AddBusinessWokrTypeOfHistoryCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.UpdateBusinessWorkTypeOfHistoryCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annleagrtremnum.AddAnnLeaGrantRemnNumPeregCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave.AddAnnuaLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave.UpdateAnnuaLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave10informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave11informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave12informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave13informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave14informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave15informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave16informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave17informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave18informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave19informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave1informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave20informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave2informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave3informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave4informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave5informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave6informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave7informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave8informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave9informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave10informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave11informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave12informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave13informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave14informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave15informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave16informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave17informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave18informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave19informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave1informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave20informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave2informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave3informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave4informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave5informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave6informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave7informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave8informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave9informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave.AddCareLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave.UpdateCareLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.otherhdinfo.AddOtherHolidayInfoCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.otherhdinfo.UpdateOtherHolidayInfoCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum.AddResvLeaRemainPeregCommand;
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
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
import nts.uk.shr.pereg.app.command.PeregListCommandHandlerCollector;
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
			new TypeLiteral<PeregAddListCommandHandler<AddPerContactCommand>>(){},
			//CS00023	社員連絡先
			new TypeLiteral<PeregAddListCommandHandler<AddEmployeeInfoContactCommand>>(){},
			//CS00024	年休情報
			new TypeLiteral<PeregAddListCommandHandler<AddAnnuaLeaveCommand>>(){},
			//CS00025	特別休暇１情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave1informationCommand>>(){},
			//CS00026	特別休暇２情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave2informationCommand>>(){},
			//CS00027	特別休暇３情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave3informationCommand>>(){},
			//CS00028	特別休暇４情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave4informationCommand>>(){},
			//CS00029	特別休暇５情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave5informationCommand>>(){},
			//CS00030	特別休暇６情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave6informationCommand>>(){},
			//CS00031	特別休暇７情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave7informationCommand>>(){},
			//CS00032	特別休暇８情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave8informationCommand>>(){},
			//CS00033	特別休暇９情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave9informationCommand>>(){},
			//CS00034	特別休暇１０情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave10informationCommand>>(){},
			//CS00035	その他休暇情報
			new TypeLiteral<PeregAddListCommandHandler<AddOtherHolidayInfoCommand>>(){},
			//CS00036	子の看護・介護休暇情報
			new TypeLiteral<PeregAddListCommandHandler<AddCareLeaveCommand>>(){},
			//CS00038	積立年休付与残数
			new TypeLiteral<PeregAddListCommandHandler<AddResvLeaRemainPeregCommand>>(){},
			//CS00037	年休付与残数
			new TypeLiteral<PeregAddListCommandHandler<AddAnnLeaGrantRemnNumPeregCommand>>(){},
			//CS00049	特別休暇１１情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave11informationCommand>>(){},
			//CS00050	特別休暇１２情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave12informationCommand>>(){},
			//CS00051	特別休暇１３情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave13informationCommand>>(){},
			//CS00052	特別休暇１４情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave14informationCommand>>(){},
			//CS00053	特別休暇１５情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave15informationCommand>>(){},
			//CS00054	特別休暇１６情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave16informationCommand>>(){},
			//CS00055	特別休暇１７情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave17informationCommand>>(){},
			//CS00056	特別休暇１８情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave18informationCommand>>(){},
			//CS00057	特別休暇１９情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave19informationCommand>>(){},
			//CS00058	特別休暇２０情報
			new TypeLiteral<PeregAddListCommandHandler<AddSpecialleave20informationCommand>>(){}
			
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
			//CS00025	特別休暇１情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave1informationCommand>>(){},
			//CS00026	特別休暇２情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave2informationCommand>>(){},
			//CS00027	特別休暇３情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave3informationCommand>>(){},
			//CS00028	特別休暇４情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave4informationCommand>>(){},
			//CS00029	特別休暇５情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave5informationCommand>>(){},
			//CS00030	特別休暇６情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave6informationCommand>>(){},
			//CS00031	特別休暇７情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave7informationCommand>>(){},
			//CS00032	特別休暇８情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave8informationCommand>>(){},
			//CS00033	特別休暇９情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave9informationCommand>>(){},
			//CS00034	特別休暇１０情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave10informationCommand>>(){},
			//CS00035	その他休暇情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateOtherHolidayInfoCommand>>(){},
			//CS00036	子の看護・介護休暇情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateCareLeaveCommand>>(){},
			
			//CS00049	特別休暇１１情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave11informationCommand>>(){},
			//CS00050	特別休暇１２情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave12informationCommand>>(){},
			//CS00051	特別休暇１３情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave13informationCommand>>(){},
			//CS00052	特別休暇１４情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave14informationCommand>>(){},
			//CS00053	特別休暇１５情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave15informationCommand>>(){},
			//CS00054	特別休暇１６情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave16informationCommand>>(){},
			//CS00055	特別休暇１７情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave17informationCommand>>(){},
			//CS00056	特別休暇１８情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave18informationCommand>>(){},
			//CS00057	特別休暇１９情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave19informationCommand>>(){},
			//CS00058	特別休暇２０情報
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateSpecialleave20informationCommand>>(){},
			//CS00070  労働条件2
			new TypeLiteral<PeregUpdateListCommandHandler<UpdateWorkingCondition2Command>>(){}
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
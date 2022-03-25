package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.CommonCompanySettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt.CorrectionAfterTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork.CorrectionAfterChangeWorkInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork.CorrectionShortWorkingHour;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime.CreateOneDayRangeCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.FactoryManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@AllArgsConstructor
public class CorrectionAttendanceRuleRequireImpl implements CorrectionAttendanceRule.Require {

	private AttendanceItemConvertFactory attendanceItemConvertFactory;

	private OptionalItemRepository optionalItem;

	private CorrectionAfterTimeChange correctionAfterTimeChange;

	private CorrectionAfterChangeWorkInfo correctionAfterChangeWorkInfo;

	private CommonCompanySettingForCalc companyCommonSettingRepo;

	private FactoryManagePerPersonDailySet personDailySetFactory;

	private FixedWorkSettingRepository fixWorkSetRepo;

	private PredetemineTimeSettingRepository predetemineTimeSetRepo;

	private CreateOneDayRangeCalc createOneDayRangeCalc;

	private WorkTypeRepository workTypeRepo;

	private WorkTimeSettingRepository workTimeSettingRepo;

	private FlowWorkSettingRepository flowWorkSettingRepo;

	private FlexWorkSettingRepository flexWorkSettingRepo;

	private ICorrectSupportDataWork iCorrectSupportDataWork;

	private WorkingConditionItemRepository workingConditionItemRepo;

	private WorkingConditionRepository workingConditionRepo;

	private CorrectionShortWorkingHour correctShortWorkingHour;
	

	@Override
	public ManagePerCompanySet managePerCompanySet() {
		return companyCommonSettingRepo.getCompanySetting();
	}

	@Override
	public Optional<ManagePerPersonDailySet> managePerPersonDailySet(String sid, GeneralDate ymd,
			IntegrationOfDaily dailyRecord) {
		return personDailySetFactory.create(AppContexts.user().companyId(), sid, ymd, dailyRecord);
	}

	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter(String cid) {
		val optionalItems = findAllOptionalItem(cid).stream()
				.collect(Collectors.toMap(c -> c.getOptionalItemNo().v(), c -> c));
		return attendanceItemConvertFactory.createDailyConverter(optionalItems);
	}

	@Override
	public Optional<WorkType> workType(String companyId, String workTypeCd) {
		return workTypeRepo.findByPK(companyId, workTypeCd);
	}

	@Override
	public Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode) {
		return workTimeSettingRepo.findByCode(companyId, workTimeCode);
	}

	@Override
	public Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode) {
		return flowWorkSettingRepo.find(companyId, workTimeCode);
	}

	@Override
	public Optional<FlexWorkSetting> flexWorkSetting(String companyId, String workTimeCode) {
		return flexWorkSettingRepo.find(companyId, workTimeCode);
	}

	@Override
	public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
		return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
	}

	@Override
	public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
		return workingConditionItemRepo.getByHistoryId(historyId);
	}

	@Override
	public Optional<AppStampShare> appStamp() {
		return Optional.empty();
	}

	@Override
	public List<OptionalItem> findAllOptionalItem(String companyId) {
		return  optionalItem.findAll(companyId);
	}

	@Override
	public Pair<ChangeDailyAttendance, IntegrationOfDaily> corectionAfterTimeChange(IntegrationOfDaily domainDaily,
			ChangeDailyAttendance changeAtt, Optional<WorkingConditionItem> workCondOpt) {
		return correctionAfterTimeChange.corection(domainDaily, changeAtt, workCondOpt);
	}

	@Override
	public IntegrationOfDaily correctionAfterChangeWorkInfo(String companyId, IntegrationOfDaily domainDaily,
			Optional<WorkingConditionItem> workCondition, ChangeDailyAttendance changeDailyAttendance) {
		return correctionAfterChangeWorkInfo.correction(companyId, domainDaily, workCondition, changeDailyAttendance);
	}

	@Override
	public IntegrationOfDaily correctShortWorkingHour(String companyId, IntegrationOfDaily domainDaily) {
		return correctShortWorkingHour.correct(companyId, domainDaily);
	}

	@Override
	public SupportDataWork correctSupportDataWork(IGetAppForCorrectionRuleRequire require,
			IntegrationOfDaily integrationOfDaily, ScheduleRecordClassifi classification) {
		return iCorrectSupportDataWork.correctSupportDataWork(require, integrationOfDaily, classification);
	}

	@Override
	public CalculationRangeOfOneDay createOneDayRange(IntegrationOfDaily integrationOfDaily,
			Optional<WorkTimezoneCommonSet> commonSet, WorkType workType, JustCorrectionAtr justCorrectionAtr,
			Optional<WorkTimeCode> workTimeCode) {
		return createOneDayRangeCalc.createOneDayRange(integrationOfDaily, commonSet, workType,
				justCorrectionAtr, workTimeCode);
	}

	@Override
	public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
		return workTimeSettingRepo.findByCode(companyId, workTimeCode.v());
	}

	@Override
	public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
		return fixWorkSetRepo.findByKey(companyId, workTimeCode.v());
	}

	@Override
	public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
		return flowWorkSettingRepo.find(companyId, workTimeCode.v());
	}

	@Override
	public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
		return flexWorkSettingRepo.find(companyId, workTimeCode.v());
	}

	@Override
	public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
		return predetemineTimeSetRepo.findByWorkTimeCode(companyId, workTimeCode.v());
	}

	@Override
	public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
		return workTypeRepo.findByPK(companyId, workTypeCode.v());
	}

}

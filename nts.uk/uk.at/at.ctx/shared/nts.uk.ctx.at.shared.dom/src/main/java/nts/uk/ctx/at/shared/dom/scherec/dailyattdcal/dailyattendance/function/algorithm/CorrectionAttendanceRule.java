package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime.BreakTimeSheetCorrector;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
<<<<<<< HEAD
=======
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
>>>>>>> pj/at/release_ver4

/**
 * @author ThanhNX
 *
 *         勤怠ルールの補正処理
 */
public class CorrectionAttendanceRule {

	// 勤怠ルールの補正処理
	public static IntegrationOfDaily process(Require require, String companyId, IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {

		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter(companyId)
																					.setData(domainDaily).completed();
		List<Integer> atendanceId = converter.editStates().stream()
				.map(x -> x.getAttendanceItemId()).distinct().collect(Collectors.toList());

		// 補正前の状態を保持
		// IntegrationOfDaily beforeDomain = converter.toDomain();
		List<ItemValue> beforeItems = atendanceId.isEmpty() ? new ArrayList<>() : converter.convert(atendanceId);

		// 社員の労働条件を取得する
		Optional<WorkingConditionItem> workCondOpt = WorkingConditionService.findWorkConditionByEmployee(require,
				domainDaily.getEmployeeId(), domainDaily.getYmd());

		// 勤怠変更後の補正
		IntegrationOfDaily afterDomain = require
				.corectionAfterTimeChange(domainDaily, changeAtt, workCondOpt).getRight();

		if (changeAtt.workInfo || changeAtt.isDirectBounceClassifi() ) {
			// 変更する勤怠項目を確認
			//// 勤務情報変更後の補正
			afterDomain = require.correctionAfterChangeWorkInfo(companyId, afterDomain, workCondOpt,
					changeAtt);

		}
		
		//出退勤変更後の補正
		if(changeAtt.attendance) {

			SupportDataWork workImport = require.correctSupportDataWork(require, afterDomain, changeAtt.getClassification());
			
			if(workImport != null)
				afterDomain = workImport.getIntegrationOfDaily().orElse(afterDomain);
		}

		if(changeAtt.workInfo || changeAtt.isDirectBounceClassifi() || changeAtt.attendance) {
		//短時間勤務の補正
			afterDomain = require.correctShortWorkingHour(companyId, afterDomain);
		}
		
		/** 休憩時間帯の補正 */
		BreakTimeSheetCorrector.correct(require, companyId, afterDomain, changeAtt.correctValCopyFromSche);

		// 手修正を基に戻す
		DailyRecordToAttendanceItemConverter afterConverter = require.createDailyConverter(companyId).setData(afterDomain)
				.completed();
		//応援別勤務職場と応援別勤務場所が反映された場合、手修正の状態しても復元されません
		if(!beforeItems.isEmpty()) {
			if (afterDomain instanceof DailyRecordOfApplication && require.appStamp().isPresent()) {
				//反映された勤怠項目IDを取得する
				val lstReflectId = ((DailyRecordOfApplication) afterDomain).getAttendanceBeforeReflect().stream()
						.map(x -> x.getAttendanceId()).collect(Collectors.toList());
				//日別勤怠(Work)から応援別勤務職場と援別勤務場所の勤怠項目IDを取得する
				val lstOuenWplLocaId = afterDomain.getListWplLocationIdFromOuen().stream().filter(x -> {
					return lstReflectId.stream().anyMatch(y -> y == x);
				}).collect(Collectors.toList());
				beforeItems = beforeItems.stream().filter(x -> !lstOuenWplLocaId.contains(x.getItemId()))
						.collect(Collectors.toList());
			}
			//	復元する
			afterConverter.merge(beforeItems);
		}
		IntegrationOfDaily integrationOfDaily = afterConverter.toDomain();
		//integrationOfDaily.setOuenTimeSheet(afterDomain.getOuenTimeSheet());
		if (afterDomain instanceof DailyRecordOfApplication && require.appStamp().isPresent()) {
			return new DailyRecordOfApplication(
					((DailyRecordOfApplication) afterDomain).getAttendanceBeforeReflect(),
					((DailyRecordOfApplication) afterDomain).getClassification(), integrationOfDaily);
		}
		return integrationOfDaily;
	}

<<<<<<< HEAD
	public static interface Require extends BreakTimeSheetCorrector.RequireM1, WorkingConditionService.RequireM1, IGetAppForCorrectionRuleRequire{
		//OptionalItemRepository.findAll
		 public List<OptionalItem> findAllOptionalItem(String companyId);
		 
		 //CorrectionAfterTimeChange.corection
		 public Pair<ChangeDailyAttendance, IntegrationOfDaily> corectionAfterTimeChange(IntegrationOfDaily domainDaily,
					ChangeDailyAttendance changeAtt, Optional<WorkingConditionItem> workCondOpt);
		 
		 //CorrectionAfterChangeWorkInfo.correction
		public IntegrationOfDaily correctionAfterChangeWorkInfo(String companyId, IntegrationOfDaily domainDaily,
				Optional<WorkingConditionItem> workCondition, ChangeDailyAttendance changeDailyAttendance);
		//CorrectionShortWorkingHour.correct
		public IntegrationOfDaily correctShortWorkingHour(String companyId, IntegrationOfDaily domainDaily);
		
		public SupportDataWork correctSupportDataWork(IGetAppForCorrectionRuleRequire require, IntegrationOfDaily integrationOfDaily, ScheduleRecordClassifi classification);
		
=======
	private WorkingConditionService.RequireM1 createImp() {

		return new WorkingConditionService.RequireM1() {

			@Override
			public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
				return workingConditionItemRepo.getByHistoryId(historyId);
			}

			@Override
			public Optional<WorkingCondition> workingCondition(String companyId, String employeeId,
					GeneralDate baseDate) {
				return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
			}
		};
	}

	private BreakTimeSheetCorrector.RequireM1 createBreakRequire(Map<Integer, OptionalItem> optionalItems) {

		return new BreakTimeSheetCorrector.RequireM1() {

			@Override
			public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {

				return fixWorkSetRepo.findByKey(companyId, workTimeCode.v());
			}

			@Override
			public Optional<PredetemineTimeSetting> predetemineTimeSetting(String cid, WorkTimeCode workTimeCode) {
				
				return predetemineTimeSetRepo.findByWorkTimeCode(cid, workTimeCode.v());
			}

			@Override
			public CalculationRangeOfOneDay createOneDayRange(
					IntegrationOfDaily integrationOfDaily, Optional<WorkTimezoneCommonSet> commonSet, WorkType workType,
					JustCorrectionAtr justCorrectionAtr, Optional<WorkTimeCode> workTimeCode) {


				return createOneDayRangeCalc.createOneDayRange(integrationOfDaily, commonSet, workType, justCorrectionAtr, workTimeCode);
			}

			@Override
			public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCd) {
				return workTypeRepo.findByPK(companyId, workTypeCd.v());
			}

			@Override
			public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {

				return workTimeSettingRepo.findByCode(companyId, workTimeCode.v());
			}

			@Override
			public ManagePerCompanySet managePerCompanySet() {

				return companyCommonSettingRepo.getCompanySetting();
			}

			@Override
			public Optional<ManagePerPersonDailySet> managePerPersonDailySet(String sid, GeneralDate ymd, IntegrationOfDaily dailyRecord) {

				return personDailySetFactory.create(AppContexts.user().companyId(), sid, ymd, dailyRecord);
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
			public DailyRecordToAttendanceItemConverter createDailyConverter() {

				return attendanceItemConvertFactory.createDailyConverter(optionalItems);
			}
		};
>>>>>>> pj/at/release_ver4
	}
}

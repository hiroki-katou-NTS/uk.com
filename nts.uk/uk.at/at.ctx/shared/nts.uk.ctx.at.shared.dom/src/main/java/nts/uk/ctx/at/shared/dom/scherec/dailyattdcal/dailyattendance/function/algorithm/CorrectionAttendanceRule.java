package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime.BreakTimeSheetCorrector;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 *
 *         勤怠ルールの補正処理
 */
public class CorrectionAttendanceRule {

	// 勤怠ルールの補正処理
	public static IntegrationOfDaily process(Require require, IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {

		String companyId = AppContexts.user().companyId();

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

			SupportDataWork workImport = require.correctSupportDataWork(require, afterDomain);
			
			if(workImport != null)
				afterDomain = workImport.getIntegrationOfDaily().orElse(afterDomain);
		}

		if(changeAtt.workInfo || changeAtt.isDirectBounceClassifi() || changeAtt.attendance) {
		//短時間勤務の補正
			afterDomain = require.correctShortWorkingHour(companyId, afterDomain);
		}
		
		/** 休憩時間帯の補正 */
		BreakTimeSheetCorrector.correct(require, afterDomain, changeAtt.correctValCopyFromSche);

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
		
		public SupportDataWork correctSupportDataWork(IGetAppForCorrectionRuleRequire require, IntegrationOfDaily integrationOfDaily);
		
	}
}

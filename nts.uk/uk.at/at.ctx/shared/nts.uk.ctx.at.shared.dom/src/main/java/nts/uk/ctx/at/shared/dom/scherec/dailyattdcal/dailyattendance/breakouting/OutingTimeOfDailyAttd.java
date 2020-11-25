package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;

/**
 * 日別勤怠の外出時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.休憩・外出.日別勤怠の外出時間帯
 * @author tutk
 *
 */
@Getter
public class OutingTimeOfDailyAttd {
	//時間帯
	@Setter
	private List<OutingTimeSheet> outingTimeSheets = new ArrayList<>();

	public OutingTimeOfDailyAttd(List<OutingTimeSheet> outingTimeSheets) {
		super();
		this.outingTimeSheets = outingTimeSheets;
	}
	/**
	 * 取得条件区分を基に外出時間の不要項目の削除
	 * 外出時間帯の取得
	 * (区分 = 控除なら　外出理由 = 私用or組合のみのリストへ)
	 * @param 取得条件区分
	 * @return 不要な項目を削除した時間帯
	 */
	public List<TimeSheetOfDeductionItem> removeUnuseItemBaseOnAtr(DeductionAtr dedAtr,
			WorkTimeMethodSet workTimeMethodSet,
			Optional<FlowWorkRestTimezone> fluRestTime,
			Optional<FlowWorkRestSettingDetail> flowDetail) {
		
		/** △外出を取得 */
		List<TimeSheetOfDeductionItem> returnList = (dedAtr.isDeduction())?
														//控除用の時は、外出理由 = 私用or組合のみの時間帯に絞る(他の2つは消す)
														this.outingTimeSheets.stream()
																			 .filter(tc->tc.getReasonForGoOut().isPrivateOrUnion())
																			 .filter(ts -> ts.isCalcState())
																			 .map(tc -> tc.toTimeSheetOfDeductionItem())
																			 .collect(Collectors.toList()):
														//全ての時は、全外出時間帯が対象
														this.outingTimeSheets.stream()
																			 .filter(ts -> ts.isCalcState())
																			 .map(tc -> tc.toTimeSheetOfDeductionItem())
																			 .collect(Collectors.toList());
		/** ○流動勤務かどうか判断 */
		if(workTimeMethodSet.isFluidWork()) {
			
			val isStampWithoutRefer = flowDetail.map(c -> c.getFlowFixedRestSetting().getCalculateMethod().isStampWithoutReference()).orElse(false);
			val isFixBreak = fluRestTime.map(c -> c.isFixRestTime()).orElse(false);
			val isReferRestTime = flowDetail.map(c -> c.getFlowRestSetting().getCalculateMethod().isUseMasterAndStamp()).orElse(false);

			/** ○外出を休憩として扱うかどうか判断 */
			if((isStampWithoutRefer && isFixBreak) || (!isFixBreak  && isReferRestTime)) {
				
				returnList = convertFromgoOutTimeToBreakTime(flowDetail.get().getFlowFixedRestSetting(),returnList);
			}
		}

		return returnList;
	}

	/**
	 * 外出時間帯から休憩時間帯への変換
	 * 休憩時間帯への変換
	 * @return 控除項目の時間帯
	 */
	private List<TimeSheetOfDeductionItem> convertFromgoOutTimeToBreakTime(FlowFixedRestSet fluidprefixBreakTimeSet,
			List<TimeSheetOfDeductionItem> deductionList) {
		
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();

		for(TimeSheetOfDeductionItem deductionItem : deductionList) {
			
			/** ○外出の区分を判断 */
			if((fluidprefixBreakTimeSet.isUsePrivateGoOutRest() && deductionItem.getGoOutReason().get().isPrivate())
				||(fluidprefixBreakTimeSet.isUseAssoGoOutRest() && deductionItem.getGoOutReason().get().isUnion())) {
				/** ○控除項目の時間帯の休憩用の区分を変更 */
				returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(deductionItem.getTimeSheet(),
																							  deductionItem.getRounding(),
																							  deductionItem.getRecordedTimeSheet(),
																							  deductionItem.getDeductionTimeSheet(),
																							  deductionItem.getWorkingBreakAtr(),
																							  deductionItem.getGoOutReason(),
																							  deductionItem.getBreakAtr(),
																							  deductionItem.getShortTimeSheetAtr(),
																							  DeductionClassification.BREAK,
																							  deductionItem.getChildCareAtr()));
			} else {
				returnList.add(deductionItem);
			}
		}

		return returnList;
	}

	/**
	 * 外出時間帯を全て控除項目の時間帯に変換する(パラメータ固定)
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> changeAllTimeSheetToDeductionItem(){
		return this.outingTimeSheets.stream()
									.filter(ts -> ts.isCalcState())
									.map(tc -> tc.toTimeSheetOfDeductionItem())
									.collect(Collectors.toList());
	}

	/**
	 * 外出理由を指定して時間帯を取得する
	 * @param reasonForGoOut 外出理由
	 * @return
	 */
	public List<TimeSpanForCalc> getTimeZoneByGoOutReason(GoingOutReason reasonForGoOut) {

		return this.outingTimeSheets.stream()
				.filter( c -> c.getReasonForGoOut() == reasonForGoOut)
				.filter(c -> c.getTimeZone().isPresent() )
				.map(c -> c.getTimeZone().get() )
				.collect( Collectors.toList() );
	}
}

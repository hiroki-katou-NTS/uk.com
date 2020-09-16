package nts.uk.ctx.at.shared.dom.monthly.agreement;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.AggregatePeriod;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.shr.com.i18n.TextResource;

public class AgreementTimeAggregateService {

	/** 36協定時間の集計 */
	public static AggregateResult aggregate(RequireM1 require, 
			String cid, String sid, AggregatePeriod aggregatePeriod, 
			DatePeriod executePeriod, WorkingConditionItem workConditionItem, 
			List<IntegrationOfDaily> dailyRecords) {
		
		/** ○３６協定運用設定を取得 */
		val agreementOpeSet = require.agreementOperationSetting(cid).orElse(null);
		
		if (agreementOpeSet == null) {
			/** ○エラーログ書き込み */
			return AggregateResult.fail(new MonthlyAggregationErrorInfo("017", 
												new ErrMessageContent(TextResource.localize("Msg_1246"))));
		}
		
		/** ○集計期間を取得 */
		aggregatePeriod = agreementOpeSet.getAggregatePeriod(aggregatePeriod.getPeriod());
		
		/** 大塚カスタマイズ（試験日対応） */
		dailyRecords = correctForOotsuka(dailyRecords, workConditionItem.getLaborSystem());
		
		return null;
	}
	
	/** 大塚カスタマイズ（試験日対応） */
	private static List<IntegrationOfDaily> correctForOotsuka(
			List<IntegrationOfDaily> dailyRecords, WorkingSystem laborSystem) {
		
		/** input.労働制を確認 */
		if (laborSystem == WorkingSystem.FLEX_TIME_WORK) {
			
			dailyRecords.stream().forEach(dr -> {
				
				dr.getAttendanceTimeOfDailyPerformance().ifPresent(at -> {
					
					val corrected = MonthlyCalculatingDailys.examDayTimeCorrect(at, dr.getWorkInformation());
					dr.setAttendanceTimeOfDailyPerformance(Optional.of(corrected));
				});
			});
		}
		
		return dailyRecords;
	} 
	
	@Getter
	@AllArgsConstructor
	public static class AggregateResult {
		
		private Optional<MonthlyAggregationErrorInfo> error;
		
		private Optional<AgreementTimeOfManagePeriod> agreementTime;
		
		public static AggregateResult fail(MonthlyAggregationErrorInfo error) {
			return new AggregateResult(Optional.of(error), Optional.empty());
		}
		
		public static AggregateResult success(AgreementTimeOfManagePeriod agreementTime) {
			return new AggregateResult(Optional.empty(), Optional.of(agreementTime));
		}
	}
	
	public static interface RequireM1 {
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String cid);
	}
}

package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

/**
 * 36協定上限複数月平均時間
 * @author shuichi_ishida
 */
@Getter
public class AgreMaxAverageTimeMulti {

	/** 上限時間 */
	private OneMonthErrorAlarmTime maxTime;
	/** 平均時間 */
	private List<AgreMaxAverageTime> averageTimes;
	
	/**
	 * コンストラクタ
	 */
	public AgreMaxAverageTimeMulti() {
		this.maxTime = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(0),
													new AgreementOneMonthTime(0));
		this.averageTimes = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param maxTime 上限時間
	 * @param averageTimeList 平均時間
	 * @return 36協定上限複数月平均時間
	 */
	public static AgreMaxAverageTimeMulti of(
			OneMonthErrorAlarmTime maxTime,
			List<AgreMaxAverageTime> averageTimeList) {
		
		AgreMaxAverageTimeMulti domain = new AgreMaxAverageTimeMulti();
		domain.maxTime = maxTime;
		domain.averageTimes = averageTimeList;
		return domain;
	}
	
	/** 36協定上限各月平均時間を追加する */
	public void add(YearMonth targerYm, YearMonth baseYm, 
			AttendanceTimeMonth targetAgreementTime, AttendanceTimeMonth baseAgreementTime) {
		
		/** 期間を計算する */
		val period = new YearMonthPeriod(targerYm, baseYm);
		
		AttendanceTimeYear totalTime;
		
		/** 末尾の36協定上限各月平均時間を取得する */
		if (this.averageTimes.isEmpty()) {
			/** 合計時間を計算する */
			totalTime = new AttendanceTimeYear(targetAgreementTime.valueAsMinutes() 
												+ baseAgreementTime.valueAsMinutes());
		} else {
			
			/** 末尾の36協定上限各月平均時間を取得する */
			val last = this.averageTimes.get(this.averageTimes.size() -1);
			
			/** 合計時間を計算する */
			totalTime = last.getTotalTime().addMinutes(targetAgreementTime.valueAsMinutes());
		}
		
		/** 平均時間を計算する */
		val averageTime = new AttendanceTimeMonth(totalTime.valueAsMinutes() / (this.averageTimes.size() + 2));
		
		/** 36協定上限各月平均時間を作成する */
		val agreementAvgTime = AgreMaxAverageTime.of(period, totalTime, averageTime);
		
		/** 「月別実績の36協定複数月平均状態」を確認する */
		agreementAvgTime.errorCheck(this.maxTime);
		
		/** 36協定上限複数月平均時間。平均時間に追加する */
		this.averageTimes.add(agreementAvgTime);
	}
}

package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;

/** 指定する年月の時間をもとに36協定時間を集計する */
public class AggregateAgreementTimeByYM {

	/** [No.683]指定する年月の時間をもとに36協定時間を集計する */
	public static AgreMaxAverageTimeMulti aggregate(RequireM1 require, 
			String sid, GeneralDate baseDate, YearMonth ym, 
			Map<YearMonth, AttendanceTimeMonth> agreementTimes) {
		
		/** 36協定基本設定を取得する */
		val agreementBasicSetting = require.basicAgreementSetting(
											AppContexts.user().companyId(), sid, baseDate);
		
		/** 「36協定上限複数月平均時間」を作成する */
		val agreementTime = AgreMaxAverageTimeMulti.of(agreementBasicSetting.getMultiMonth().getMultiMonthAvg(),
														new ArrayList<>());
		
		/** 36協定時間を取得する */
		val lastAgreementTime = getAgreementTime(require, sid, ym, agreementTimes);
		
		/** 「パラメータ。年月」からループの年月期間を計算する */
		val agreementRunPeriod = new YearMonthPeriod(ym.addMonths(-5), ym.addMonths(-1));
		YearMonth current = agreementRunPeriod.end();
		
		/** 年月期間をループする */
		while (current.greaterThanOrEqualTo(agreementRunPeriod.start())) {
			
			/** 36協定時間を取得する */
			val currentAgreementTime = getAgreementTime(require, sid, current, agreementTimes);
			
			/** 36協定上限各月平均時間を追加する */
			agreementTime.add(current, ym, currentAgreementTime, lastAgreementTime);
			
			current = current.addMonths(-1);
		}
		
		return agreementTime;
	}
	
	private static AttendanceTimeMonth getAgreementTime(RequireM2 require, String sid,
			YearMonth ym, Map<YearMonth, AttendanceTimeMonth> agreementTimes) {
		
		/** 「パラメータ。36協定時間」に対象の年月のデータが存在するかを確認する */
		if (agreementTimes.containsKey(ym)) {
			
			/** 36協定時間を取得する */
			return agreementTimes.get(ym);
		}
		
		/** 「管理期間の36協定時間」を取得する */
		return require.agreementTimeOfManagePeriod(sid, ym)
						/** 36協定時間を取得する */
						.map(at -> at.getAgreementTime().getAgreementTime())
						/** 36協定時間　＝　0 */
						.orElseGet(() -> new AttendanceTimeMonth(0));
	}
	
	public static interface RequireM2 {
		
		Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String sid, YearMonth ym);
	}
	
	public static interface RequireM1 extends RequireM2 {
		
		BasicAgreementSetting basicAgreementSetting(String cid, String sid, GeneralDate baseDate);
	}
}

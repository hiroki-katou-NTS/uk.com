package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.context.AppContexts;

/** 36協定上限複数月平均時間と年間時間の取得 */
public class GetYearAndMultiMonthAgreementTime {

	/** [NO.599]36協定上限複数月平均時間と年間時間の取得(日指定) */
	public static AgreementTimeOutput getByYmAndDate(RequireM1 require, String sid, GeneralDate baseDate,
			YearMonth ym, ScheRecAtr scheRecAtr) {
	
		/** ○ドメインモデル「３６協定運用設定」を取得する */
		val agreementSeting = require.agreementOperationSetting(AppContexts.user().companyId()).orElse(null);
		if (agreementSeting == null) {
			return new AgreementTimeOutput();
		}
		
		/** ○指定日を含む年月期間を取得 */
		val ymPeriod = agreementSeting.getPeriodYear(baseDate);
		
		/** 年月を指定して、36協定期間の年度を取得する */
		val year = agreementSeting.getYear(ymPeriod.end());
		
		/** ○36協定上限複数月平均時間と年間時間の取得 */
		return internalGet(require, sid, baseDate, ym, year, ymPeriod, scheRecAtr);
	}
	
	/** 
	 * 36協定上限複数月平均時間と年間時間の取得
	 * @param sid　社員ID
	 * @param baseDate　基準日
	 * @param ym　年月
	 * @param year　年度
	 * @param scheRecAtr　予実区分 
	 * @return
	 */
	public static AgreementTimeOutput getByYmAndYear(RequireM1 require, String sid, GeneralDate baseDate,
			YearMonth ym, Year year, ScheRecAtr scheRecAtr) {
	
		/** 年度から年月期間を取得 */
		val ymPeriod = getDatePeriod(require, year);
		if (ymPeriod == null) {
			return new AgreementTimeOutput();
		}
		
		/** ○36協定上限複数月平均時間と年間時間の取得 */
		return internalGet(require, sid, baseDate, ym, year, ymPeriod, scheRecAtr);
	}
	
	/** 
	 * 内部処理
	 * @param sid 社員ID
	 * @param baseDate　基準日
	 * @param ym　年月
	 * @param year　年度
	 * @param ymPeriod　年月期間
	 * @param scheRecAtr　予実区分　
	 * @return
	 */
	private static AgreementTimeOutput internalGet(RequireM3 require, String sid, GeneralDate baseDate,
			YearMonth ym, Year year, YearMonthPeriod ymPeriod, ScheRecAtr scheRecAtr) {
		
		/** 年月から複数月期間を計算する */
		val multiMonthPeriod = new YearMonthPeriod(ym.addMonths(-5), ym);
		
		/** 空っぽのMap<年月、管理期間の36協定時間>を作成する */
		Map<YearMonth, AgreementTimeOfManagePeriod> preData = new HashMap<>();
		
		/** 年度の管理期間の36協定時間を取得する */
		AgreementTimeGetService.prepareData(require, sid, ymPeriod, baseDate, preData, scheRecAtr);
		
		/** 複数月平均の管理期間の36協定時間を取得する */
		AgreementTimeGetService.prepareData(require, sid, multiMonthPeriod, baseDate, preData, scheRecAtr);
		
		/** 36協定上限複数月平均時間を取得する */
		val multiMonthAgreementTime = getAgreMaxAverageTimeMulti(require, sid, ym, baseDate, preData);
		
		/** 36協定年間時間を取得する */
		val yearAgreementTime = GetAgreementTime.getYear(require, sid, year, baseDate, preData);
		
		/** 36協定上限複数月平均時間、36協定年間時間を返す */
		return new AgreementTimeOutput(yearAgreementTime.orElse(null), multiMonthAgreementTime);
	} 
	
	/** 36協定上限複数月平均時間を取得する */ 
	private static AgreMaxAverageTimeMulti getAgreMaxAverageTimeMulti(RequireM6 require, String sid, YearMonth ym, 
			GeneralDate baseDate, Map<YearMonth, AgreementTimeOfManagePeriod> preData) {
		
		/** パラメータ。36協定時間からMap<年月、勤怠月間時間>を変換する */
		val mapped = preData.entrySet().stream().collect(Collectors.toMap(d -> d.getKey(), 
																		d -> d.getValue().getAgreementTime().getAgreementTime()));
		
		/** [No.683]指定する年月の時間をもとに36協定時間を集計する */
		return AggregateAgreementTimeByYM.aggregate(require, sid, baseDate, ym, mapped);
	}
	
	/** 年度から年期間を取得 */
	private static YearMonthPeriod getDatePeriod(RequireM2 require, Year year) {
		
		/** ○ドメインモデル「３６協定運用設定」を取得する */
		val agreementSetting = require.agreementOperationSetting(AppContexts.user().companyId());
		
		/** 年度から36協定の年月期間を取得 */
		return agreementSetting.map(as -> as.getYearMonthPeriod(year)).orElse(null);
	}

	public static interface RequireM6 extends AggregateAgreementTimeByYM.RequireM1 {
		
	}
	
	public static interface RequireM1 extends RequireM3, RequireM2 {
		
	}
	
	public static interface RequireM3 extends RequireM6, GetAgreementTime.RequireM3, 
		AgreementTimeGetService.RequireM1 {
		
	}
	
	public static interface RequireM2 {
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String cid);
	}
}

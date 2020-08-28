package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementPeriodFromYear;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

/**
 * 36協定期間を取得
 * @author shuichi_ishida
 */
public class GetAgreementPeriod {

	/**
	 * 年度を指定して36協定期間を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @return 期間
	 */
	public static Optional<DatePeriod> byYear(RequireM2 require, CacheCarrier cacheCarrier, 
			String companyId, String employeeId, GeneralDate criteria, Year year) {
		
		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, criteria);
		if (closure == null) return Optional.empty();
		
		// 年度から集計期間を取得
		val resultOpt = GetAgreementPeriodFromYear.algorithm(require, year, closure);
		
		// 期間を返す
		return resultOpt;
	}
	/**
	 * 指定日を含む年期間を取得
	 * @param companyId 会社ID
	 * @param criteria 指定年月日
	 * @param agreementOperationSet 36協定運用設定
	 * @param closure 締め
	 * @return 年月期間
	 */
	public static Optional<YearMonthPeriod> containsDate(RequireM1 require, 
			String companyId, GeneralDate criteria, 
			Optional<AgreementOperationSetting> agreementOperationSetOpt, Closure closure) {
		
		// 「36協定運用設定」を取得する
		AgreementOperationSetting agreementOpeSet = null;
		if (agreementOperationSetOpt.isPresent()) {
			agreementOpeSet = agreementOperationSetOpt.get();
		}
		else {
			val dbAgreOpeSetOpt = require.agreementOperationSetting(companyId);
			if (dbAgreOpeSetOpt.isPresent()) agreementOpeSet = dbAgreOpeSetOpt.get(); 
		}
		if (agreementOpeSet == null) return Optional.empty(); 
		
		// 年月期間を返す
		return Optional.of(agreementOpeSet.getPeriodYear(criteria, closure));
	}
	
	public static interface RequireM2 extends ClosureService.RequireM3, RequireM1 {
	}
	
	public static interface RequireM1 extends GetAgreementPeriodFromYear.RequireM1 {
	}
}

package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementPeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.pub.monthly.agreement.GetAgreementPeriodPub;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 * 36協定期間を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAgreementPeriodPubImpl implements GetAgreementPeriodPub {

	@Inject 
	private RecordDomRequireService requireService;
	
	/** 年度を指定して36協定期間を取得 */
	@Override
	public Optional<DatePeriod> byYear(String companyId, String employeeId, GeneralDate criteria, Year year) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		return GetAgreementPeriod.byYear(require, cacheCarrier, companyId, employeeId, criteria, year);
	}
	
	/** 指定日を含む年期間を取得 */
	@Override
	public Optional<YearMonthPeriod> containsDate(String companyId, GeneralDate criteria,
			Optional<AgreementOperationSetting> agreementOperationSet, Closure closure) {
		val require = requireService.createRequire();
		
		return GetAgreementPeriod.containsDate(require, companyId, criteria, agreementOperationSet, closure);
	}
}

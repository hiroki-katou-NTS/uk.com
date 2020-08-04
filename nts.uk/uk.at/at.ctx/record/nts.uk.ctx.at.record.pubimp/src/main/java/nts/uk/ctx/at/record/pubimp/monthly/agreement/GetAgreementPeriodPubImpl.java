package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementPeriod;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.pub.monthly.agreement.GetAgreementPeriodPub;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 36協定期間を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAgreementPeriodPubImpl implements GetAgreementPeriodPub {

	/** 36協定期間を取得 */
	@Inject
	private GetAgreementPeriod getAgreementPeriod;
	
	/** 年度を指定して36協定期間を取得 */
	@Override
	public Optional<DatePeriod> byYear(String companyId, String employeeId, GeneralDate criteria, Year year) {
		return this.getAgreementPeriod.byYear(companyId, employeeId, criteria, year);
	}
	
	/** 指定日を含む年期間を取得 */
	@Override
	public Optional<YearMonthPeriod> containsDate(String companyId, GeneralDate criteria,
			Optional<AgreementOperationSetting> agreementOperationSet, Closure closure) {
		return this.getAgreementPeriod.containsDate(companyId, criteria, agreementOperationSet, closure);
	}
}

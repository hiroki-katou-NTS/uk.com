package nts.uk.ctx.at.record.pubimp.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementPeriodFromYear;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.standardtime.GetAgreementPeriodFromYearPub;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 実装：年度から集計期間を取得
 * @author shuichu_ishida
 */
@Stateless
public class GetAgreementPeriodFromYearPubImpl implements GetAgreementPeriodFromYearPub {

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;
	
	/** 年度から集計期間を取得 */
	@Override
	public Optional<DatePeriod> algorithm(Year year, Closure closure) {
		return GetAgreementPeriodFromYear.algorithm(new GetAgreementPeriodFromYear.RequireM1() {
			
			@Override
			public Optional<AgreementOperationSetting> agreementOperationSetting(String companyId) {
				return agreementOperationSettingRepository.find(companyId);
			}
		}, year, closure);
	}
}

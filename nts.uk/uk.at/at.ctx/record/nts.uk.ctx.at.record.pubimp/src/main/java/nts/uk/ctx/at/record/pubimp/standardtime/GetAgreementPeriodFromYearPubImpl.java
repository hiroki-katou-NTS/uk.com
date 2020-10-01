package nts.uk.ctx.at.record.pubimp.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.standardtime.GetAgreementPeriodFromYearPub;
import nts.uk.shr.com.context.AppContexts;

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
	public Optional<DatePeriod> algorithm(Year year) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return agreementOperationSettingRepository.find(AppContexts.user().companyId()).map(c -> c.getPeriodFromYear(year));
//		return GetAgreementPeriodFromYear.algorithm(new GetAgreementPeriodFromYear.RequireM1() {
//			
//			@Override
//			public Optional<AgreementOperationSetting> agreementOperationSetting(String companyId) {
//				return agreementOperationSettingRepository.find(companyId);
//			}
//		}, year, closure);
	}
}

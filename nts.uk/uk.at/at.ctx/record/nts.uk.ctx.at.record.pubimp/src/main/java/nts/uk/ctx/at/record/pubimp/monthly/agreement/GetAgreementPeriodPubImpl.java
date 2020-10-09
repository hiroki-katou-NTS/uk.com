package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.monthly.agreement.GetAgreementPeriodPub;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;

/**
 * 36協定期間を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAgreementPeriodPubImpl implements GetAgreementPeriodPub {

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepo;
	
	/** 年度を指定して36協定期間を取得 */
	@Override
	public Optional<DatePeriod> byYear(String companyId, Year year) {
		val agreementSetting = agreementOperationSettingRepo.find(companyId);
		
		return agreementSetting.map(as -> as.getPeriodFromYear(year));
	}
	
	/** 指定日を含む年期間を取得 */
	@Override
	public Optional<YearMonthPeriod> containsDate(String companyId, GeneralDate baseDate, 
			Optional<AgreementOperationSetting> agreementOperationSet) {
		if (!agreementOperationSet.isPresent()) {
			agreementOperationSet = agreementOperationSettingRepo.find(companyId);
		}
		
		return agreementOperationSet.map(as -> as.getPeriodYear(baseDate));
	}
}

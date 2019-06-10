package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import nts.arc.time.YearMonth;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetNumberOverrunByYMImpl implements GetNumberOverrunByYM {

	@Inject
	private AgreementOperationSettingRepository agreementOpSettRepo;
	
	@Inject
	private GetExcessTimesYear getExcessTimesYear;

	/*
	 * [No.605]年月を指定して年間超過回数の取得
	 */
	@Override
	public Optional<AgreementExcessInfo> getNumberOverrunByYearMonth(String employeeId, YearMonth yearMonth,
			Optional<AgreementOperationSetting> agreeOpSetOpt) {
		String companyId = AppContexts.user().companyId();
		// 年月を指定して、36協定期間の年度を取得する
		if (!agreeOpSetOpt.isPresent()) {
			agreeOpSetOpt = agreementOpSettRepo.find(companyId);
		}

		if (!agreeOpSetOpt.isPresent())
			return Optional.empty();

		//[No.458]年間超過回数の取得
		Year year = getYearAgreementPeriod(agreeOpSetOpt.get(), yearMonth);

		return Optional.ofNullable(getExcessTimesYear.algorithm(employeeId, year));
	}

	// 年月を指定して、36協定期間の年度を取得する
	private Year getYearAgreementPeriod(AgreementOperationSetting agreeOpSet, YearMonth yearMonth) {
		if (yearMonth.month() < (agreeOpSet.getStartingMonth().value + 1))
			return new Year(yearMonth.year() -1);
		return new Year(yearMonth.year());
	}

}

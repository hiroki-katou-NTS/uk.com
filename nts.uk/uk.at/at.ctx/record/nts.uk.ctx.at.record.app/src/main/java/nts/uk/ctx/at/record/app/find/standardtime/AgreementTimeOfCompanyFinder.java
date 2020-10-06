package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfCompanyDto;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 全社 screen
 *
 */
@Stateless
public class AgreementTimeOfCompanyFinder {

	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;

	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepository;

	public AgreementTimeOfCompanyDto findAll(int laborSystemAtr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementTimeOfCompanyDto result = new AgreementTimeOfCompanyDto();

		Optional<AgreementTimeOfCompany> agreementTimeOfCompany = agreementTimeCompanyRepository.find(companyId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));

		if (agreementTimeOfCompany.isPresent()) {
			AgreementTimeOfCompany argTimeCom = agreementTimeOfCompany.get();
			/** TODO: 36協定時間対応により、コメントアウトされた */
//			String basicSettingId = argTimeCom.getBasicSettingId();
//			result.setUpperMonth(argTimeCom.getUpperAgreementSetting().getUpperMonth().v());
//			result.setUpperMonthAverage(argTimeCom.getUpperAgreementSetting().getUpperMonthAverage().v());
//
//			Optional<BasicAgreementSetting> basicAgreementSetting = basicAgreementSettingRepository
//					.find(basicSettingId);
//			if (basicAgreementSetting.isPresent()) {
//				BasicAgreementSetting basicArgSet = basicAgreementSetting.get();
//				result.setAlarmWeek(basicArgSet.getAlarmWeek().v());
//				result.setAlarmTwoWeeks(basicArgSet.getAlarmTwoWeeks().v());
//				result.setAlarmFourWeeks(basicArgSet.getAlarmFourWeeks().v());
//				result.setAlarmOneMonth(basicArgSet.getAlarmOneMonth().v());
//				result.setAlarmTwoMonths(basicArgSet.getAlarmTwoMonths().v());
//				result.setAlarmThreeMonths(basicArgSet.getAlarmThreeMonths().v());
//				result.setAlarmOneYear(basicArgSet.getAlarmOneYear().v());
//
//				result.setErrorWeek(basicArgSet.getErrorWeek().v());
//				result.setErrorTwoWeeks(basicArgSet.getErrorTwoWeeks().v());
//				result.setErrorFourWeeks(basicArgSet.getErrorFourWeeks().v());
//				result.setErrorOneMonth(basicArgSet.getErrorOneMonth().v());
//				result.setErrorTwoMonths(basicArgSet.getErrorTwoMonths().v());
//				result.setErrorThreeMonths(basicArgSet.getErrorThreeMonths().v());
//				result.setErrorOneYear(basicArgSet.getErrorOneYear().v());
//
//				result.setLimitWeek(basicArgSet.getLimitWeek().v());
//				result.setLimitTwoWeeks(basicArgSet.getLimitTwoWeeks().v());
//				result.setLimitFourWeeks(basicArgSet.getLimitFourWeeks().v());
//				result.setLimitOneMonth(basicArgSet.getLimitOneMonth().v());
//				result.setLimitTwoMonths(basicArgSet.getLimitTwoMonths().v());
//				result.setLimitThreeMonths(basicArgSet.getLimitThreeMonths().v());
//				result.setLimitOneYear(basicArgSet.getLimitOneYear().v());
//				
//				result.setUpdateMode(true);
//			}
		}
		return result;
	}
}

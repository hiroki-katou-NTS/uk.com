package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfClassificationDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfClassificationListDto;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 分類 screen
 */
@Stateless
public class AgreementTimeOfClassificationFinder {

	@Inject
	private AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepository;

	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepository;

	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;

	public AgreementTimeOfClassificationListDto findAll(int laborSystemAtr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementTimeOfClassificationListDto agreementTimeOfClassificationListDto = new AgreementTimeOfClassificationListDto();

		// get list classifications have been setting
		List<String> classificationCodes = agreementTimeOfClassificationRepository.findClassificationSetting(companyId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));

		agreementTimeOfClassificationListDto.setClassificationCodes(classificationCodes);

		return agreementTimeOfClassificationListDto;
	}

	public AgreementTimeOfClassificationDetailDto findDetail(int laborSystemAtr, String classificationCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementTimeOfClassificationDetailDto agreementTimeOfClassificationDetail = new AgreementTimeOfClassificationDetailDto();

		// get basicSettingID of Classification selected
		Optional<String> basicSettingIdOfClass = agreementTimeOfClassificationRepository.findEmploymentBasicSettingID(
				companyId, EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class), classificationCode);
		if (basicSettingIdOfClass.isPresent()) {
			// get basicSetting detail of Employment selected
			Optional<BasicAgreementSetting> basicSettingOfClass = basicAgreementSettingRepository
					.find(basicSettingIdOfClass.get());

			// set error time + alarm time
			if (basicSettingOfClass.isPresent()) {
				agreementTimeOfClassificationDetail.setErrorWeek(basicSettingOfClass.get().getErrorWeek().v());
				agreementTimeOfClassificationDetail.setAlarmWeek(basicSettingOfClass.get().getAlarmWeek().v());
				agreementTimeOfClassificationDetail.setErrorTwoWeeks(basicSettingOfClass.get().getErrorTwoWeeks().v());
				agreementTimeOfClassificationDetail.setAlarmTwoWeeks(basicSettingOfClass.get().getAlarmTwoWeeks().v());
				agreementTimeOfClassificationDetail
						.setErrorFourWeeks(basicSettingOfClass.get().getErrorFourWeeks().v());
				agreementTimeOfClassificationDetail
						.setAlarmFourWeeks(basicSettingOfClass.get().getAlarmFourWeeks().v());
				agreementTimeOfClassificationDetail.setErrorOneMonth(basicSettingOfClass.get().getErrorOneMonth().v());
				agreementTimeOfClassificationDetail.setAlarmOneMonth(basicSettingOfClass.get().getAlarmOneMonth().v());
				agreementTimeOfClassificationDetail
						.setErrorTwoMonths(basicSettingOfClass.get().getErrorTwoMonths().v());
				agreementTimeOfClassificationDetail
						.setAlarmTwoMonths(basicSettingOfClass.get().getAlarmTwoMonths().v());
				agreementTimeOfClassificationDetail
						.setErrorThreeMonths(basicSettingOfClass.get().getErrorThreeMonths().v());
				agreementTimeOfClassificationDetail
						.setAlarmThreeMonths(basicSettingOfClass.get().getAlarmThreeMonths().v());
				agreementTimeOfClassificationDetail.setErrorOneYear(basicSettingOfClass.get().getErrorOneYear().v());
				agreementTimeOfClassificationDetail.setAlarmOneYear(basicSettingOfClass.get().getAlarmOneYear().v());
			} else {
				return null;
			}
		}

		// get basicSettingId of Company
		Optional<AgreementTimeOfCompany> agreementTimeOfCompany = agreementTimeCompanyRepository.find(companyId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));
		if (agreementTimeOfCompany.isPresent()) {
			// get Limit Time of company
			Optional<BasicAgreementSetting> basicSettingOfCom = basicAgreementSettingRepository
					.find(agreementTimeOfCompany.get().getBasicSettingId());
			// set limit time
			agreementTimeOfClassificationDetail.setLimitWeek(basicSettingOfCom.get().getLimitWeek().v());
			agreementTimeOfClassificationDetail.setLimitTwoWeeks(basicSettingOfCom.get().getLimitTwoWeeks().v());
			agreementTimeOfClassificationDetail.setLimitFourWeeks(basicSettingOfCom.get().getLimitFourWeeks().v());
			agreementTimeOfClassificationDetail.setLimitOneMonth(basicSettingOfCom.get().getLimitOneMonth().v());
			agreementTimeOfClassificationDetail.setLimitTwoMonths(basicSettingOfCom.get().getLimitTwoMonths().v());
			agreementTimeOfClassificationDetail.setLimitThreeMonths(basicSettingOfCom.get().getLimitThreeMonths().v());
			agreementTimeOfClassificationDetail.setLimitOneYear(basicSettingOfCom.get().getLimitOneYear().v());
		}

		return agreementTimeOfClassificationDetail;
	}
}

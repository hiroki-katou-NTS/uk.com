package nts.uk.ctx.at.record.app.find.standardtime;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfClassificationDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfClassificationListDto;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author nampt 分類 screen
 */
@Stateless
public class AgreementTimeOfClassificationFinder {

	@Inject
	private AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepository;

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

		Optional<AgreementTimeOfClassification> agreementTimeOfClassificationOpt = agreementTimeOfClassificationRepository.find(
				companyId, EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class), classificationCode);
		if (agreementTimeOfClassificationOpt.isPresent()) {
			AgreementTimeOfClassification agreementTimeOfClassification = agreementTimeOfClassificationOpt.get();
			/** TODO: 36協定時間対応により、コメントアウトされた */
//			agreementTimeOfClassificationDetail.setUpperMonth(agreementTimeOfClassification.getUpperAgreementSetting().getUpperMonth().v());
//			agreementTimeOfClassificationDetail.setUpperMonthAverage(agreementTimeOfClassification.getUpperAgreementSetting().getUpperMonthAverage().v());
//			
//			String basicSettingIdOfClass = agreementTimeOfClassification.getBasicSettingId();
//			// get basicSetting detail of Employment selected
//			Optional<BasicAgreementSetting> basicSettingOfClass = basicAgreementSettingRepository
//					.find(basicSettingIdOfClass);
//
//			// set error time + alarm time
//			if (basicSettingOfClass.isPresent()) {
//				agreementTimeOfClassificationDetail.setErrorWeek(basicSettingOfClass.get().getErrorWeek().v());
//				agreementTimeOfClassificationDetail.setAlarmWeek(basicSettingOfClass.get().getAlarmWeek().v());
//				agreementTimeOfClassificationDetail.setErrorTwoWeeks(basicSettingOfClass.get().getErrorTwoWeeks().v());
//				agreementTimeOfClassificationDetail.setAlarmTwoWeeks(basicSettingOfClass.get().getAlarmTwoWeeks().v());
//				agreementTimeOfClassificationDetail
//						.setErrorFourWeeks(basicSettingOfClass.get().getErrorFourWeeks().v());
//				agreementTimeOfClassificationDetail
//						.setAlarmFourWeeks(basicSettingOfClass.get().getAlarmFourWeeks().v());
//				agreementTimeOfClassificationDetail.setErrorOneMonth(basicSettingOfClass.get().getErrorOneMonth().v());
//				agreementTimeOfClassificationDetail.setAlarmOneMonth(basicSettingOfClass.get().getAlarmOneMonth().v());
//				agreementTimeOfClassificationDetail
//						.setErrorTwoMonths(basicSettingOfClass.get().getErrorTwoMonths().v());
//				agreementTimeOfClassificationDetail
//						.setAlarmTwoMonths(basicSettingOfClass.get().getAlarmTwoMonths().v());
//				agreementTimeOfClassificationDetail
//						.setErrorThreeMonths(basicSettingOfClass.get().getErrorThreeMonths().v());
//				agreementTimeOfClassificationDetail
//						.setAlarmThreeMonths(basicSettingOfClass.get().getAlarmThreeMonths().v());
//				agreementTimeOfClassificationDetail.setErrorOneYear(basicSettingOfClass.get().getErrorOneYear().v());
//				agreementTimeOfClassificationDetail.setAlarmOneYear(basicSettingOfClass.get().getAlarmOneYear().v());
//			} else {
//				return null;
//			}
			
		}

		// get basicSettingId of Company
		Optional<AgreementTimeOfCompany> agreementTimeOfCompany = agreementTimeCompanyRepository.find(companyId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));
		if (agreementTimeOfCompany.isPresent()) {
			// get Limit Time of company
			/** TODO: 36協定時間対応により、コメントアウトされた */
//			Optional<BasicAgreementSetting> basicSettingOfCom = basicAgreementSettingRepository
//					.find(agreementTimeOfCompany.get().getBasicSettingId());
//			if(basicSettingOfCom.isPresent()){
//				BasicAgreementSetting basicArgSet = basicSettingOfCom.get();
//				// set limit time
//				agreementTimeOfClassificationDetail.setLimitWeek(basicArgSet.getLimitWeek().v());
//				agreementTimeOfClassificationDetail.setLimitTwoWeeks(basicArgSet.getLimitTwoWeeks().v());
//				agreementTimeOfClassificationDetail.setLimitFourWeeks(basicArgSet.getLimitFourWeeks().v());
//				agreementTimeOfClassificationDetail.setLimitOneMonth(basicArgSet.getLimitOneMonth().v());
//				agreementTimeOfClassificationDetail.setLimitTwoMonths(basicArgSet.getLimitTwoMonths().v());
//				agreementTimeOfClassificationDetail.setLimitThreeMonths(basicArgSet.getLimitThreeMonths().v());
//				agreementTimeOfClassificationDetail.setLimitOneYear(basicArgSet.getLimitOneYear().v());
//			}
		}

		return agreementTimeOfClassificationDetail;
	}
}

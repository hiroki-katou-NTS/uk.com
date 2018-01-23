package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfEmploymentDetailDto;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfEmploymentListDto;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 雇用 screen
 *
 */
@Stateless
public class AgreementTimeOfEmploymentFinder {

	@Inject
	private AgreementTimeOfEmploymentRepostitory agreementTimeOfEmploymentRepostitory;

	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepository;

	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;

	public AgreementTimeOfEmploymentListDto findAll(int laborSystemAtr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		// get list employments have been setting
		List<String> employmentCategoryCodes = agreementTimeOfEmploymentRepostitory.findEmploymentSetting(companyId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));

		AgreementTimeOfEmploymentListDto agreementTimeOfEmploymentListDto = new AgreementTimeOfEmploymentListDto();
		agreementTimeOfEmploymentListDto.setEmploymentCategoryCodes(employmentCategoryCodes);

		return agreementTimeOfEmploymentListDto;
	}

	public AgreementTimeOfEmploymentDetailDto findDetail(int laborSystemAtr, String employmentCategoryCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementTimeOfEmploymentDetailDto agreementTimeOfEmploymentDetailDto = new AgreementTimeOfEmploymentDetailDto();

		// get basicSettingID of Employment selected
		Optional<String> basicSettingIdOfEmp = agreementTimeOfEmploymentRepostitory.findEmploymentBasicSettingId(companyId,
				employmentCategoryCode, EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));
		// get basicSetting detail of Employment selected
		if(basicSettingIdOfEmp.isPresent()){
			Optional<BasicAgreementSetting> basicSettingOfEmp = basicAgreementSettingRepository
					.find(basicSettingIdOfEmp.get());	

			// set error time + alarm time
			if(basicSettingOfEmp.isPresent()){
				agreementTimeOfEmploymentDetailDto.setErrorWeek(basicSettingOfEmp.get().getErrorWeek().v());
				agreementTimeOfEmploymentDetailDto.setAlarmWeek(basicSettingOfEmp.get().getAlarmWeek().v());
				agreementTimeOfEmploymentDetailDto.setErrorTwoWeeks(basicSettingOfEmp.get().getErrorTwoWeeks().v());
				agreementTimeOfEmploymentDetailDto.setAlarmTwoWeeks(basicSettingOfEmp.get().getAlarmTwoWeeks().v());
				agreementTimeOfEmploymentDetailDto.setErrorFourWeeks(basicSettingOfEmp.get().getErrorFourWeeks().v());
				agreementTimeOfEmploymentDetailDto.setAlarmFourWeeks(basicSettingOfEmp.get().getAlarmFourWeeks().v());
				agreementTimeOfEmploymentDetailDto.setErrorOneMonth(basicSettingOfEmp.get().getErrorOneMonth().v());
				agreementTimeOfEmploymentDetailDto.setAlarmOneMonth(basicSettingOfEmp.get().getAlarmOneMonth().v());
				agreementTimeOfEmploymentDetailDto.setErrorTwoMonths(basicSettingOfEmp.get().getErrorTwoMonths().v());
				agreementTimeOfEmploymentDetailDto.setAlarmTwoMonths(basicSettingOfEmp.get().getAlarmTwoMonths().v());
				agreementTimeOfEmploymentDetailDto.setErrorThreeMonths(basicSettingOfEmp.get().getErrorThreeMonths().v());
				agreementTimeOfEmploymentDetailDto.setAlarmThreeMonths(basicSettingOfEmp.get().getAlarmThreeMonths().v());
				agreementTimeOfEmploymentDetailDto.setErrorOneYear(basicSettingOfEmp.get().getErrorOneYear().v());
				agreementTimeOfEmploymentDetailDto.setAlarmOneYear(basicSettingOfEmp.get().getAlarmOneYear().v());			
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
			agreementTimeOfEmploymentDetailDto.setLimitWeek(basicSettingOfCom.get().getLimitWeek().v());
			agreementTimeOfEmploymentDetailDto.setLimitTwoWeeks(basicSettingOfCom.get().getLimitTwoWeeks().v());
			agreementTimeOfEmploymentDetailDto.setLimitFourWeeks(basicSettingOfCom.get().getLimitFourWeeks().v());
			agreementTimeOfEmploymentDetailDto.setLimitOneMonth(basicSettingOfCom.get().getLimitOneMonth().v());
			agreementTimeOfEmploymentDetailDto.setLimitTwoMonths(basicSettingOfCom.get().getLimitTwoMonths().v());
			agreementTimeOfEmploymentDetailDto.setLimitThreeMonths(basicSettingOfCom.get().getLimitThreeMonths().v());
			agreementTimeOfEmploymentDetailDto.setLimitOneYear(basicSettingOfCom.get().getLimitOneYear().v());
		}

		return agreementTimeOfEmploymentDetailDto;
	}

}

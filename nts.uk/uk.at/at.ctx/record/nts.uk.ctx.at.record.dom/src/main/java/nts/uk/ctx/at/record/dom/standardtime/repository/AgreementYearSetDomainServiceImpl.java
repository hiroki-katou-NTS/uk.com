package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AgreementYearSetDomainServiceImpl implements AgreementYearSetDomainService {

	@Inject
	private AgreementYearSettingRepository agreementYearSettingRepository;
	
	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;
	
	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepository;

	@Override
	public List<String> add(AgreementYearSetting agreementYearSetting, Optional<WorkingConditionItem> workingConditionItem) {
		List<String> errors = new ArrayList<>();
		
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId(); 
		
		AgreementOneYearTime limitOneYear = new AgreementOneYearTime(0);
		
		if (workingConditionItem.isPresent()) {
			if (workingConditionItem.get().getLaborSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK) {
				Optional<AgreementTimeOfCompany> agreementTimeOfCompany = this.agreementTimeCompanyRepository.find(companyId, LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
				if (agreementTimeOfCompany.isPresent()) {
					/** TODO: 36協定時間対応により、コメントアウトされた */
//					Optional<BasicAgreementSetting> basicAgreementSetting = this.basicAgreementSettingRepository.find(agreementTimeOfCompany.get().getBasicSettingId());
//					if (basicAgreementSetting.isPresent()) {
//						limitOneYear = basicAgreementSetting.get().getLimitOneYear();
//					}					
				}
			} else {
				Optional<AgreementTimeOfCompany> agreementTimeOfCompany = this.agreementTimeCompanyRepository.find(companyId, LaborSystemtAtr.GENERAL_LABOR_SYSTEM);
				if (agreementTimeOfCompany.isPresent()) {
					/** TODO: 36協定時間対応により、コメントアウトされた */
//					Optional<BasicAgreementSetting> basicAgreementSetting = this.basicAgreementSettingRepository.find(agreementTimeOfCompany.get().getBasicSettingId());
//					if (basicAgreementSetting.isPresent()) {
//						limitOneYear = basicAgreementSetting.get().getLimitOneYear();
//					}					
				}
			}
		}

		/** TODO: 36協定時間対応により、コメントアウトされた */
//		if (agreementYearSetting.getAlarmOneYear().v().compareTo(agreementYearSetting.getErrorOneYear().v()) > 0) {
//			errors.add("Msg_59,KMK008_43,KMK008_42");
//		}
//
//		if (limitOneYear.v() > 0 && agreementYearSetting.getErrorOneYear().v().compareTo(limitOneYear.v()) > 0) {
//			errors.add("Msg_59,KMK008_42,KMK008_44");
//		}

		if (errors.isEmpty()) {
			this.agreementYearSettingRepository.add(agreementYearSetting);
		}

		return errors;
	}

	@Override
	public List<String> update(AgreementYearSetting agreementYearSetting, Optional<WorkingConditionItem> workingConditionItem, Integer yearMonthValueOld) {
		List<String> errors = new ArrayList<>();
		
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId(); 
		
		AgreementOneYearTime limitOneYear = new AgreementOneYearTime(0);
		
		if (workingConditionItem.isPresent()) {
			if (workingConditionItem.get().getLaborSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK) {
				Optional<AgreementTimeOfCompany> agreementTimeOfCompany = this.agreementTimeCompanyRepository.find(companyId, LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
				if (agreementTimeOfCompany.isPresent()) {
					/** TODO: 36協定時間対応により、コメントアウトされた */
//					Optional<BasicAgreementSetting> basicAgreementSetting = this.basicAgreementSettingRepository.find(agreementTimeOfCompany.get().getBasicSettingId());
//					if (basicAgreementSetting.isPresent()) {
//						limitOneYear = basicAgreementSetting.get().getLimitOneYear();
//					}					
				}
			} else {
				Optional<AgreementTimeOfCompany> agreementTimeOfCompany = this.agreementTimeCompanyRepository.find(companyId, LaborSystemtAtr.GENERAL_LABOR_SYSTEM);
				if (agreementTimeOfCompany.isPresent()) {
					/** TODO: 36協定時間対応により、コメントアウトされた */
//					Optional<BasicAgreementSetting> basicAgreementSetting = this.basicAgreementSettingRepository.find(agreementTimeOfCompany.get().getBasicSettingId());
//					if (basicAgreementSetting.isPresent()) {
//						limitOneYear = basicAgreementSetting.get().getLimitOneYear();
//					}					
				}
			}
		}

		/** TODO: 36協定時間対応により、コメントアウトされた */
//		if (agreementYearSetting.getAlarmOneYear().v().compareTo(agreementYearSetting.getErrorOneYear().v()) > 0) {
//			errors.add("Msg_59,KMK008_43,KMK008_42");
//		}
//		if (limitOneYear.v() > 0 && agreementYearSetting.getErrorOneYear().v().compareTo(limitOneYear.v()) > 0) {
//			errors.add("Msg_59,KMK008_42,KMK008_44");
//		}
//		Optional<AgreementYearSetting> agreementYear = this.agreementYearSettingRepository.findByKey(agreementYearSetting.getEmployeeId(), agreementYearSetting.getYearValue()); 
//		if(agreementYear.isPresent()){
//			errors.add("Msg_61,KMK008_29");
//		}

		if (errors.isEmpty()) {
			this.agreementYearSettingRepository.updateById(agreementYearSetting, yearMonthValueOld);
		}
		return errors;
	}

	@Override
	public void delete(String employeeId, int yearValue) {
		this.agreementYearSettingRepository.delete(employeeId, yearValue);
	}

	@Override
	public boolean checkExistData(String employeeId, BigDecimal yearValue) {
		return this.agreementYearSettingRepository.checkExistData(employeeId, yearValue);
	}

}

package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AgreementMonthSetDomainServiceImpl implements AgreementMonthSetDomainService{
	
	@Inject
	private AgreementMonthSettingRepository agreementMonthSettingRepository;
	
	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;
	
	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepository;

	@Override
	public List<String> add(AgreementMonthSetting agreementMonthSetting, Optional<WorkingConditionItem> workingConditionItem) {
		List<String> errors = new ArrayList<>();
		
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId(); 
		
		LimitOneMonth limitOneMonth = new LimitOneMonth(0);
		
		if (workingConditionItem.isPresent()) {
			if (workingConditionItem.get().getLaborSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK) {
				Optional<AgreementTimeOfCompany> agreementTimeOfCompany = this.agreementTimeCompanyRepository.find(companyId, LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
				if (agreementTimeOfCompany.isPresent()) {
					Optional<BasicAgreementSetting> basicAgreementSetting = this.basicAgreementSettingRepository.find(agreementTimeOfCompany.get().getBasicSettingId());
					if (basicAgreementSetting.isPresent()) {
						limitOneMonth = basicAgreementSetting.get().getLimitOneMonth();
					}					
				}
			} else {
				Optional<AgreementTimeOfCompany> agreementTimeOfCompany = this.agreementTimeCompanyRepository.find(companyId, LaborSystemtAtr.GENERAL_LABOR_SYSTEM);
				if (agreementTimeOfCompany.isPresent()) {
					Optional<BasicAgreementSetting> basicAgreementSetting = this.basicAgreementSettingRepository.find(agreementTimeOfCompany.get().getBasicSettingId());
					if (basicAgreementSetting.isPresent()) {
						limitOneMonth = basicAgreementSetting.get().getLimitOneMonth();
					}					
				}
			}
		}
		
		if(agreementMonthSetting.getAlarmOneMonth().v().compareTo(agreementMonthSetting.getErrorOneMonth().v()) > 0){
			errors.add("Msg_59,KMK008_43,KMK008_42");
		}
		
		if(limitOneMonth.v() > 0 && agreementMonthSetting.getErrorOneMonth().v().compareTo(limitOneMonth.v()) > 0){
			errors.add("Msg_59,KMK008_42,KMK008_44");
		}
		
		if (errors.isEmpty()) {
			this.agreementMonthSettingRepository.add(agreementMonthSetting);
		}
		
		return errors;
	}

	@Override
	public List<String> update(AgreementMonthSetting agreementMonthSetting, Optional<WorkingConditionItem> workingConditionItem, Integer yearMonthValueOld) {
		List<String> errors = new ArrayList<>();
		
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId(); 
		
		LimitOneMonth limitOneMonth = new LimitOneMonth(0);
		
		if (workingConditionItem.isPresent()) {
			if (workingConditionItem.get().getLaborSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK) {
				Optional<AgreementTimeOfCompany> agreementTimeOfCompany = this.agreementTimeCompanyRepository.find(companyId, LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
				if (agreementTimeOfCompany.isPresent()) {
					Optional<BasicAgreementSetting> basicAgreementSetting = this.basicAgreementSettingRepository.find(agreementTimeOfCompany.get().getBasicSettingId());
					if (basicAgreementSetting.isPresent()) {
						limitOneMonth = basicAgreementSetting.get().getLimitOneMonth();
					}					
				}
			} else {
				Optional<AgreementTimeOfCompany> agreementTimeOfCompany = this.agreementTimeCompanyRepository.find(companyId, LaborSystemtAtr.GENERAL_LABOR_SYSTEM);
				if (agreementTimeOfCompany.isPresent()) {
					Optional<BasicAgreementSetting> basicAgreementSetting = this.basicAgreementSettingRepository.find(agreementTimeOfCompany.get().getBasicSettingId());
					if (basicAgreementSetting.isPresent()) {
						limitOneMonth = basicAgreementSetting.get().getLimitOneMonth();
					}					
				}
			}
		}
		
		if(agreementMonthSetting.getAlarmOneMonth().v().compareTo(agreementMonthSetting.getErrorOneMonth().v()) > 0){
			errors.add("Msg_59,KMK008_43,KMK008_42");
		}
		
		if(limitOneMonth.v() > 0 && agreementMonthSetting.getErrorOneMonth().v().compareTo(limitOneMonth.v()) > 0){
			errors.add("Msg_59,KMK008_42,KMK008_44");
		}
		
		if (errors.isEmpty()) {
			// fix bug 100605
			// this.agreementMonthSettingRepository.update(agreementMonthSetting);
			this.agreementMonthSettingRepository.updateById(agreementMonthSetting, yearMonthValueOld);
		}
		
		return errors;
	}

	@Override
	public void delete(String employeeId, BigDecimal yearMonthValue) {
		this.agreementMonthSettingRepository.delete(employeeId, yearMonthValue);
	}

	@Override
	public boolean checkExistData(String employeeId, BigDecimal yearMonthValue) {
		return this.agreementMonthSettingRepository.checkExistData(employeeId, yearMonthValue);
	}

}

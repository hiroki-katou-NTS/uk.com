package nts.uk.ctx.at.record.dom.standardtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.UseClassificationAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public class BasicAgreementSettingsGetter {
	
	private AgreementUnitSetting  unitSetting = null; 
	
	private List<AffClassificationSidImport> affClassifications = new ArrayList<>();
	private List<AgreementTimeOfClassification> agreeTimeClassifi = new ArrayList<>();
	
	private Map<GeneralDate, Map<String, List<String>>> empToWorkplaceId = new HashMap<>();
	private Map<String, List<AgreementTimeOfWorkPlace>> agreeTimeWP = new HashMap<>();
	
	private Map<String, List<SyEmploymentImport>> employments = new HashMap<>();
	private Map<EmploymentCode, List<AgreementTimeOfEmployment>> agreeTimeEmployment = new HashMap<>();
	
	private List<AgreementTimeOfCompany> agreeTimeCompany = new ArrayList<>();
	
	public BasicAgreementSettingsGetter(AgreementUnitSetting unitSetting,
			List<AffClassificationSidImport> affClassifications, List<AgreementTimeOfClassification> agreeTimeClassifi,
			Map<GeneralDate, Map<String, List<String>>> empToWorkplaceId,
			Map<String, List<AgreementTimeOfWorkPlace>> agreeTimeWP, Map<String, List<SyEmploymentImport>> employments,
			Map<EmploymentCode, List<AgreementTimeOfEmployment>> agreeTimeEmployment, List<AgreementTimeOfCompany> agreeTimeCompany) {
		this.unitSetting = unitSetting;
		this.affClassifications = affClassifications;
		this.agreeTimeClassifi = agreeTimeClassifi;
		this.empToWorkplaceId = empToWorkplaceId;
		this.agreeTimeWP = agreeTimeWP;
		this.employments = employments;
		this.agreeTimeEmployment = agreeTimeEmployment;
		this.agreeTimeCompany = agreeTimeCompany;
	}

	public BasicAgreementSetting getBasicSet(String companyId, String employeeId, GeneralDate criteriaDate,
			WorkingSystem workingSystem) {
		LaborSystemtAtr laborSystemAtr = workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK 
				? LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM : LaborSystemtAtr.GENERAL_LABOR_SYSTEM;

		if (this.unitSetting.getClassificationUseAtr() == UseClassificationAtr.USE) {
			// 分類36協定時間を取得する
			val afc = this.affClassifications.stream().filter(ac -> ac.getEmployeeId().equals(employeeId) 
					&& ac.getDateRange().contains(criteriaDate)).findFirst();
			if(afc.isPresent()){
				val atc = this.agreeTimeClassifi.stream()
						.filter(atci -> atci.getClassificationCode().equals(afc.get().getClassificationCode()) 
										&& atci.getLaborSystemAtr() == laborSystemAtr)
						.findFirst();
				if(atc.isPresent()){
					return atc.get().getSetting();
				}
			}
		}
		if (this.unitSetting.getWorkPlaceUseAtr() == UseClassificationAtr.USE) {
			Map<String, List<String>> empWp = this.empToWorkplaceId.get(criteriaDate);
			if(empWp != null && empWp.containsKey(employeeId)) {
				for(String workplaceId : empWp.get(employeeId)) {
					AgreementTimeOfWorkPlace atwp = this.agreeTimeWP.get(workplaceId).stream()
							.filter(x ->x.getLaborSystemAtr() == laborSystemAtr)
							.findFirst().orElse(null);
					if(atwp != null){
						return atwp.getSetting();
					}
				};
			}
		}
		if (this.unitSetting.getEmploymentUseAtr() == UseClassificationAtr.USE){
			if(this.employments.containsKey(employeeId)) {
				val employment = this.employments.get(employeeId).stream().filter(e -> e.getPeriod().contains(criteriaDate)).findFirst();
				if(employment.isPresent() && this.agreeTimeEmployment.containsKey(employment.get().getEmploymentCode())){
					val atwp = this.agreeTimeEmployment.get(employment.get().getEmploymentCode()).stream()
							.filter(x ->x.getLaborSystemAtr() == laborSystemAtr)
							.findFirst().orElse(null);
					
					if(atwp != null){
						return atwp.getSetting();
					}
				}
			}
		}
		// 会社36協定時間を取得する
		val agreementTimeOfCmpOpt = this.agreeTimeCompany.stream().filter(atc -> atc.getLaborSystemAtr() == laborSystemAtr).findFirst();
		if (agreementTimeOfCmpOpt.isPresent()){
			return agreementTimeOfCmpOpt.get().getSetting();
		}

		return AgreementDomainService.getDefault();
	}
	
}

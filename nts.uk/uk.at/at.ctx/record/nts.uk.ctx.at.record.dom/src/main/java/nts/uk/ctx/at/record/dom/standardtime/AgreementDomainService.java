package nts.uk.ctx.at.record.dom.standardtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.UseClassificationAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.limitrule.AgreementMultiMonthAvg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSettingForCalc;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * ???????????????????????????36??????
 * @author shuichi_ishida
 */
public class AgreementDomainService {

	/**
	 * ??????????????????36?????????????????????????????????
	 * @param companyId ??????ID
	 * @param employeeId ??????ID
	 * @param criteriaDate ?????????
	 * @param year ??????
	 * @return 36??????????????????
	 */
	public static BasicAgreementSettingForCalc getBasicSet(RequireM5 require, String companyId, String employeeId, GeneralDate criteriaDate, Year year) {
		
		/** 36????????????????????????????????? */
		val basicSetting = getBasicSet(require, companyId, employeeId, criteriaDate);
		
		val basicSetForCalc = new BasicAgreementSettingForCalc(basicSetting, false);
		
		/** ????????????36???????????????????????????????????? */
		val personYearSetting = require.agreementYearSetting(employeeId, year.v());

		personYearSetting.ifPresent(pys -> {
			
			/** ???????????????36????????????????????????1???????????????????????? */
			basicSetting.getOneYear().updateWithEmpSet(pys.getOneYearTime());
			basicSetForCalc.personAgreementSetted();
		});
		
		/** ???36?????????????????????????????? */
		return basicSetForCalc;
	}
	
	/**
	 * ??????????????????36?????????????????????????????????
	 * @param companyId ??????ID
	 * @param employeeId ??????ID
	 * @param criteriaDate ?????????
	 * @param ym ??????
	 * @return 36??????????????????
	 */
	public static BasicAgreementSettingForCalc getBasicSet(RequireM6 require, String companyId, String employeeId, GeneralDate criteriaDate, YearMonth ym) {
		
		/** 36????????????????????????????????? */
		val basicSetting = getBasicSet(require, companyId, employeeId, criteriaDate);
		
		val basicSetForCalc = new BasicAgreementSettingForCalc(basicSetting, false);
		
		/** ?????????????????????????????????????????????????????? */
		val personYMSetting = require.agreementMonthSetting(employeeId, ym);

		personYMSetting.ifPresent(pys -> {
			
			/** ????????????36???????????????????????????????????????????????? */
			basicSetting.getOneMonth().updateWithEmpSet(pys.getOneMonthTime());
			basicSetForCalc.personAgreementSetted();
		});
		
		/** ???36?????????????????????????????? */
		return basicSetForCalc;
	}
	
	/**
	 * clones from ??????????????????36?????????????????????????????????
	 */
	public static Map<String, BasicAgreementSettingForCalc> getBasicSetClones(RequireM6 require, String companyId, List<String> employeeId, GeneralDate criteriaDate, YearMonth ym) {
		
		/** 36????????????????????????????????? */
		val basicSettingMap = getBasicSetClones(require, companyId, employeeId, criteriaDate);
		
		
		/** ?????????????????????????????????????????????????????? */
		val personYMSettings = require.agreementMonthSettingClones(employeeId, ym);
		
		Map<String, BasicAgreementSettingForCalc> results = new HashMap<String, BasicAgreementSettingForCalc>();
		for(String id: employeeId) {
			BasicAgreementSetting basicSetting = basicSettingMap.get(id);
			val basicSetForCalc = new BasicAgreementSettingForCalc(basicSetting, false);
			Optional<AgreementMonthSetting> personYMSetting = personYMSettings.stream().filter(c->c.getEmployeeId().equals(id)).findAny();
			personYMSetting.ifPresent(pys -> {
				
				/** ????????????36???????????????????????????????????????????????? */
				basicSetting.getOneMonth().updateWithEmpSet(pys.getOneMonthTime());
				basicSetForCalc.personAgreementSetted();
			});
			results.put(id, basicSetForCalc);
		}
		/** ???36?????????????????????????????? */
		return results;
	}
	
	/**
	 * ?????????????????????36?????????????????????????????????
	 * @param companyId ??????ID
	 * @param employeeId ??????ID
	 * @param criteriaDate ?????????
	 * @return 36??????????????????
	 */
	public static BasicAgreementSetting getBasicSet(RequireM4 require, String companyId, String employeeId, GeneralDate criteriaDate) {
		
		/** ??????????????????????????????????????????????????????????????? */
		val workCondition = require.workingConditionItem(employeeId, criteriaDate);
		if (!workCondition.isPresent()) {
			return getDefault();
		}
		
		return getBasicSet(require, companyId, employeeId, criteriaDate, workCondition.get().getLaborSystem());
	}
	
	/**
	 * clones from ?????????????????????36?????????????????????????????????
	 */
	public static Map<String, BasicAgreementSetting> getBasicSetClones(RequireM4 require, String companyId, List<String> employeeIds, GeneralDate criteriaDate) {
		
		/** ??????????????????????????????????????????????????????????????? */
		val workConditionList = require.workingConditionItemClones(employeeIds, criteriaDate);
		
		Map<String, BasicAgreementSetting> result = new HashMap<String, BasicAgreementSetting>();
		Map<String, WorkingSystem>  employeeIdWorkingSystem = new HashMap<String, WorkingSystem>();
		for(val id: employeeIds) {
			val workCondition = workConditionList.stream().filter(c->c.getEmployeeId().equals(id)).findAny();
			if (!workCondition.isPresent()) {
				result.put(id, getDefault());
			}else {
				employeeIdWorkingSystem.put(id, workCondition.get().getLaborSystem());
			}
		}
		result.putAll(getBasicSetClones(require, companyId, employeeIdWorkingSystem, criteriaDate));
		return result;
	}
	
	/**
	 * ?????????????????????36?????????????????????????????????
	 * @param companyId ??????ID
	 * @param employeeId ??????ID
	 * @param criteriaDate ?????????
	 * @param workingSystem ?????????
	 * @return 36??????????????????
	 */
	public static BasicAgreementSetting getBasicSet(RequireM3 require, String companyId, String employeeId, GeneralDate criteriaDate,
			WorkingSystem workingSystem) {
		
		// ???36????????????????????????????????????
		AgreementUnitSetting agreementUnitSet = new AgreementUnitSetting(companyId,
				UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE);
		val agreementUnitSetOpt = require.agreementUnitSetting(companyId);
		if (agreementUnitSetOpt.isPresent()) agreementUnitSet = agreementUnitSetOpt.get();

		// 36??????????????????????????????
		LaborSystemtAtr laborSystemAtr = LaborSystemtAtr.GENERAL_LABOR_SYSTEM;
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			laborSystemAtr = LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM;
		}
		
		if (agreementUnitSet.getClassificationUseAtr() == UseClassificationAtr.USE){
			
			// ??????36???????????????????????????
			val affClassficationOpt = require.affEmployeeClassification(companyId, employeeId, criteriaDate);
			if (affClassficationOpt.isPresent()){
				val classCd = affClassficationOpt.get().getClassificationCode();
				val agreementTimeOfCls = require.agreementTimeOfClassification(companyId, laborSystemAtr, classCd);
				if (agreementTimeOfCls.isPresent()){
					return agreementTimeOfCls.get().getSetting();
				}
			}
		}
		if (agreementUnitSet.getWorkPlaceUseAtr() == UseClassificationAtr.USE){
			
			// ??????36???????????????????????????
			val workplaceIds = require.getCanUseWorkplaceForEmp(
					companyId, employeeId, criteriaDate);
			for (String workplaceId : workplaceIds){
				val agreementTimeOfWkp = require.agreementTimeOfWorkPlace(
						workplaceId, laborSystemAtr);
				if (agreementTimeOfWkp.isPresent()){
					return agreementTimeOfWkp.get().getSetting();
				}
			}
		}
		if (agreementUnitSet.getEmploymentUseAtr() == UseClassificationAtr.USE){
			
			// ??????36???????????????????????????
			val syEmploymentOpt = require.employment(companyId, employeeId, criteriaDate);
			if (syEmploymentOpt.isPresent()){
				val employmentCd = syEmploymentOpt.get().getEmploymentCode();
				val agreementTimeOfEmp = require.agreementTimeOfEmployment(
						companyId, employmentCd, laborSystemAtr);
				if (agreementTimeOfEmp.isPresent()){
					return agreementTimeOfEmp.get().getSetting();
				}
			}
		}
		
		// ??????36???????????????????????????
		val agreementTimeOfCmpOpt = require.agreementTimeOfCompany(companyId, laborSystemAtr);
		if (agreementTimeOfCmpOpt.isPresent()){
			return agreementTimeOfCmpOpt.get().getSetting();
		}
		
		// ???????????????0?????????
		return getDefault();
	}
	
	/**
	 * clones from ?????????????????????36?????????????????????????????????
	 */
	public static Map<String, BasicAgreementSetting> getBasicSetClones(RequireM3 require, String companyId, Map<String, WorkingSystem> employeeIdworkingSystem, GeneralDate criteriaDate) {
		
		// ???36????????????????????????????????????
		AgreementUnitSetting agreementUnitSet = new AgreementUnitSetting(companyId,
				UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE);
		val agreementUnitSetOpt = require.agreementUnitSetting(companyId);
		if (agreementUnitSetOpt.isPresent()) agreementUnitSet = agreementUnitSetOpt.get();
		Map<String, BasicAgreementSetting> result = new HashMap<String, BasicAgreementSetting>();
		for(val emp: employeeIdworkingSystem.entrySet()) {
			String employeeId = emp.getKey();
			
			// 36??????????????????????????????
			LaborSystemtAtr laborSystemAtr = LaborSystemtAtr.GENERAL_LABOR_SYSTEM;
			if (emp.getValue() == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
				laborSystemAtr = LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM;
			}
			
			if (agreementUnitSet.getClassificationUseAtr() == UseClassificationAtr.USE){
				
				// ??????36???????????????????????????
				val affClassficationOpt = require.affEmployeeClassification(companyId, employeeId, criteriaDate);
				if (affClassficationOpt.isPresent()){
					val classCd = affClassficationOpt.get().getClassificationCode();
					val agreementTimeOfCls = require.agreementTimeOfClassification(companyId, laborSystemAtr, classCd);
					if (agreementTimeOfCls.isPresent()){
						result.put(employeeId, agreementTimeOfCls.get().getSetting());
						continue;
					}
				}
			}
			if (agreementUnitSet.getWorkPlaceUseAtr() == UseClassificationAtr.USE){
				
				// ??????36???????????????????????????
				val workplaceIds = require.getCanUseWorkplaceForEmp(companyId, employeeId, criteriaDate);
				boolean kt = false;
				for (String workplaceId : workplaceIds){
					val agreementTimeOfWkp = require.agreementTimeOfWorkPlace(workplaceId, laborSystemAtr);
					if (agreementTimeOfWkp.isPresent()){
						result.put(employeeId, agreementTimeOfWkp.get().getSetting());
						kt = true;
						break;
					}
				}
				if(kt) continue;
			}
			if (agreementUnitSet.getEmploymentUseAtr() == UseClassificationAtr.USE){
				
				// ??????36???????????????????????????
				val syEmploymentOpt = require.employment(companyId, employeeId, criteriaDate);
				if (syEmploymentOpt.isPresent()){
					val employmentCd = syEmploymentOpt.get().getEmploymentCode();
					val agreementTimeOfEmp = require.agreementTimeOfEmployment(companyId, employmentCd, laborSystemAtr);
					if (agreementTimeOfEmp.isPresent()){
						result.put(employeeId, agreementTimeOfEmp.get().getSetting());
						continue;
					}
				}
			}
			
			// ??????36???????????????????????????
			val agreementTimeOfCmpOpt = require.agreementTimeOfCompany(companyId, laborSystemAtr);
			if (agreementTimeOfCmpOpt.isPresent()){
				result.put(employeeId, agreementTimeOfCmpOpt.get().getSetting());
				continue;
			}
			result.put(employeeId, getDefault());
		}
		// ???????????????0?????????
		return result;
	}
	
	public static BasicAgreementSettingsGetter getBasicSet(RequireM2 require, String companyId, List<String> employeeIds, DatePeriod datePeriod) {
		// ???36????????????????????????????????????
		List<AffClassificationSidImport> affClassifications = new ArrayList<>();
		List<AgreementTimeOfClassification> agreeTimeClassifi = new ArrayList<>();
		Map<GeneralDate, Map<String, List<String>>> empToWorkplaceId = new HashMap<>();
		Map<String, List<AgreementTimeOfWorkPlace>> agreeTimeWP = new HashMap<>();
		Map<String, List<SyEmploymentImport>> employments = new HashMap<>();
		Map<EmploymentCode, List<AgreementTimeOfEmployment>> agreeTimeEmployment = new HashMap<>();
		List<AgreementTimeOfCompany> agreeTimeCompany = new ArrayList<>();
		
		AgreementUnitSetting agreementUnitSet = require.agreementUnitSetting(companyId).orElseGet(() -> new AgreementUnitSetting(companyId,
				UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE));
		
		if (agreementUnitSet.getClassificationUseAtr() == UseClassificationAtr.USE){
			affClassifications = require.affEmployeeClassification(companyId, employeeIds, datePeriod);
			
			agreeTimeClassifi = require.agreementTimeOfClassification(companyId, affClassifications.stream()
																.map(c -> c.getClassificationCode()).distinct().collect(Collectors.toList()));
		}
		
		if(agreementUnitSet.getWorkPlaceUseAtr() == UseClassificationAtr.USE){
			empToWorkplaceId.putAll(require.getCanUseWorkplaceForEmp(companyId, employeeIds, datePeriod));
			
			List<String> workplaceIds = empToWorkplaceId.entrySet().stream().map(c -> 
													c.getValue().values().stream().flatMap(List::stream).distinct().collect(Collectors.toList()))
											.flatMap(List::stream).distinct().collect(Collectors.toList());
			
			// ??????36???????????????????????????
			agreeTimeWP = require.agreementTimeOfWorkPlace(workplaceIds)
					.stream().collect(Collectors.groupingBy(AgreementTimeOfWorkPlace::getWorkplaceId));
		}
		
		if(agreementUnitSet.getEmploymentUseAtr() == UseClassificationAtr.USE){
			employments = require.employment(employeeIds, datePeriod);
			List<String> employmentCodes = employments.entrySet().stream().map(c -> 
																c.getValue().stream().map(h -> h.getEmploymentCode()).collect(Collectors.toList()))
															.flatMap(List::stream).distinct().collect(Collectors.toList());
			
			agreeTimeEmployment = require.agreementTimeOfEmployment(companyId, employmentCodes)
					.stream().collect(Collectors.groupingBy(AgreementTimeOfEmployment::getEmploymentCategoryCode));

		}
		
		agreeTimeCompany = require.agreementTimeOfCompany(companyId);
		
		return new BasicAgreementSettingsGetter(agreementUnitSet, affClassifications, agreeTimeClassifi, empToWorkplaceId, 
				agreeTimeWP, employments, agreeTimeEmployment, agreeTimeCompany);
	}
	
	public static BasicAgreementSetting getDefault() {
		// ???????????????0?????????
		return new BasicAgreementSetting(
				new AgreementOneMonth(),
				new AgreementOneYear(),
				new AgreementMultiMonthAvg(),
				AgreementOverMaxTimes.ZERO_TIMES);
	}

	public static interface RequireM6 extends RequireM4 {
	
		Optional<AgreementMonthSetting> agreementMonthSetting(String sid, YearMonth yearMonth);
		
		List<AgreementMonthSetting> agreementMonthSettingClones(List<String> sid, YearMonth yearMonth);
	}
	
	public static interface RequireM5 extends RequireM4 {
	
		Optional<AgreementYearSetting> agreementYearSetting(String sid, int year);
	}
	
	public static interface RequireM4 extends RequireM3 {
		
		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);
		
		List<WorkingConditionItem> workingConditionItemClones(List<String> employeeId, GeneralDate baseDate);
	}
	
	public static interface RequireM3 {
		
		Optional<AgreementUnitSetting> agreementUnitSetting(String companyId);
		
		Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode);
		
		List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId,
				LaborSystemtAtr laborSystemAtr);
		
		Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId, String employmentCategoryCode,
				LaborSystemtAtr laborSystemAtr);
		
		Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr);
	}
	
	public static interface RequireM2 {
		
		Optional<AgreementUnitSetting> agreementUnitSetting(String companyId);
		
		List<AffClassificationSidImport> affEmployeeClassification(String companyId, List<String> employeeId, DatePeriod baseDate);
		
		List<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, List<String> classificationCode);
		
		Map<GeneralDate, Map<String, List<String>>> getCanUseWorkplaceForEmp(String companyId, List<String> employeeId, DatePeriod baseDate);
		
		List<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(List<String> workplaceId);
		
		Map<String, List<SyEmploymentImport>> employment(List<String> employeeId, DatePeriod baseDate);
		
		List<AgreementTimeOfEmployment> agreementTimeOfEmployment(String comId, List<String> employmentCategoryCode);
		
		List<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId);
	}
}

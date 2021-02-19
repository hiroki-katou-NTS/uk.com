package nts.uk.ctx.at.record.dom.standardtime.repository;

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
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSettingsGetter;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * ドメインサービス：36協定
 * @author shuichi_ishida
 */
public class AgreementDomainService {

	/**
	 * 年度指定して36協定基本設定を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param year 年度
	 * @return 36協定基本設定
	 */
	public static BasicAgreementSetting getBasicSet(RequireM5 require, String companyId, String employeeId, GeneralDate criteriaDate, Year year) {
		
		/** 36協定基本設定を取得する */
		val basicSetting = getBasicSet(require, companyId, employeeId, criteriaDate);
		
		/** 個人の「36協定年間設定」を取得する */
		val personYearSetting = require.agreementYearSetting(employeeId, year.v());

		personYearSetting.ifPresent(pys -> {
			
			/** 取得した「36協定基本設定」。1年間を上書きする */
			basicSetting.getOneYear().getSpecConditionLimit().setErAlTime(pys.getOneYearTime());
		});
		
		/** 「36協定基本設定」を返す */
		return basicSetting;
	}
	
	/**
	 * 年月指定して36協定基本設定を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param ym 年月
	 * @return 36協定基本設定
	 */
	public static BasicAgreementSetting getBasicSet(RequireM6 require, String companyId, String employeeId, GeneralDate criteriaDate, YearMonth ym) {
		
		/** 36協定基本設定を取得する */
		val basicSetting = getBasicSet(require, companyId, employeeId, criteriaDate);
		
		/** 個人の「３６協定年月設定」を取得する */
		val personYMSetting = require.agreementMonthSetting(employeeId, ym);

		personYMSetting.ifPresent(pys -> {
			
			/** 取得した36協定基本設定。１ヶ月を上書きする */
			basicSetting.getOneMonth().getSpecConditionLimit().setErAlTime(pys.getOneMonthTime());
		});
		
		/** 「36協定基本設定」を返す */
		return basicSetting;
	}
	
	/**
	 * 年月日指定して36協定基本設定を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @return 36協定基本設定
	 */
	public static BasicAgreementSetting getBasicSet(RequireM4 require, String companyId, String employeeId, GeneralDate criteriaDate) {
		
		/** ●ドメインモデル「労働契約履歴」を取得する */
		val workCondition = require.workingConditionItem(employeeId, criteriaDate);
		if (!workCondition.isPresent()) {
			return getDefault();
		}
		
		return getBasicSet(require, companyId, employeeId, criteriaDate, workCondition.get().getLaborSystem());
	}
	
	/**
	 * 年月日指定して36協定基本設定を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param workingSystem 労働制
	 * @return 36協定基本設定
	 */
	public static BasicAgreementSetting getBasicSet(RequireM3 require, String companyId, String employeeId, GeneralDate criteriaDate,
			WorkingSystem workingSystem) {
		
		// 「36協定単位設定」を取得する
		AgreementUnitSetting agreementUnitSet = new AgreementUnitSetting(companyId,
				UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE);
		val agreementUnitSetOpt = require.agreementUnitSetting(companyId);
		if (agreementUnitSetOpt.isPresent()) agreementUnitSet = agreementUnitSetOpt.get();

		// 36協定労働制を確認する
		LaborSystemtAtr laborSystemAtr = LaborSystemtAtr.GENERAL_LABOR_SYSTEM;
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			laborSystemAtr = LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM;
		}
		
		if (agreementUnitSet.getClassificationUseAtr() == UseClassificationAtr.USE){
			
			// 分類36協定時間を取得する
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
			
			// 職場36協定時間を取得する
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
			
			// 雇用36協定時間を取得する
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
		
		// 会社36協定時間を取得する
		val agreementTimeOfCmpOpt = require.agreementTimeOfCompany(companyId, laborSystemAtr);
		if (agreementTimeOfCmpOpt.isPresent()){
			return agreementTimeOfCmpOpt.get().getSetting();
		}
		
		// 全ての値を0で返す
		return getDefault();
	}
	
	public static BasicAgreementSettingsGetter getBasicSet(RequireM2 require, String companyId, List<String> employeeIds, DatePeriod datePeriod) {
		// 「36協定単位設定」を取得する
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
			
			// 職場36協定時間を取得する
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
		// 全ての値を0で返す
		return new BasicAgreementSetting(
				new AgreementOneMonth(),
				new AgreementOneYear(),
				new AgreementMultiMonthAvg(),
				AgreementOverMaxTimes.ZERO_TIMES);
	}

	public static interface RequireM6 extends RequireM4 {
	
		Optional<AgreementMonthSetting> agreementMonthSetting(String sid, YearMonth yearMonth);
	}
	
	public static interface RequireM5 extends RequireM4 {
	
		Optional<AgreementYearSetting> agreementYearSetting(String sid, int year);
	}
	
	public static interface RequireM4 extends RequireM3 {
		
		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);
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

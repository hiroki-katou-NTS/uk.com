package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfClassification;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfEmployment;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.record.dom.standardtime.AgreementUnitSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSettingsGetter;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.UseClassificationAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmWeek;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorWeek;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * ドメインサービス：36協定
 * @author shuichi_ishida
 */
public class AgreementDomainService {

	/**
	 * 36協定基本設定を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param workingSystem 労働制
	 * @return 36協定基本設定
	 */
	public static BasicAgreementSettings getBasicSet(RequireM3 require, String companyId, String employeeId, GeneralDate criteriaDate,
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
					val basicAgreementSetOpt = require.basicAgreementSetting(
							agreementTimeOfCls.get().getBasicSettingId());
					if (basicAgreementSetOpt.isPresent()) {
						return BasicAgreementSettings.of(
								basicAgreementSetOpt.get(), agreementTimeOfCls.get().getUpperAgreementSetting());
					}
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
					val basicAgreementSetOpt = require.basicAgreementSetting(
							agreementTimeOfWkp.get().getBasicSettingId());
					if (basicAgreementSetOpt.isPresent()) {
						return BasicAgreementSettings.of(
								basicAgreementSetOpt.get(), agreementTimeOfWkp.get().getUpperAgreementSetting());
					}
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
					val basicAgreementSetOpt = require.basicAgreementSetting(
							agreementTimeOfEmp.get().getBasicSettingId());
					if (basicAgreementSetOpt.isPresent()) {
						return BasicAgreementSettings.of(
								basicAgreementSetOpt.get(), agreementTimeOfEmp.get().getUpperAgreementSetting());
					}
				}
			}
		}
		
		// 会社36協定時間を取得する
		val agreementTimeOfCmpOpt = require.agreementTimeOfCompany(companyId, laborSystemAtr);
		if (agreementTimeOfCmpOpt.isPresent()){
			val basicAgreementSetOpt = require.basicAgreementSetting(
					agreementTimeOfCmpOpt.get().getBasicSettingId());
			if (basicAgreementSetOpt.isPresent()) {
				return BasicAgreementSettings.of(
						basicAgreementSetOpt.get(), agreementTimeOfCmpOpt.get().getUpperAgreementSetting());
			}
		}
		
		// 全ての値を0で返す
		return BasicAgreementSettings.of(getDefault(), null);
	}
	
	public static BasicAgreementSettingsGetter getBasicSet(RequireM2 require, String companyId, List<String> employeeIds, DatePeriod datePeriod) {
		// 「36協定単位設定」を取得する
		Map<String, BasicAgreementSetting> basicAgreeSettings = new HashMap<>();
		List<AffClassificationSidImport> affClassifications = new ArrayList<>();
		List<AgreementTimeOfClassification> agreeTimeClassifi = new ArrayList<>();
		Map<GeneralDate, Map<String, List<String>>> empToWorkplaceId = new HashMap<>();
		Map<String, List<AgreementTimeOfWorkPlace>> agreeTimeWP = new HashMap<>();
		Map<String, List<SyEmploymentImport>> employments = new HashMap<>();
		Map<String, List<AgreementTimeOfEmployment>> agreeTimeEmployment = new HashMap<>();
		List<AgreementTimeOfCompany> agreeTimeCompany = new ArrayList<>();
		
		AgreementUnitSetting agreementUnitSet = require.agreementUnitSetting(companyId).orElseGet(() -> new AgreementUnitSetting(companyId,
				UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE, UseClassificationAtr.NOT_USE));
		
		if (agreementUnitSet.getClassificationUseAtr() == UseClassificationAtr.USE){
			affClassifications = require.affEmployeeClassification(companyId, employeeIds, datePeriod);
			
			agreeTimeClassifi = require.agreementTimeOfClassification(companyId, affClassifications.stream()
																.map(c -> c.getClassificationCode()).distinct().collect(Collectors.toList()));
			
			basicAgreeSettings.putAll(getBasicSetting(require, agreeTimeClassifi.stream().map(c -> c.getBasicSettingId()).distinct().collect(Collectors.toList())));
		}
		
		if(agreementUnitSet.getWorkPlaceUseAtr() == UseClassificationAtr.USE){
			empToWorkplaceId.putAll(require.getCanUseWorkplaceForEmp(companyId, employeeIds, datePeriod));
			
			List<String> workplaceIds = empToWorkplaceId.entrySet().stream().map(c -> 
													c.getValue().values().stream().flatMap(List::stream).distinct().collect(Collectors.toList()))
											.flatMap(List::stream).distinct().collect(Collectors.toList());
			
			// 職場36協定時間を取得する
			agreeTimeWP = require.agreementTimeOfWorkPlace(workplaceIds)
					.stream().collect(Collectors.groupingBy(AgreementTimeOfWorkPlace::getWorkplaceId));
			
			basicAgreeSettings.putAll(getBasicSetting(require, agreeTimeWP.values().stream().flatMap(x -> x.stream())
					.distinct().map(c -> c.getBasicSettingId()).distinct().collect(Collectors.toList())));
		}
		
		if(agreementUnitSet.getEmploymentUseAtr() == UseClassificationAtr.USE){
			employments = require.employment(employeeIds, datePeriod);
			List<String> employmentCodes = employments.entrySet().stream().map(c -> 
																c.getValue().stream().map(h -> h.getEmploymentCode()).collect(Collectors.toList()))
															.flatMap(List::stream).distinct().collect(Collectors.toList());
			
			agreeTimeEmployment = require.agreementTimeOfEmployment(companyId, employmentCodes)
					.stream().collect(Collectors.groupingBy(AgreementTimeOfEmployment::getEmploymentCategoryCode));

			basicAgreeSettings.putAll(getBasicSetting(require, agreeTimeEmployment.values().stream().flatMap(x -> x.stream())
					.map(c -> c.getBasicSettingId()).distinct().collect(Collectors.toList())));
		}
		
		agreeTimeCompany = require.agreementTimeOfCompany(companyId);
		basicAgreeSettings.putAll(getBasicSetting(require, agreeTimeCompany.stream()
																.map(c -> c.getBasicSettingId()).distinct().collect(Collectors.toList())));
		
		return new BasicAgreementSettingsGetter(agreementUnitSet, affClassifications, agreeTimeClassifi, empToWorkplaceId, 
				agreeTimeWP, employments, agreeTimeEmployment, agreeTimeCompany, basicAgreeSettings);
	}
	
	private static Map<String, BasicAgreementSetting> getBasicSetting(RequireM1 require, List<String> setIds){
		return require.basicAgreementSetting(setIds).stream().collect(Collectors.toMap(c -> c.getBasicSettingId(), c -> c));
	}
	
	private static BasicAgreementSetting getDefault(){
		// 全ての値を0で返す
		return new BasicAgreementSetting(
				new String(),
				new AlarmWeek(0),
				new ErrorWeek(0),
				new LimitWeek(0),
				new AlarmTwoWeeks(0),
				new ErrorTwoWeeks(0),
				new LimitTwoWeeks(0),
				new AlarmFourWeeks(0),
				new ErrorFourWeeks(0),
				new LimitFourWeeks(0),
				new AlarmOneMonth(0),
				new ErrorOneMonth(0),
				new LimitOneMonth(0),
				new AlarmTwoMonths(0),
				new ErrorTwoMonths(0),
				new LimitTwoMonths(0),
				new AlarmThreeMonths(0),
				new ErrorThreeMonths(0),
				new LimitThreeMonths(0),
				new AlarmOneYear(0),
				new ErrorOneYear(0),
				new LimitOneYear(0));
	}

	public static interface RequireM3 {
		
		Optional<AgreementUnitSetting> agreementUnitSetting(String companyId);
		
		Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode);
		
		Optional<BasicAgreementSetting> basicAgreementSetting(String basicSettingId);
		
		List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId,
				LaborSystemtAtr laborSystemAtr);
		
		Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId, String employmentCategoryCode,
				LaborSystemtAtr laborSystemAtr);
		
		Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr);
	}
	
	public static interface RequireM2 extends RequireM1 {
		
		Optional<AgreementUnitSetting> agreementUnitSetting(String companyId);
		
		List<AffClassificationSidImport> affEmployeeClassification(String companyId, List<String> employeeId, DatePeriod baseDate);
		
		List<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, List<String> classificationCode);
		
		Map<GeneralDate, Map<String, List<String>>> getCanUseWorkplaceForEmp(String companyId, List<String> employeeId, DatePeriod baseDate);
		
		List<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(List<String> workplaceId);
		
		Map<String, List<SyEmploymentImport>> employment(List<String> employeeId, DatePeriod baseDate);
		
		List<AgreementTimeOfEmployment> agreementTimeOfEmployment(String comId, List<String> employmentCategoryCode);
		
		List<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId);
	}
	
	private static interface RequireM1 {
		
		List<BasicAgreementSetting> basicAgreementSetting(List<String> basicSettingId);
	}
}

package nts.uk.ctx.hr.shared.infra.entity.laborcontracthistory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistoryInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PPEDT_JM_WORK")
public class PpedtJmWork extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String cid;

	@Column(name = "SID")
	public String sid;

	@Id
	@Column(name = "HIST_ID")
	public String hisId;

	@Column(name = "CCD_G")
	public Integer formerCompanyInGroup;

	@Column(name = "J_BASYO")
	public String placeOfWork;

	@Column(name = "J_GYOMUNAIYO")
	public String detailOfWorkToEngaged;

	@Column(name = "SYUGYO_KBN")
	public Integer employmentDivision;

	@Column(name = "J_KOTAI_KBN")
	public String alternateDivision;

	@Column(name = "J_START1")
	public String startTime1;

	@Column(name = "J_FINISH1")
	public String finishTime1;

	@Column(name = "J_TEKIYO1")
	public String applicationDate1;

	@Column(name = "J_START2")
	public String startTime2;

	@Column(name = "J_FINISH2")
	public String finishTime2;

	@Column(name = "J_TEKIYO2")
	public String applicationDate2;

	@Column(name = "J_START3")
	public String startTime3;

	@Column(name = "J_FINISH3")
	public String finishTime3;

	@Column(name = "J_TEKIYO3")
	public String applicationDate3;

	@Column(name = "J_START4")
	public String startTime4;

	@Column(name = "J_FINISH4")
	public String finishTime4;

	@Column(name = "J_TEKIYO4")
	public String applicationDate4;

	@Column(name = "J_START_FINISH_BIKO")
	public String remarksOnStartEndTimes;

	@Column(name = "CONTRACT_TIME")
	public Integer contractTime;

	@Column(name = "J_FLEX_START_S")
	public String flexibleStartStart;

	@Column(name = "J_FLEX_START_E")
	public String flexibleStartEnd;

	@Column(name = "J_FLEX_FINISH_S")
	public String flexibleFinishStart;

	@Column(name = "J_FLEX_FINISH_E")
	public String flexibleFinishEnd;

	@Column(name = "J_CORETIME_S")
	public String realTimeStart;

	@Column(name = "J_CORETIME_E")
	public String realTimeEnd;

	@Column(name = "J_JYOGAI_S")
	public String offSiteStart;

	@Column(name = "J_JYOGAI_E")
	public String offSiteEnd;

	@Column(name = "J_SAIRYO_S")
	public String discretionLaborSystemStart;

	@Column(name = "J_SAIRYO_E")
	public String discretionLaborSystemEnd;

	@Column(name = "REST_TIME")
	public Integer breakTime;

	@Column(name = "J_ZAN_UMU")
	public String overtimeWork;

	@Column(name = "J_HOLIDAY_TEI")
	public String holidaysRegularDays;

	@Column(name = "J_HOLIDAY_TEIBIKO")
	public String holidaysRegularDaysAndOthers;

	@Column(name = "J_HOLIDAY_HI")
	public Integer holidaysIrregularDays;

	@Column(name = "J_HOLIDAY_HIBIKO")
	public String holidaysIrregularDaysAndOthers;

	@Column(name = "J_HOLIDAY_HEN")
	public Integer holidaysTransfLaborSystem;

	@Column(name = "J_HOLIDAY_HENBIKO")
	public String holidaysTransfLaborSystemAndOthers;

	@Column(name = "J_HOLIDAY_BIKO")
	public String holidayRemarks;

	@Column(name = "J_NENKYU")
	public Integer forSixMonthOfContinuousWork;

	@Column(name = "J_NENKYU_6INAI")
	public String paidWithin6Months;

	@Column(name = "J_NENKYU_KEIKATUKI")
	public Integer benefitsAndMoons;

	@Column(name = "J_NENKYU_FUYOBI")
	public Integer benefitsAndDays;

	@Column(name = "J_SONOTA_YUKYU")
	public String otherVacationPaid;

	@Column(name = "J_SONOTA_MUKYU")
	public String otherVacationUnPaid;

	@Column(name = "J_KYUKA_BIKO")
	public String vacationRemarks;

	@Column(name = "KYUKUBUN")
	public Integer wageType;

	@Column(name = "KIHON_KYUYOTUKI")
	public Integer basicWageMonthly;

	@Column(name = "KIHON_KYUYOHI")
	public Integer basicWageDaily;

	@Column(name = "CONTRACT_UNIT_PRICE")
	public Integer contractPriceHourly;

	@Column(name = "J_DEKIDAKA_KIHON")
	public Integer basicUnitPriceOfPieceworkSalary;

	@Column(name = "J_DEKIDAKA_HOSYO")
	public Integer pieceRateSalary;

	@Column(name = "J_KIHON_KYU_SONOTA")
	public Integer basicWageAndOthers;

	@Column(name = "J_KIHON_KYU_BIKO")
	public String basicWageRemarks;

	@Column(name = "J_FAMI_HAIGUSYA")
	public Integer familyAllowanceSpouse;

	@Column(name = "J_FAMI_HOKA")
	public Integer perFamilyAllowanceAndOthers;

	@Column(name = "J_SYOKUNOKYU")
	public Integer occupationalAllowance;

	@Column(name = "J_JYUTAKU")
	public Integer housingAllowance;

	@Column(name = "J_SONOTATEATE")
	public Integer otherBenefits;

	@Column(name = "J_TUKIN")
	public Integer commutingAllowance;

	@Column(name = "J_TEATE_MEI1")
	public String benefitsName1;

	@Column(name = "J_TEATE_KIN1")
	public Integer allowanceAmount1;

	@Column(name = "J_TEATE_CAL1")
	public String allowanceCalculationMethod1;

	@Column(name = "J_TEATE_MEI2")
	public String benefitsName2;

	@Column(name = "J_TEATE_KIN2")
	public Integer allowanceAmount2;

	@Column(name = "J_TEATE_CAL2")
	public String allowanceCalculationMethod2;

	@Column(name = "J_TEATE_MEI3")
	public String benefitsName3;

	@Column(name = "J_TEATE_KIN3")
	public Integer allowanceAmount3;

	@Column(name = "J_TEATE_CAL3")
	public String allowanceCalculationMethod3;

	@Column(name = "J_TEATE_MEI4")
	public String benefitsName4;

	@Column(name = "J_TEATE_KIN4")
	public Integer allowanceAmount4;

	@Column(name = "J_TEATE_CAL4")
	public String allowanceCalculationMethod4;

	@Column(name = "J_TEATE_MEI5")
	public String benefitsName5;

	@Column(name = "J_TEATE_KIN5")
	public Integer allowanceAmount5;

	@Column(name = "J_TEATE_CAL5")
	public String allowanceCalculationMethod5;

	@Column(name = "J_TEATE_BIKO")
	public String rewardsName;

	@Column(name = "J_TIME_RITU")
	public Integer additionRateWithLegalTime;

	@Column(name = "J_TIMEGAI_RITU")
	public Integer overtimeExtraCharge;

	@Column(name = "J_HOTEHOL_RITU")
	public Integer legalHolidaySurchargeRate;

	@Column(name = "J_HOTEGAIHOL_RITU")
	public Integer statutoryOffDayCutRate;

	@Column(name = "J_NIGHTRITU")
	public Integer lateNightMowingRate;

	@Column(name = "J_SHIMEDAY1")
	public Integer wageDeadline;

	@Column(name = "J_SHIMEDAY1_RIYU")
	public String wageDeadlineReason;

	@Column(name = "J_SIHARAIDAY1")
	public Integer wagePaymentDate1;

	@Column(name = "J_SIHARAIDAY1_RIYU")
	public String wagePaymentDate1Reason;

	@Column(name = "J_SIHARAIDAY2")
	public Integer wagePaymentDate2;

	@Column(name = "J_SIHARAIDAY2_RIYU")
	public String wagePaymentDate2Reason;

	@Column(name = "J_ROSHI_KOJYO")
	public String laborManageAgreeDeduc;

	@Column(name = "J_ROSHI_KOJYO_BIKO")
	public String laborManageAgreeDeducReason;

	@Column(name = "J_SYOKYU")
	public String reasonForRaise;

	@Column(name = "J_SYOYO")
	public String bonus;

	@Column(name = "J_SYOYO_BIKO")
	public String bonusReason;

	@Column(name = "J_TAISYOKUKIN")
	public String severancePay;

	@Column(name = "J_TAISYOKUKIN_BIKO")
	public String severancePayReason;

	@Column(name = "J_TEINEN")
	public String retirement;

	@Column(name = "J_TEINEN_AGE")
	public Integer retirementAge;

	@Column(name = "J_JIKOTAISYOKUBI")
	public Integer selfRetiProceDate;

	@Column(name = "J_KAIKOJIKOU")
	public String dismissalProcedures;

	@Column(name = "J_SYAHOKANYUU")
	public String socialInsuEnrollStatus;

	@Column(name = "J_KOYOHOKEN_UMU")
	public String appliOfEmploymentInsu;

	@Column(name = "J_SONOTA")
	public String other;

	@Column(name = "HAKO_KBN")
	public Integer issueClassification;

	@Column(name = "HAKO_DATE")
	public GeneralDate dateOfIssue;

	@Column(name = "UNIT_PRICE_1")
	public Integer unitPrice1;

	@Column(name = "UNIT_PRICE_2")
	public Integer unitPrice2;

	@Column(name = "UNIT_PRICE_3")
	public Integer unitPrice3;

	@Column(name = "J_SHIMEDAY2")
	public Integer wageDeadline2;

	@Column(name = "J_SHIMEDAY2_RIYU")
	public String wageDeadline2Reasons;

	@Column(name = "BASE_PRICE")
	public Integer basePrice;

	@Column(name = "UNIT_PRICE_01M")
	public Integer unitPrice01M;

	@Column(name = "UNIT_PRICE_02M")
	public Integer unitPrice02M;

	@Column(name = "UNIT_PRICE_03M")
	public Integer unitPrice03M;

	@Column(name = "UNIT_PRICE_04M")
	public Integer unitPrice04M;

	@Column(name = "UNIT_PRICE_05M")
	public Integer unitPrice05M;

	@Column(name = "UNIT_PRICE_06M")
	public Integer unitPrice06M;

	@Column(name = "UNIT_PRICE_07M")
	public Integer unitPrice07M;

	@Column(name = "UNIT_PRICE_08M")
	public Integer unitPrice08M;

	@Column(name = "UNIT_PRICE_09M")
	public Integer unitPrice09M;

	@Column(name = "UNIT_PRICE_10M")
	public Integer unitPrice10M;

	@Column(name = "UNIT_PRICE_11M")
	public Integer unitPrice11M;

	@Column(name = "UNIT_PRICE_12M")
	public Integer unitPrice12M;

	@Column(name = "MANRYOUDATE")
	public GeneralDate contractExpirationDate;

	@Column(name = "J_TIMENEN_KBN")
	public String annualHolidays;

	@Column(name = "J_DAITAI_KBN")
	public String insteadOfLeisure;

	@Column(name = "J_60OVER_RITU")
	public Integer times60verMowingRate;

	@Column(name = "J_KEIZOKUKOYO_KBN")
	public String continueEmployment;

	@Column(name = "J_KEIZOKUKOYO_AGE")
	public Integer continueEmploymentAge;

	@Override
	public String getKey() {
		return hisId;
	}

	public LaborContractHistoryInfo toDomain() {
		return LaborContractHistoryInfo.createFromJavaType(cid, sid, hisId, formerCompanyInGroup, placeOfWork,
				detailOfWorkToEngaged, employmentDivision, alternateDivision, startTime1, finishTime1, applicationDate1,
				startTime2, finishTime2, applicationDate2, startTime3, finishTime3, applicationDate3, startTime4,
				finishTime4, applicationDate4, remarksOnStartEndTimes, contractTime, flexibleStartStart,
				flexibleStartEnd, flexibleFinishStart, flexibleFinishEnd, realTimeStart, realTimeEnd, offSiteStart,
				offSiteEnd, discretionLaborSystemStart, discretionLaborSystemEnd, breakTime, overtimeWork,
				holidaysRegularDays, holidaysRegularDaysAndOthers, holidaysIrregularDays,
				holidaysIrregularDaysAndOthers, holidaysTransfLaborSystem, holidaysTransfLaborSystemAndOthers,
				holidayRemarks, forSixMonthOfContinuousWork, paidWithin6Months, benefitsAndMoons, benefitsAndDays,
				otherVacationPaid, otherVacationUnPaid, vacationRemarks, wageType, basicWageMonthly, basicWageDaily,
				contractPriceHourly, basicUnitPriceOfPieceworkSalary, pieceRateSalary, basicWageAndOthers,
				basicWageRemarks, familyAllowanceSpouse, perFamilyAllowanceAndOthers, occupationalAllowance,
				housingAllowance, otherBenefits, commutingAllowance, benefitsName1, allowanceAmount1,
				allowanceCalculationMethod1, benefitsName2, allowanceAmount2, allowanceCalculationMethod2,
				benefitsName3, allowanceAmount3, allowanceCalculationMethod3, benefitsName4, allowanceAmount4,
				allowanceCalculationMethod4, benefitsName5, allowanceAmount5, allowanceCalculationMethod5, rewardsName,
				additionRateWithLegalTime, overtimeExtraCharge, legalHolidaySurchargeRate, statutoryOffDayCutRate,
				lateNightMowingRate, wageDeadline, wageDeadlineReason, wagePaymentDate1, wagePaymentDate1Reason,
				wagePaymentDate2, wagePaymentDate2Reason, laborManageAgreeDeduc, laborManageAgreeDeducReason,
				reasonForRaise, bonus, bonusReason, severancePay, severancePayReason, retirement, retirementAge,
				selfRetiProceDate, dismissalProcedures, socialInsuEnrollStatus, appliOfEmploymentInsu,
				holidaysIrregularDaysAndOthers, issueClassification, dateOfIssue, unitPrice1, unitPrice2, unitPrice3,
				wageDeadline2, wageDeadline2Reasons, basePrice, unitPrice01M, unitPrice02M, unitPrice03M, unitPrice04M,
				unitPrice05M, unitPrice06M, unitPrice07M, unitPrice08M, unitPrice09M, unitPrice10M, unitPrice11M,
				unitPrice12M, contractExpirationDate, annualHolidays, insteadOfLeisure, times60verMowingRate,
				continueEmployment, continueEmploymentAge);
	}

	public PpedtJmWork toEntity (LaborContractHistoryInfo domain) {
		
		PpedtJmWork entity = PpedtJmWork.builder()
				.cid(domain.cid)
				.sid(domain.sid)    
			    .hisId(domain.hisId)  	 
			    .formerCompanyInGroup(domain.formerCompanyInGroup.value)  	 
			    .placeOfWork(domain.placeOfWork)  	 	
			    .detailOfWorkToEngaged(domain.detailOfWorkToEngaged)  	 	
			    .employmentDivision(domain.employmentDivision.value)  	 	
			    .alternateDivision(domain.alternateDivision)  	 	
			    .startTime1(domain.startTime1) 
			    .finishTime1(domain.finishTime1)  	 	
			    .applicationDate1(domain.applicationDate1)  	 	
			    .startTime2(domain.startTime2) 
			    .finishTime2(domain.finishTime2)  	 	
			    .applicationDate2(domain.applicationDate2)  	 	
			    .startTime3(domain.startTime3)  	 	
			    .finishTime3(domain.finishTime3)  	 	
			    .applicationDate3(domain.applicationDate3)  	 	
			    .startTime4(domain.startTime4)  	 	
			    .finishTime4(domain.finishTime4)  	 	
			    .applicationDate4(domain.applicationDate4)  	 	
			    .remarksOnStartEndTimes(domain.remarksOnStartEndTimes)  	 	
			    .contractTime(domain.contractTime)  	 	
			    .flexibleStartStart(domain.flexibleStartStart)  	 	
			    .flexibleStartEnd(domain.flexibleStartEnd)  	 	
			    .flexibleFinishStart(domain.flexibleFinishStart)  	 	
			    .flexibleFinishEnd(domain.flexibleFinishEnd)  	 	
			    .realTimeStart(domain.realTimeStart)  	 	
			    .realTimeEnd(domain.realTimeEnd)  	 	
			    .offSiteStart(domain.offSiteStart)  	 	
			    .offSiteEnd(domain.offSiteEnd)  	 	
			    .discretionLaborSystemStart(domain.discretionLaborSystemStart)  	 	
			    .discretionLaborSystemEnd(domain.discretionLaborSystemEnd)  	 	
			    .breakTime(domain.breakTime)  	 	
			    .overtimeWork(domain.overtimeWork)  	 	
			    .holidaysRegularDays(domain.holidaysRegularDays)  	 	
			    .holidaysRegularDaysAndOthers(domain.holidaysRegularDaysAndOthers)  	 	
			    .holidaysIrregularDays(domain.holidaysIrregularDays)  	 	
			    .holidaysIrregularDaysAndOthers(domain.holidaysIrregularDaysAndOthers)  	
			    .holidaysTransfLaborSystem(domain.holidaysTransfLaborSystem)  	 	
			    .holidaysTransfLaborSystemAndOthers(domain.holidaysTransfLaborSystemAndOthers)  	 	
			    .holidayRemarks(domain.holidayRemarks)  	 	
			    .forSixMonthOfContinuousWork(domain.forSixMonthOfContinuousWork)  	 	
			    .paidWithin6Months(domain.paidWithin6Months)  	 	
			    .benefitsAndMoons(domain.benefitsAndMoons)  	 	
			    .benefitsAndDays(domain.benefitsAndDays)  	 	
			    .otherVacationPaid(domain.otherVacationPaid)  	 	
			    .otherVacationUnPaid(domain.otherVacationUnPaid)  	 	
			    .vacationRemarks(domain.vacationRemarks)  	 	
			    .wageType(domain.wageType.value)  	 	
			    .basicWageMonthly(domain.basicWageMonthly)  	 	
			    .basicWageDaily(domain.basicWageDaily)  	 	
			    .contractPriceHourly(domain.contractPriceHourly)  	 	
			    .basicUnitPriceOfPieceworkSalary(domain.basicUnitPriceOfPieceworkSalary)  	 	
			    .pieceRateSalary(domain.pieceRateSalary)  	 	
			    .basicWageAndOthers(domain.basicWageAndOthers)  	 	
			    .basicWageRemarks(domain.basicWageRemarks)  	 	
			    .familyAllowanceSpouse(domain.familyAllowanceSpouse)  	 	
			    .perFamilyAllowanceAndOthers(domain.perFamilyAllowanceAndOthers)  	 	
			    .occupationalAllowance(domain.occupationalAllowance) 	 	
			    .housingAllowance(domain.housingAllowance) 	 	
			    .otherBenefits(domain.otherBenefits)  	 	
			    .commutingAllowance(domain.commutingAllowance)  	 	
			    .benefitsName1(domain.benefitsName1)  	 	
			    .allowanceAmount1(domain.allowanceAmount1)  	 	
			    .allowanceCalculationMethod1(domain.allowanceCalculationMethod1)  	 	
			    .benefitsName2(domain.benefitsName2)  	 	
			    .allowanceAmount2(domain.allowanceAmount2)    	
			    .allowanceCalculationMethod2(domain.allowanceCalculationMethod2)  	 	
			    .benefitsName3(domain.benefitsName3)  	 	
			    .allowanceAmount3(domain.allowanceAmount3) 	 	
			    .allowanceCalculationMethod3(domain.allowanceCalculationMethod3)  	 	
			    .benefitsName4(domain.benefitsName4) 	 
			    .allowanceAmount4(domain.allowanceAmount4) 	 	
			    .allowanceCalculationMethod4(domain.allowanceCalculationMethod4)  	 	
			    .benefitsName5(domain.benefitsName5) 	  	
			    .allowanceAmount5(domain.allowanceAmount5)  	 	
			    .allowanceCalculationMethod5(domain.allowanceCalculationMethod5)  	 	
			    .rewardsName(domain.rewardsName)  	 	
			    .additionRateWithLegalTime(domain.additionRateWithLegalTime)  	 	
			    .overtimeExtraCharge(domain.overtimeExtraCharge)  	 	
			    .legalHolidaySurchargeRate(domain.legalHolidaySurchargeRate)  	 	
			    .statutoryOffDayCutRate(domain.statutoryOffDayCutRate)  	 	
			    .lateNightMowingRate(domain.lateNightMowingRate)  	 	
			    .wageDeadline(domain.wageDeadline) 	  	
			    .wageDeadlineReason(domain.wageDeadlineReason)  	 	
			    .wagePaymentDate1(domain.wagePaymentDate1)  	 	
			    .wagePaymentDate1Reason(domain.wagePaymentDate1Reason)  	 	
			    .wagePaymentDate2(domain.wagePaymentDate2) 	 	 
			    .wagePaymentDate2Reason(domain.wagePaymentDate2Reason)  	 	
			    .laborManageAgreeDeduc(domain.laborManageAgreeDeduc) 	  	
			    .laborManageAgreeDeducReason(domain.laborManageAgreeDeducReason)  	 	
			    .reasonForRaise(domain.reasonForRaise)  	 	
			    .bonus(domain.bonus) 	  	
			    .bonusReason(domain.bonusReason)  	 	
			    .severancePay(domain.severancePay) 	  	
			    .severancePayReason(domain.severancePayReason)  	 	
			    .retirement(domain.retirement)  	 	
			    .retirementAge(domain.retirementAge)  	 	
			    .selfRetiProceDate(domain.selfRetiProceDate)  	 	
			    .dismissalProcedures(domain.dismissalProcedures)  	 	
			    .socialInsuEnrollStatus(domain.socialInsuEnrollStatus)  	 	
			    .appliOfEmploymentInsu(domain.appliOfEmploymentInsu)  	 	
			    .other(domain.other)  	 	
			    .issueClassification(domain.issueClassification)  	 	
			    .dateOfIssue(domain.dateOfIssue)  	 	
			    .unitPrice1(domain.unitPrice1)  	 	
			    .unitPrice2(domain.unitPrice2)  	 	
			    .unitPrice3(domain.unitPrice3)  	 	
			    .wageDeadline2(domain.wageDeadline2)  	 	
			    .wageDeadline2Reasons(domain.wageDeadline2Reasons)  	 	
			    .basePrice(domain.basePrice)  	 	
			    .unitPrice01M(domain.unitPrice01M)  	 	
			    .unitPrice02M(domain.unitPrice02M)  	 	
			    .unitPrice03M(domain.unitPrice03M)  	 	
			    .unitPrice04M(domain.unitPrice04M) 	  	
			    .unitPrice05M(domain.unitPrice05M) 	  	
			    .unitPrice06M(domain.unitPrice06M) 
			    .unitPrice07M(domain.unitPrice07M) 	  	
			    .unitPrice08M(domain.unitPrice08M) 	  	
			    .unitPrice09M(domain.unitPrice09M) 	  	
			    .unitPrice10M(domain.unitPrice10M)  	 	
			    .unitPrice11M(domain.unitPrice11M)  	 	
			    .unitPrice12M(domain.unitPrice12M)  	 
			    .contractExpirationDate(domain.contractExpirationDate)  	 	
			    .annualHolidays(domain.annualHolidays)  	 	
			    .insteadOfLeisure(domain.insteadOfLeisure)  	 	 
			    .times60verMowingRate(domain.times60verMowingRate)  	 	
			    .continueEmployment(domain.continueEmployment)  	 	
			    .continueEmploymentAge(domain.continueEmploymentAge) 	 	
				.build();
		
		return entity;

	}

}

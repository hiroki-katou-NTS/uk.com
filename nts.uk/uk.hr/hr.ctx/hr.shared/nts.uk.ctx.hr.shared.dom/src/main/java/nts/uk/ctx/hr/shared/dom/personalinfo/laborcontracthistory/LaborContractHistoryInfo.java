/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

/**
 * @author laitv 労働契約履歴情報. 
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.労働契約履歴情報. 
 * 129 field
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaborContractHistoryInfo extends DomainObject {

	public String cid; // 会社ID
	public String sid; // 社員ID
	public String hisId;	//履歴ID
	public YesNoClassification formerCompanyInGroup;	//	グループ内出向元会社
	public String placeOfWork;	//	就業の場所
	public String detailOfWorkToEngaged;	//	従事すべき業務の内容
	public EmploymentDivision employmentDivision;	//	就業区分
	public String alternateDivision;	//	交替制区分
	public String startTime1;	//	始業時刻１
	public String finishTime1;	//	終業時刻１
	public String applicationDate1;	//	適用日１
	public String startTime2;	//	始業時刻２
	public String finishTime2;	//	終業時刻２
	public String applicationDate2;	//	適用日２
	public String startTime3;	//	始業時刻３
	public String finishTime3;	//	終業時刻３
	public String applicationDate3;	//	適用日３
	public String startTime4;	//	始業時刻４
	public String finishTime4;	//	終業時刻４
	public String applicationDate4;	//	適用日４
	public String remarksOnStartEndTimes;	//	始業終業時刻の備考
	public Integer contractTime;	//	契約時間(1日)
	public String flexibleStartStart;	//	ﾌﾚｷｼﾌﾞﾙ始業帯／開始
	public String flexibleStartEnd;	//	ﾌﾚｷｼﾌﾞﾙ始業帯／終了
	public String flexibleFinishStart;	//	ﾌﾚｷｼﾌﾞﾙ終業帯／開始
	public String flexibleFinishEnd;	//	ﾌﾚｷｼﾌﾞﾙ終業帯／終了
	public String realTimeStart;	//	ｺｱﾀｲﾑ／開始
	public String realTimeEnd;	//	ｺｱﾀｲﾑ／終了
	public String offSiteStart;	//	事業場外みなし／始業
	public String offSiteEnd;	//	事業場外みなし／終業
	public String discretionLaborSystemStart;	//	裁量労働制／始業
	public String discretionLaborSystemEnd;	//	裁量労働制／終業
	public Integer breakTime;	//	休憩時間
	public String overtimeWork;	//	所定時間外労働の有無
	public String holidaysRegularDays;	//	休日（定例日）
	public String holidaysRegularDaysAndOthers;	//	休日（定例日）その他
	public Integer holidaysIrregularDays;	//	休日（非定例日）
	public String holidaysIrregularDaysAndOthers;	//	休日（非定例日）その他
	public Integer holidaysTransfLaborSystem;	//	休日（変形労働制）
	public String holidaysTransfLaborSystemAndOthers;	//	休日（変形労働制）その他
	public String holidayRemarks;	//	休日備考
	public Integer forSixMonthOfContinuousWork;	//	六ヶ月継続勤務の場合
	public String paidWithin6Months;	//	六ヶ月以内の有給
	public Integer benefitsAndMoons;	//	有給付与経過月
	public Integer benefitsAndDays;	//	有給付与日数
	public String otherVacationPaid;	//	その他の休暇（有給）
	public String otherVacationUnPaid;	//	その他の休暇（無給）
	public String vacationRemarks;	//	休暇備考
	public WageType wageType;	//	給与区分
	public Integer basicWageMonthly;	//	基本賃金(月給）
	public Integer basicWageDaily;	//	基本賃金(日給）
	public Integer contractPriceHourly;	//	契約単価(時間単位)
	public Integer basicUnitPriceOfPieceworkSalary;	//	出来高給の基本単価
	public Integer pieceRateSalary;	//	出来高給の保障給
	public Integer basicWageAndOthers;	//	基本賃金その他
	public String basicWageRemarks;	//	基本賃金備考
	public Integer familyAllowanceSpouse;	//	家族手当配偶者
	public Integer perFamilyAllowanceAndOthers;	//	家族手当その他1人につき
	public Integer occupationalAllowance;	//	職能手当
	public Integer housingAllowance;	//	住宅手当
	public Integer otherBenefits;	//	その他手当
	public Integer commutingAllowance;	//	通勤手当
	public String benefitsName1;	//	諸手当名称１
	public Integer allowanceAmount1;	//	諸手当金額１
	public String allowanceCalculationMethod1;	//	諸手当計算方法１
	public String benefitsName2;	//	諸手当名称２
	public Integer allowanceAmount2; //	諸手当金額２
	public String allowanceCalculationMethod2;	//	諸手当計算方法２
	public String benefitsName3;	//	諸手当名称３
	public Integer allowanceAmount3;	//	諸手当金額３
	public String allowanceCalculationMethod3;	//	諸手当計算方法３
	public String benefitsName4;	//	諸手当名称４
	public Integer allowanceAmount4;	//	諸手当金額４
	public String allowanceCalculationMethod4;	//	諸手当計算方法４
	public String benefitsName5;	//	諸手当名称５
	public Integer allowanceAmount5;	//	諸手当金額５
	public String allowanceCalculationMethod5;	//	諸手当計算方法５
	public String rewardsName;	//	諸手当名称備考
	public Integer additionRateWithLegalTime;	//	法定時間内割増率
	public Integer overtimeExtraCharge;	//	所定時間外割増率
	public Integer legalHolidaySurchargeRate;	//	法定休日割増率
	public Integer statutoryOffDayCutRate;	//	法定外休日割増率
	public Integer lateNightMowingRate;	//	深夜割増率
	public Integer wageDeadline;	//	賃金締切日
	public String wageDeadlineReason;	//	賃金締切日理由
	public Integer wagePaymentDate1;	//	賃金支払日1
	public String wagePaymentDate1Reason;	//	賃金支払日1理由
	public Integer wagePaymentDate2;	//	賃金支払日2
	public String wagePaymentDate2Reason;	//	賃金支払日2理由
	public String laborManageAgreeDeduc;	//	労使協定による控除
	public String laborManageAgreeDeducReason;	//	労使協定による控除理由
	public String reasonForRaise;	//	昇給理由
	public String bonus;	//	賞与
	public String bonusReason;	//	賞与理由
	public String severancePay;	//	退職金
	public String severancePayReason;	//	退職金理由
	public String retirement;	//	定年制
	public Integer retirementAge;	//	定年制年齢
	public Integer selfRetiProceDate;	//	自己退職の手続日
	public String dismissalProcedures;	//	解雇手続事項
	public String socialInsuEnrollStatus;	//	社会保険加入状況
	public String appliOfEmploymentInsu;	//	雇用保険の適用有無
	public String other;	//	その他
	public Integer issueClassification;	//	発行区分
	public GeneralDate dateOfIssue;	//	発行日
	public Integer unitPrice1;	//	単価（汎用目的の単価）
	public Integer unitPrice2;	//	単価（汎用目的の単価）
	public Integer unitPrice3;	//	単価（汎用目的の単価）
	public Integer wageDeadline2;	//	賃金締切日２
	public String wageDeadline2Reasons;	//	賃金締切日２理由
	public Integer basePrice;	//	１時間あたりの単価（基準値）
	public Integer unitPrice01M;	//	１月度の１時間あたりの単価
	public Integer unitPrice02M;	//	２月度の１時間あたりの単価
	public Integer unitPrice03M;	//	３月度の１時間あたりの単価
	public Integer unitPrice04M;	//	４月度の１時間あたりの単価
	public Integer unitPrice05M;	//	５月度の１時間あたりの単価
	public Integer unitPrice06M;	//６月度の１時間あたりの単価
	public Integer unitPrice07M;	//	７月度の１時間あたりの単価
	public Integer unitPrice08M;	//	８月度の１時間あたりの単価
	public Integer unitPrice09M;	//	９月度の１時間あたりの単価
	public Integer unitPrice10M;	//	10月度の１時間あたりの単価
	public Integer unitPrice11M;	//	11月度の１時間あたりの単価
	public Integer unitPrice12M;	//12月度の１時間あたりの単価
	public GeneralDate contractExpirationDate;	//	契約満了日
	public String annualHolidays;	//	時間単位年休（有・無）
	public String insteadOfLeisure;	//	代替休暇（有・無） 
	public Integer times60verMowingRate;	//	60時間超過割増率
	public String continueEmployment;	//	継続雇用（有・無） 
	public Integer continueEmploymentAge;	//	継続雇用年齢

	
	public static LaborContractHistoryInfo createFromJavaType( 
			  String cid,   
			  String sid,   
			  String hisId, 	 
			  Integer formerCompanyInGroup, 	 
			  String placeOfWork, 	 	
			  String detailOfWorkToEngaged, 	 	
			  Integer employmentDivision, 	 	
			  String alternateDivision, 	 	
			  String startTime1,
			  String finishTime1, 	 	
			  String applicationDate1, 	 	
			  String startTime2,
			  String finishTime2, 	 	
			  String applicationDate2, 	 	
			  String startTime3, 	 	
			  String finishTime3 ,	 	
			  String applicationDate3 ,	 	
			  String startTime4 ,	 	
			  String finishTime4 ,	 	
			  String applicationDate4 ,	 	
			  String remarksOnStartEndTimes, 	 	
			  Integer contractTime ,	 	
			  String flexibleStartStart, 	 	
			  String flexibleStartEnd ,	 	
			  String flexibleFinishStart, 	 	
			  String flexibleFinishEnd ,	 	
			  String realTimeStart, 	 	
			  String realTimeEnd, 	 	
			  String offSiteStart, 	 	
			  String offSiteEnd ,	 	
			  String discretionLaborSystemStart, 	 	
			  String discretionLaborSystemEnd, 	 	
			  Integer breakTime, 	 	
			  String overtimeWork, 	 	
			  String holidaysRegularDays, 	 	
			  String holidaysRegularDaysAndOthers, 	 	
			  Integer holidaysIrregularDays, 	 	
			  String holidaysIrregularDaysAndOthers, 	
			  Integer holidaysTransfLaborSystem ,	 	
			  String holidaysTransfLaborSystemAndOthers, 	 	
			  String holidayRemarks ,	 	
			  Integer forSixMonthOfContinuousWork ,	 	
			  String paidWithin6Months ,	 	
			  Integer benefitsAndMoons ,	 	
			  Integer benefitsAndDays, 	 	
			  String otherVacationPaid, 	 	
			  String otherVacationUnPaid ,	 	
			  String vacationRemarks, 	 	
			  Integer wageType, 	 	
			  Integer basicWageMonthly, 	 	
			  Integer basicWageDaily, 	 	
			  Integer contractPriceHourly, 	 	
			  Integer basicUnitPriceOfPieceworkSalary, 	 	
			  Integer pieceRateSalary, 	 	
			  Integer basicWageAndOthers, 	 	
			  String basicWageRemarks ,	 	
			  Integer familyAllowanceSpouse, 	 	
			  Integer perFamilyAllowanceAndOthers ,	 	
			  Integer occupationalAllowance,	 	
			  Integer housingAllowance, 	 	
			  Integer otherBenefits ,	 	
			  Integer commutingAllowance, 	 	
			  String benefitsName1 ,	 	
			  Integer allowanceAmount1 ,	 	
			  String allowanceCalculationMethod1, 	 	
			  String benefitsName2, 	 	
			  Integer allowanceAmount2,   	
			  String allowanceCalculationMethod2, 	 	
			  String benefitsName3, 	 	
			  Integer allowanceAmount3,	 	
			  String allowanceCalculationMethod3 ,	 	
			  String benefitsName4 	,
			  Integer allowanceAmount4 ,	 	
			  String allowanceCalculationMethod4, 	 	
			  String benefitsName5 	, 	
			  Integer allowanceAmount5, 	 	
			  String allowanceCalculationMethod5 ,	 	
			  String rewardsName, 	 	
			  Integer additionRateWithLegalTime, 	 	
			  Integer overtimeExtraCharge, 	 	
			  Integer legalHolidaySurchargeRate, 	 	
			  Integer statutoryOffDayCutRate, 	 	
			  Integer lateNightMowingRate ,	 	
			  Integer wageDeadline 	, 	
			  String wageDeadlineReason, 	 	
			  Integer wagePaymentDate1 ,	 	
			  String wagePaymentDate1Reason ,	 	
			  Integer wagePaymentDate2 	 	,
			  String wagePaymentDate2Reason ,	 	
			  String laborManageAgreeDeduc 	, 	
			  String laborManageAgreeDeducReason ,	 	
			  String reasonForRaise ,	 	
			  String bonus 	 ,	
			  String bonusReason ,	 	
			  String severancePay 	, 	
			  String severancePayReason ,	 	
			  String retirement ,	 	
			  Integer retirementAge ,	 	
			  Integer selfRetiProceDate ,	 	
			  String dismissalProcedures ,	 	
			  String socialInsuEnrollStatus ,	 	
			  String appliOfEmploymentInsu, 	 	
			  String other ,	 	
			  Integer issueClassification, 	 	
			  GeneralDate dateOfIssue ,	 	
			  Integer unitPrice1 ,	 	
			  Integer unitPrice2 ,	 	
			  Integer unitPrice3 ,	 	
			  Integer wageDeadline2 ,	 	
			  String wageDeadline2Reasons, 	 	
			  Integer basePrice ,	 	
			  Integer unitPrice01M ,	 	
			  Integer unitPrice02M ,	 	
			  Integer unitPrice03M ,	 	
			  Integer unitPrice04M 	, 	
			  Integer unitPrice05M 	, 	
			  Integer unitPrice06M,
			  Integer unitPrice07M 	, 	
			  Integer unitPrice08M 	, 	
			  Integer unitPrice09M 	, 	
			  Integer unitPrice10M ,	 	
			  Integer unitPrice11M ,	 	
			  Integer unitPrice12M ,	 
			  GeneralDate contractExpirationDate, 	 	
			  String annualHolidays ,	 	
			  String insteadOfLeisure, 	 	 
			  Integer times60verMowingRate, 	 	
			  String continueEmployment ,	 	
			  Integer continueEmploymentAge ) {
		LaborContractHistoryInfo domain = LaborContractHistoryInfo.builder()
				.cid(cid)
				.sid(sid)    
			    .hisId(hisId)  	 
			    .formerCompanyInGroup(EnumAdaptor.valueOf(formerCompanyInGroup, YesNoClassification.class))  	 
			    .placeOfWork(placeOfWork)  	 	
			    .detailOfWorkToEngaged(detailOfWorkToEngaged)  	 	
			    .employmentDivision(EnumAdaptor.valueOf(employmentDivision, EmploymentDivision.class))  	 	
			    .alternateDivision(alternateDivision)  	 	
			    .startTime1(startTime1) 
			    .finishTime1(finishTime1)  	 	
			    .applicationDate1(applicationDate1)  	 	
			    .startTime2(startTime2) 
			    .finishTime2(finishTime2)  	 	
			    .applicationDate2(applicationDate2)  	 	
			    .startTime3(startTime3)  	 	
			    .finishTime3(finishTime3)  	 	
			    .applicationDate3(applicationDate3)  	 	
			    .startTime4(startTime4)  	 	
			    .finishTime4(finishTime4)  	 	
			    .applicationDate4(applicationDate4)  	 	
			    .remarksOnStartEndTimes(remarksOnStartEndTimes)  	 	
			    .contractTime(contractTime)  	 	
			    .flexibleStartStart(flexibleStartStart)  	 	
			    .flexibleStartEnd(flexibleStartEnd)  	 	
			    .flexibleFinishStart(flexibleFinishStart)  	 	
			    .flexibleFinishEnd(flexibleFinishEnd)  	 	
			    .realTimeStart(realTimeStart)  	 	
			    .realTimeEnd(realTimeEnd)  	 	
			    .offSiteStart(offSiteStart)  	 	
			    .offSiteEnd(offSiteEnd)  	 	
			    .discretionLaborSystemStart(discretionLaborSystemStart)  	 	
			    .discretionLaborSystemEnd(discretionLaborSystemEnd)  	 	
			    .breakTime(breakTime)  	 	
			    .overtimeWork(overtimeWork)  	 	
			    .holidaysRegularDays(holidaysRegularDays)  	 	
			    .holidaysRegularDaysAndOthers(holidaysRegularDaysAndOthers)  	 	
			    .holidaysIrregularDays(holidaysIrregularDays)  	 	
			    .holidaysIrregularDaysAndOthers(holidaysIrregularDaysAndOthers)  	
			    .holidaysTransfLaborSystem(holidaysTransfLaborSystem)  	 	
			    .holidaysTransfLaborSystemAndOthers(holidaysTransfLaborSystemAndOthers)  	 	
			    .holidayRemarks(holidayRemarks)  	 	
			    .forSixMonthOfContinuousWork(forSixMonthOfContinuousWork)  	 	
			    .paidWithin6Months(paidWithin6Months)  	 	
			    .benefitsAndMoons(benefitsAndMoons)  	 	
			    .benefitsAndDays(benefitsAndDays)  	 	
			    .otherVacationPaid(otherVacationPaid)  	 	
			    .otherVacationUnPaid(otherVacationUnPaid)  	 	
			    .vacationRemarks(vacationRemarks)  	 	
			    .wageType(EnumAdaptor.valueOf(wageType, WageType.class))  	 	
			    .basicWageMonthly(basicWageMonthly)  	 	
			    .basicWageDaily(basicWageDaily)  	 	
			    .contractPriceHourly(contractPriceHourly)  	 	
			    .basicUnitPriceOfPieceworkSalary(basicUnitPriceOfPieceworkSalary)  	 	
			    .pieceRateSalary(pieceRateSalary)  	 	
			    .basicWageAndOthers(basicWageAndOthers)  	 	
			    .basicWageRemarks(basicWageRemarks)  	 	
			    .familyAllowanceSpouse(familyAllowanceSpouse)  	 	
			    .perFamilyAllowanceAndOthers(perFamilyAllowanceAndOthers)  	 	
			    .occupationalAllowance(occupationalAllowance) 	 	
			    .housingAllowance(housingAllowance) 	 	
			    .otherBenefits(otherBenefits)  	 	
			    .commutingAllowance(commutingAllowance)  	 	
			    .benefitsName1(benefitsName1)  	 	
			    .allowanceAmount1(allowanceAmount1)  	 	
			    .allowanceCalculationMethod1(allowanceCalculationMethod1)  	 	
			    .benefitsName2(benefitsName2)  	 	
			    .allowanceAmount2(allowanceAmount2)    	
			    .allowanceCalculationMethod2(allowanceCalculationMethod2)  	 	
			    .benefitsName3(benefitsName3)  	 	
			    .allowanceAmount3(allowanceAmount3) 	 	
			    .allowanceCalculationMethod3(allowanceCalculationMethod3)  	 	
			    .benefitsName4(benefitsName4) 	 
			    .allowanceAmount4(allowanceAmount4) 	 	
			    .allowanceCalculationMethod4(allowanceCalculationMethod4)  	 	
			    .benefitsName5(benefitsName5) 	  	
			    .allowanceAmount5(allowanceAmount5)  	 	
			    .allowanceCalculationMethod5(allowanceCalculationMethod5)  	 	
			    .rewardsName(rewardsName)  	 	
			    .additionRateWithLegalTime(additionRateWithLegalTime)  	 	
			    .overtimeExtraCharge(overtimeExtraCharge)  	 	
			    .legalHolidaySurchargeRate(legalHolidaySurchargeRate)  	 	
			    .statutoryOffDayCutRate(statutoryOffDayCutRate)  	 	
			    .lateNightMowingRate(lateNightMowingRate)  	 	
			    .wageDeadline(wageDeadline) 	  	
			    .wageDeadlineReason(wageDeadlineReason)  	 	
			    .wagePaymentDate1(wagePaymentDate1)  	 	
			    .wagePaymentDate1Reason(wagePaymentDate1Reason)  	 	
			    .wagePaymentDate2(wagePaymentDate2) 	 	 
			    .wagePaymentDate2Reason(wagePaymentDate2Reason)  	 	
			    .laborManageAgreeDeduc(laborManageAgreeDeduc) 	  	
			    .laborManageAgreeDeducReason(laborManageAgreeDeducReason)  	 	
			    .reasonForRaise(reasonForRaise)  	 	
			    .bonus(bonus) 	  	
			    .bonusReason(bonusReason)  	 	
			    .severancePay(severancePay) 	  	
			    .severancePayReason(severancePayReason)  	 	
			    .retirement(retirement)  	 	
			    .retirementAge(retirementAge)  	 	
			    .selfRetiProceDate(selfRetiProceDate)  	 	
			    .dismissalProcedures(dismissalProcedures)  	 	
			    .socialInsuEnrollStatus(socialInsuEnrollStatus)  	 	
			    .appliOfEmploymentInsu(appliOfEmploymentInsu)  	 	
			    .other(other)  	 	
			    .issueClassification(issueClassification)  	 	
			    .dateOfIssue(dateOfIssue)  	 	
			    .unitPrice1(unitPrice1)  	 	
			    .unitPrice2(unitPrice2)  	 	
			    .unitPrice3(unitPrice3)  	 	
			    .wageDeadline2(wageDeadline2)  	 	
			    .wageDeadline2Reasons(wageDeadline2Reasons)  	 	
			    .basePrice(basePrice)  	 	
			    .unitPrice01M(unitPrice01M)  	 	
			    .unitPrice02M(unitPrice02M)  	 	
			    .unitPrice03M(unitPrice03M)  	 	
			    .unitPrice04M(unitPrice04M) 	  	
			    .unitPrice05M(unitPrice05M) 	  	
			    .unitPrice06M(unitPrice06M) 
			    .unitPrice07M(unitPrice07M) 	  	
			    .unitPrice08M(unitPrice08M) 	  	
			    .unitPrice09M(unitPrice09M) 	  	
			    .unitPrice10M(unitPrice10M)  	 	
			    .unitPrice11M(unitPrice11M)  	 	
			    .unitPrice12M(unitPrice12M)  	 
			    .contractExpirationDate(contractExpirationDate)  	 	
			    .annualHolidays(annualHolidays)  	 	
			    .insteadOfLeisure(insteadOfLeisure)  	 	 
			    .times60verMowingRate(times60verMowingRate)  	 	
			    .continueEmployment(continueEmployment)  	 	
			    .continueEmploymentAge(continueEmploymentAge) 	 	
				.build();
		
		return domain;
			    
	}

}

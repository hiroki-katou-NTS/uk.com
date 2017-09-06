package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@Getter
public class SubCondition {
	
	/*会社ID*/
	private String companyId;

	/*特別休暇コード*/
	private SpecialHolidayCode specialHolidayCode;
	
	/*性別制限*/
	private UseGender useGender;
	
	/*雇用制限*/
	private UseEmployee useEmployee;
	
	/*分類制限*/
	private UseCls useCls;
	
	/*年齢制限*/
	private UseAge useAge;
	
	/*性別区分*/
	private GenderAtr genderAtr;
	
	/*年齢上限*/
	private LimitAgeFrom limitAgeFrom;
	
	/*年齢下限*/
	private LimitAgeTo limitAgeTo;
	
	/*年齢基準区分*/
	private AgeCriteriaAtr ageCriteriaAtr;
	
	/*年齢基準年区分*/
	private AgeBaseYearAtr ageBaseYearAtr;
	
	/*年齢基準日*/
	private AgeBaseDates ageBaseDates;

public static SubCondition createFromJavaType(
		String companyId,
		String specialHolidayCode,
		int useGender,
		int useEmployee,
		int useCls,
		int useAge,
		int genderAtr,
		int limitAgeFrom,
		int limitAgeTo,
		int ageCriteriaAtr,
		int ageBaseYearAtr,
		int ageBaseDates){
				return new SubCondition(companyId, 
				new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(useGender, UseGender.class),
				EnumAdaptor.valueOf(useEmployee, UseEmployee.class),
				EnumAdaptor.valueOf(useCls, UseCls.class),
				EnumAdaptor.valueOf(useAge, UseAge.class),
				EnumAdaptor.valueOf(genderAtr, GenderAtr.class),
				new LimitAgeFrom(limitAgeFrom),
				new LimitAgeTo(limitAgeTo),
				EnumAdaptor.valueOf(ageCriteriaAtr, AgeCriteriaAtr.class),
				EnumAdaptor.valueOf(ageBaseYearAtr, AgeBaseYearAtr.class),
				new AgeBaseDates(ageBaseDates));
}

public SubCondition() {
	// TODO Auto-generated constructor stub
}
}

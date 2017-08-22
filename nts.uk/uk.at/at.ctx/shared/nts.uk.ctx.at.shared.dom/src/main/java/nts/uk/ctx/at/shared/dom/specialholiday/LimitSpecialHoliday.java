package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.Getter;

@Getter
public class LimitSpecialHoliday {
	
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
}

package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;

import lombok.Data;
@Data
public class SubConditionDto {
	/**会社ID*/
	private String companyId;

	/**特別休暇コード*/
	private String specialHolidayCode;
	
	/**性別制限*/
	private int useGender;
	
	/**雇用制限*/
	private int useEmployee;
	
	/**分類制限*/
	private int useCls;
	
	/**年齢制限*/
	private int useAge;
	
	/**性別区分*/
	private int genderAtr;
	
	/**年齢上限*/
	private int limitAgeFrom;
	
	/**年齢下限*/
	private int limitAgeTo;
	
	/**年齢基準区分*/
	private int ageCriteriaAtr;
	
	/**年齢基準年区分*/
	private int ageBaseYearAtr;
	
	/**年齢基準日*/
	private int ageBaseDates;
	
	private List<String> employmentList;
	
	private List<String> classificationList;
	
	
}

package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class SubConditionCommand {

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
	private Integer limitAgeFrom;
	
	/**年齢下限*/
	private Integer limitAgeTo;
	
	/**年齢基準区分*/
	private int ageCriteriaAtr;
	
	/**年齢基準年区分*/
	private int ageBaseYearAtr;
	
	/**年齢基準日*/
	private int ageBaseDates;
	
	private List<String> employmentList;
	
	private List<String> classificationList;
}

package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;

import lombok.Data;

@Data
public class SpecialHolidayDto {

	/*特別休暇コード*/
	private String specialHolidayCode;

	/*特別休暇名称*/
	private String specialHolidayName;

	/*定期付与*/
	private int grantMethod;

	/*メモ*/
	private String memo;
	
	private List<String> workTypeList;
	
	private GrantRegularDto grantRegular;
	
	private GrantPeriodicDto grantPeriodic;
	
	private SphdLimitDto sphdLimit;
	
	private SubConditionDto subCondition;
	
	private GrantSingleDto grantSingle;
}

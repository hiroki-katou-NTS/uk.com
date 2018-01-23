package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.Data;

@Data
public class SphdLimitCommand {
	
	/***会社ID*/
	private String companyId;

	/**特別休暇コード*/
	private String specialHolidayCode;
	
	/**月数*/
	private Integer specialVacationMonths;
	
	/**年数*/
	private Integer specialVacationYears;
	
	/**付与日数を繰り越す*/
	private int grantCarryForward;
	
	/**繰越上限日数*/
	private int limitCarryoverDays;
	
	/**特別休暇の期限方法*/
	private int specialVacationMethod; 
	

}

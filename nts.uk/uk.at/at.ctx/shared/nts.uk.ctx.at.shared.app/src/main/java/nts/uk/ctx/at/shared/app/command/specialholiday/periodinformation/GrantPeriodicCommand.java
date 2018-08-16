package nts.uk.ctx.at.shared.app.command.specialholiday.periodinformation;

import lombok.Value;

@Value
public class GrantPeriodicCommand {
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private int specialHolidayCode;
	
	/** 期限指定方法 */
	private int timeSpecifyMethod;
	
	/** 使用可能期間 */
	private AvailabilityPeriodCommand availabilityPeriod;
	
	/** 有効期限 */
	private SpecialVacationDeadlineCommand expirationDate;
	
	/** 繰越上限日数 */
	private int limitCarryoverDays;
}

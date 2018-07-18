package nts.uk.ctx.at.shared.app.command.specialholidaynew.periodinformation;

import lombok.Value;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Value
public class GrantPeriodicCommand {
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private int specialHolidayCode;
	
	/** 期限指定方法 */
	private int timeSpecifyMethod;
	
	/** 使用可能期間 */
	private DatePeriod availabilityPeriod;
	
	/** 有効期限 */
	private SpecialVacationDeadlineCommand expirationDate;
	
	/** 繰越上限日数 */
	private int limitCarryoverDays;
}

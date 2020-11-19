package nts.uk.ctx.at.shared.app.find.holidaysetting.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublicHolidaySettingDto {

	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The Constant FALSE_VALUE. */
	private final static int FALSE_VALUE = 0;
	
	/** 公休を管理する */	
	private Integer managePublicHoliday;	
	
	/** 公休繰越期限 */
	private Integer publicHdCarryOverDeadline;
	
	/** 公休日数がマイナス時に繰越する */
	private Integer carryOverNumberOfPublicHdIsNegative;
	
	/** 公休管理期間  */
	private Integer publicHolidayPeriod;	
}

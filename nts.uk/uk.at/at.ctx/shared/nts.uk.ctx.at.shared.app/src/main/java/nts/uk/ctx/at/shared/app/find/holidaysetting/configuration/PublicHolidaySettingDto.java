package nts.uk.ctx.at.shared.app.find.holidaysetting.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author quytb
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublicHolidaySettingDto {	
	/** 公休を管理する */	
	private Integer managePublicHoliday;	
	
	/** 公休繰越期限 */
	private Integer publicHdCarryOverDeadline;
	
	/** 公休日数がマイナス時に繰越する */
	private Integer carryOverNumberOfPublicHdIsNegative;
	
	/** 公休管理期間  */
	private Integer publicHolidayPeriod;	
}

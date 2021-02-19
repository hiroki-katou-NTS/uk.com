/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author quytb
 * 公休設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.休日管理.公休管理.設定.公休設定
 * 
 */
@Getter
@Setter
@AllArgsConstructor
public class PublicHolidaySetting extends AggregateRoot {
	
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** 公休を管理する */	
	private int isManagePublicHoliday;
	
	/** 公休管理期間  */
	private PublicHolidayPeriod publicHolidayPeriod;
	
	/** 公休繰越期限 */
	private PublicHolidayCarryOverDeadline publicHolidayCarryOverDeadline;
	
	/** 公休日数がマイナス時に繰越する */
	private int carryOverNumberOfPublicHolidayIsNegative;
	
}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;

/**
 * The Class PublicHolidaySettingDomainEvent.
 */
@Value
@EqualsAndHashCode(callSuper = false)
// 公休の管理区分を変更した
public class PublicHolidaySettingDomainEvent extends DomainEvent {
	
	// 会社の公休管理をする
	private boolean isManageComPublicHd;
}
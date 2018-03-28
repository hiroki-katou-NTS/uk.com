/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;

/*
 * 時間代休の消化単位．管理区分変更
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class CompensatoryDigestiveTimeUnitDomainEvent extends DomainEvent {
	/** 時間代休管理区分  */
	private boolean parameter;
}

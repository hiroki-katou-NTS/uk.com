/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workmanagementmultiple;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;

/**
 * The Class WorkManagementMultipleDomainEvent.
 */
@Value
@EqualsAndHashCode(callSuper = false)
// 複数回勤務の使用区分が変更された
public class WorkManagementMultipleDomainEvent extends DomainEvent {
	/** The use atr. */
	// 使用区分
	private boolean useAtr;
}

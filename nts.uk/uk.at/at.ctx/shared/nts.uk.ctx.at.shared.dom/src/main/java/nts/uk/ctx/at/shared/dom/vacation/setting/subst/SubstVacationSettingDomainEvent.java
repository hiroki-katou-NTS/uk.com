/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;

/*
 * 振休管理設定．管理区分変更
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class SubstVacationSettingDomainEvent extends DomainEvent {
	/** 管理区分 */
	private boolean parameter;
}

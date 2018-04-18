/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;

/*
 * 積立年休設定．管理区分変更
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class RetentionYearlySettingDomainEvent extends DomainEvent {
	/** 管理区分 */
	private boolean parameter;
}

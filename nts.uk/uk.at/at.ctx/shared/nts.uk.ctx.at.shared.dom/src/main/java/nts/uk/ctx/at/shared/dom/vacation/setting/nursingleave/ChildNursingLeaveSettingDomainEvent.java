/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;

/*
 * 子の看護休暇設定．管理区分変更
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ChildNursingLeaveSettingDomainEvent extends DomainEvent {
	/** 管理区分 */
	private boolean parameter;
}

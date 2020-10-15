/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class AddSetManageWorkHour.
 */
@Getter
@Setter
@Builder
// 就業時間の加算設定管理
public class AddSetManageWorkHour extends AggregateRoot implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 会社ID */
	private String companyId;

	/** 時間外超過の加算設定 */
	private NotUseAtr additionSettingOfOvertime;
}

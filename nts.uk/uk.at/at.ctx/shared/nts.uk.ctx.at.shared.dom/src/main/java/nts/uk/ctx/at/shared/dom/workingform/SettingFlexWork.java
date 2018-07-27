/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingform;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class FlexWorkSetting.
 */
// フレックス勤務の設定
@Getter
public class SettingFlexWork extends AggregateRoot {

	/** The company id. */
	private String companyId; // 会社ID

	/** The flex work managing. */
	private boolean flexWorkManaging; // フレックス勤務を管理する

	/**
	 * Instantiates a new flex work setting.
	 *
	 * @param companyId the company id
	 * @param flexWorkManaging the flex work managing
	 */
	public SettingFlexWork(String companyId, boolean flexWorkManaging) {
		this.companyId = companyId;
		this.flexWorkManaging = flexWorkManaging;
	}
}

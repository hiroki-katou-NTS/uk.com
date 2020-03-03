/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.func;

import lombok.Builder;
import lombok.Data;

/**
 * The Class SettingWorkMultipleDto.
 */
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data
@Builder
public class SettingWorkMultipleDto {

	/** The flex work managing. */
	private int workMultiple; // フレックス勤務を管理する
}

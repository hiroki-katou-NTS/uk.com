/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workingform;

import lombok.Builder;
import lombok.Data;

/**
 * The Class SettingFlexWorkDto.
 */
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data
@Builder
public class SettingFlexWorkDto {

	/** The flex work managing. */
	private boolean flexWorkManaging; // フレックス勤務を管理する
}

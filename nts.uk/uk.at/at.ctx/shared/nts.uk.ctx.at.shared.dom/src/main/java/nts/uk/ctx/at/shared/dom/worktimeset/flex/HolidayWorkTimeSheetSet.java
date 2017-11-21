/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.TimeZoneRounding;

//休出時間の時間帯設定
@Getter
public class HolidayWorkTimeSheetSet extends DomainObject {

	/** The timezone. */
	// 時間帯
	private TimeZoneRounding timezone;

	/** The is legal holiday constraint time. */
	private boolean isLegalHolidayConstraintTime;

	// TODO 法定内休出枠NO
	// private legalHolidayFrameNo

	/** The is non statutory dayoff constraint time. */
	// 法定外休出を拘束時間として扱う
	private boolean isNonStatutoryDayoffConstraintTime;

	// 法定外休出枠NO
	// private statutoryHolidayOutboundNo

	/** The is non statutory holiday constraint time. */
	// 法定外祝日を拘束時間として扱う
	private boolean isNonStatutoryHolidayConstraintTime;

	// 法定外祝日枠NO
	// private statutoryHolidayFrameNo;
}

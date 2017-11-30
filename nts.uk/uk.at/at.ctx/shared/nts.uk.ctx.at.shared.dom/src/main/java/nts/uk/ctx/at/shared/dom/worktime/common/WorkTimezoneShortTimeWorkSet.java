/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimezoneShortTimeWorkSet.
 */
//就業時間帯の短時間勤務設定
@Getter
public class WorkTimezoneShortTimeWorkSet extends DomainObject{

	/** The nurs timezone work use. */
	//介護時間帯に勤務した場合に勤務として扱う
	private boolean nursTimezoneWorkUse;
	
	/** The employment time deduct. */
	//就業時間から控除する
	private boolean employmentTimeDeduct;
	
	/** The child care work use. */
	//育児時間帯に勤務した場合に勤務として扱う
	private boolean childCareWorkUse;
}

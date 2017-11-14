/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimezoneCommonSet.
 */
//就業時間帯の共通設定
@Getter
public class WorkTimezoneCommonSet extends DomainObject{

	/** The Zero H stradd calculate set. */
	//0時跨ぎ計算設定
	private boolean ZeroHStraddCalculateSet;

	/** The interval set. */
	//インターバル時間設定
	private IntervalTimeSet intervalSet;
	
	/** The sub hol time set. */
	//代休時間設定
	private WorkTimezoneOtherSubHolTimeSet subHolTimeSet;
	
	/** The raising salary set. */
	//加給設定
	private WorkTimezoneRaisingSalarySet raisingSalarySet;
	
	/** The medical set. */
	//医療設定
	private List<WorkTimezoneMedicalSet> medicalSet;
	
	/** The go out set. */
	//外出設定
	private WorkTimezoneGoOutSet goOutSet;
	
	/** The stamp set. */
	//打刻設定
	private WorkTimezoneStampSet stampSet;
	
	/** The late night time set. */
	//深夜時間設定
	private WorkTimezoneLateNightTimeSet lateNightTimeSet;
	
	/** The short time work set. */
	//短時間勤務設定
	private WorkTimezoneShortTimeWorkSet shortTimeWorkSet;
	
	/** The extraord time set. */
	//臨時時間設定
	private WorkTimezoneExtraordTimeSet extraordTimeSet;
	
	/** The late early set. */
	//遅刻・早退設定
	private WorkTimezoneLateEarlySet lateEarlySet;
}

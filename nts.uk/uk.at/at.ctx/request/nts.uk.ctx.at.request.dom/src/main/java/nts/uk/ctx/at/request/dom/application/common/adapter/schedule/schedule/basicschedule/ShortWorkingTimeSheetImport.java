package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule;

import lombok.Getter;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ShortWorkingTimeSheetImport {
	/** 短時間勤務枠NO: 短時間勤務枠NO*/
	private int shortWorkTimeFrameNo;
	
	/** 育児介護区分: 育児介護区分*/
	private int childCareAttr;

	/** 開始: 時刻(日区分付き) */
	private int startTime;
	
	/** 終了: 時刻(日区分付き) */
	private int endTime;
	
	/** 控除時間: 勤怠時間 */
	private int deductionTime;
	
	/** 時間: 勤怠時間 */
	private int shortTime;
}

package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.primitive.ActualWorkTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.primitive.WorkFrameNo;

/** 日別実績の作業時間 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkTimeOfDaily {

	/** no need */
		/** 年月日: 年月日 */
		/** 社員ID: 社員ID */
	/** no need */
	
	/** 作業枠NO: 作業枠NO */
	private WorkFrameNo workFrameNo;
	
	/** 時間帯: 作業実績の時間帯 */
	private ActualWorkTimeSheet timeSheet;
	
	/** 時間: 作業実績の時間 */
	private ActualWorkTime workTime;
}

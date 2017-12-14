package nts.uk.ctx.at.record.dom.breakorgoout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;

/**
 * 
 * @author nampt
 * 休憩時間帯
 *
 */
@Getter
@AllArgsConstructor
public class BreakTimeSheet extends DomainObject {
	
	//休憩枠NO
	private BreakFrameNo breakFrameNo;
	
	//開始 - 勤怠打刻(実打刻付き)
	private WorkStamp startTime;
	
	//終了 - 勤怠打刻(実打刻付き)
	private WorkStamp endTime;

}

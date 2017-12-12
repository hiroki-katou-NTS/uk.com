package nts.uk.ctx.at.record.dom.breakorgoout;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 外出時間帯
 *
 */
@Setter
@AllArgsConstructor
@Getter
public class OutingTimeSheet extends DomainObject {
	


	//外出枠NO
	private OutingFrameNo outingFrameNo;
	
	// 勤怠打刻(実打刻付き)
	private TimeActualStamp goOut;
	
	//外出時間.計算時間
	private AttendanceTime outingTimeCalculation;
	
	//外出時間.時間
	private AttendanceTime outingTime;
	
	private GoingOutReason reasonForGoOut;
	
	// 勤怠打刻(実打刻付き)
	private TimeActualStamp comeBack;
	
}

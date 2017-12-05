package nts.uk.ctx.at.record.dom.breakorgoout;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 外出時間帯
 *
 */
@Getter
public class OutingTimeSheet extends DomainObject {
	
	//外出枠NO
	private OutingFrameNo outingFrameNo;
	
	// 勤怠打刻(実打刻付き) - primitive value
	private BigDecimal goOut;
	
	//外出時間.計算時間
	private AttendanceTime outingTimeCalculation;
	
	//外出時間.時間
	private AttendanceTime outingTime;
	
	private GoingOutReason reasonForGoOut;
	
	// 勤怠打刻(実打刻付き) - primitive value
	private BigDecimal comeBack;
	
}

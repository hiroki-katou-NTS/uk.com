package nts.uk.ctx.at.record.breakorgoout;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.at.record.breakorgoout.enums.GoingOutReason;

/**
 * 
 * @author nampt
 * 外出時間帯
 *
 */
@Getter
public class OutingTimeSheet {
	
	//外出枠NO - primitive value
	private String outingFrameNo;
	
	// 勤怠打刻(実打刻付き) - primitive value
	private BigDecimal goOut;
	
	//計算付き時間 - primitive value
	private BigDecimal goOutTime;
	
	private GoingOutReason reasonForGoOut;
	
	// 勤怠打刻(実打刻付き) - primitive value
	private BigDecimal comeBack;
	
}

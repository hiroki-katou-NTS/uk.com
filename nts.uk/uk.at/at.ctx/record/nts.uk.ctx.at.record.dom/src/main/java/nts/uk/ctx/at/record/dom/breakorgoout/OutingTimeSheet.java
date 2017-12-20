package nts.uk.ctx.at.record.dom.breakorgoout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 外出時間帯
 *
 */
@AllArgsConstructor
@Getter
public class OutingTimeSheet extends DomainObject {
	
	/*
	 * 外出枠NO
	 */
	private OutingFrameNo outingFrameNo;
	
	/*
	 * 外出
	 */
	private TimeActualStamp goOut;
	
	/*
	 * 
	 */
	private AttendanceTime outingTimeCalculation;
	
	/*
	 * 外出時間
	 */
	private AttendanceTime outingTime;
	
	/*
	 * 外出理由
	 */
	private GoingOutReason reasonForGoOut;
	
	/*
	 * 戻り
	 */
	private TimeActualStamp comeBack;

	public void setProperty(OutingFrameNo outingFrameNo, TimeActualStamp goOut, AttendanceTime outingTimeCalculation,
			AttendanceTime outingTime, GoingOutReason reasonForGoOut, TimeActualStamp comeBack) {
		this.outingFrameNo = outingFrameNo;
		this.goOut = goOut;
		this.outingTimeCalculation = outingTimeCalculation;
		this.outingTime = outingTime;
		this.reasonForGoOut = reasonForGoOut;
		this.comeBack = comeBack;
	}
	
	
	
}

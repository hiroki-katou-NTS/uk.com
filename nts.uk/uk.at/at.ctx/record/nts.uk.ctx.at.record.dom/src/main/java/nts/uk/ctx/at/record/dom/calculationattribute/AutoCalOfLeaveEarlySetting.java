package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveEarlyAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveLateAttr;

/**
 * 
 * @author nampt
 * 遅刻早退の自動計算設定
 *
 */
@Getter
public class AutoCalOfLeaveEarlySetting {
	
	//早退
	private LeaveEarlyAttr leaveEarly;
	
	//遅刻
	private LeaveLateAttr leaveLate;

}

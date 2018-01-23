package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveAttr;

/**
 * 
 * @author nampt
 * 遅刻早退の自動計算設定
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutoCalOfLeaveEarlySetting {
	
	//早退
	private LeaveAttr leaveEarly;
	
	//遅刻
	private LeaveAttr leaveLate;

}

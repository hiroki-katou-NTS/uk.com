package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;


/**
 * 日別実績の外出時間
 * @author ken_takasu
 *
 */
@Value
public class GoOutTimeOfDaily {
	
	private DeductionTotalTime forDeductionTotalTime;//控除用合計時間
	private DeductionTotalTime forRecordTotalTime;//計上用合計時間
	private TimevacationUseTimeOfDaily HolidayUseTime;//休暇使用時間
	

}

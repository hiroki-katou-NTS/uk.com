package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.WorkStampWithActualStamp;

/**
 * 外出時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class GoOutTimeSheet {
	private WorkStampWithActualStamp goOut;
	private WorkStampWithActualStamp comeBack;
	private StampGoOutReason goOutReason;
}

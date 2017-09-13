package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;

/**
 * 勤怠打刻(実打刻付き)
 * @author keisuke_hoshina
 *
 */
@Value
public class WorkStampWithActualStamp {
	private WorkStamp engrave;
	private WorkStamp actualEngrave;
	private int numberOfReflectionEngrave;
}

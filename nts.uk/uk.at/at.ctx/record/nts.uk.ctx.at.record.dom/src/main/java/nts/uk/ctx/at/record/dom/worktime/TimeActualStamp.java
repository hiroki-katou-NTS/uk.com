package nts.uk.ctx.at.record.dom.worktime;

import lombok.Getter;

/**
 * 
 * @author nampt
 * 勤怠打刻(実打刻付き)
 *
 */
@Getter
public class TimeActualStamp {
	
	private WorkStamp actualStamp;
	
	private WorkStamp stamp;
	
	private int numberOfReflectionStamp;
}

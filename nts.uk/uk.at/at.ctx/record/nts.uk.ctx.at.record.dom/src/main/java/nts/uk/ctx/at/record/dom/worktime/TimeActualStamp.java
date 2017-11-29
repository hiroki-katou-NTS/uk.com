package nts.uk.ctx.at.record.dom.worktime;

import java.util.Optional;

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
	
	private Optional<WorkStamp> stamp;
	
	private int numberOfReflectionStamp;
}

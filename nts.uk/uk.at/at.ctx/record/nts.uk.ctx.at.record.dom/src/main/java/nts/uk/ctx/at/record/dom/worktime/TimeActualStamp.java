package nts.uk.ctx.at.record.dom.worktime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author nampt
 * 勤怠打刻(実打刻付き)
 *
 */
@Getter
@NoArgsConstructor
public class TimeActualStamp {
	
	private WorkStamp actualStamp;
	
	private WorkStamp stamp;
	
	private int numberOfReflectionStamp;

	public TimeActualStamp(WorkStamp actualStamp, WorkStamp stamp, int numberOfReflectionStamp) {
		super();
		this.actualStamp = actualStamp;
		this.stamp = stamp;
		this.numberOfReflectionStamp = numberOfReflectionStamp;
	}
	
}

package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author nampt
 * 勤怠打刻(実打刻付き)
 *
 */
@Getter
public class TimeActualStampExport {
	
	private Optional<WorkStampExport> actualStamp;
	@Setter
	private Optional<WorkStampExport> stamp = Optional.empty();
	
	//打刻反映回数
	private Integer numberOfReflectionStamp;

	public TimeActualStampExport(WorkStampExport actualStamp, WorkStampExport stamp,
			Integer numberOfReflectionStamp) {
		super();
		this.actualStamp = Optional.ofNullable(actualStamp);
		this.stamp = Optional.ofNullable(stamp);
		this.numberOfReflectionStamp = numberOfReflectionStamp;
	}
	
	
}

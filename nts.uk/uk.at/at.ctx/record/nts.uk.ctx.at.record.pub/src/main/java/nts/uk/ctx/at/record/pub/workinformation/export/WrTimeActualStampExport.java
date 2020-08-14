package nts.uk.ctx.at.record.pub.workinformation.export;

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
public class WrTimeActualStampExport {
	
	private Optional<WrWorkStampExport> actualStamp;
	@Setter
	private Optional<WrWorkStampExport> stamp = Optional.empty();
	
	//打刻反映回数
	private Integer numberOfReflectionStamp;

	public WrTimeActualStampExport(WrWorkStampExport actualStamp, WrWorkStampExport stamp,
			Integer numberOfReflectionStamp) {
		super();
		this.actualStamp = Optional.ofNullable(actualStamp);
		this.stamp = Optional.ofNullable(stamp);
		this.numberOfReflectionStamp = numberOfReflectionStamp;
	}
	
	
}

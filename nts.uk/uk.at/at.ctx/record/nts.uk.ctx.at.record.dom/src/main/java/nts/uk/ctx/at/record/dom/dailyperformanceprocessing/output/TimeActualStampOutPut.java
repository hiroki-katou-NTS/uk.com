package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author nampt
 *  勤怠打刻(実打刻付き) temporary - this class created for contain
 * temporary data
 *
 */
@Getter
@NoArgsConstructor
@Setter
public class TimeActualStampOutPut {

	private WorkStampOutPut actualStamp;

	private WorkStampOutPut stamp;

	private int numberOfReflectionStamp;

}

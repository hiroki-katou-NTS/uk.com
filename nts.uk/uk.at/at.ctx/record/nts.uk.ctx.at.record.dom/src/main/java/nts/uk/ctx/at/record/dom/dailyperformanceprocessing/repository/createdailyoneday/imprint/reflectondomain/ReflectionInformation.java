package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectondomain;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeActualStamp;

/**
 * 反映情報（Temporary）
 * 
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class ReflectionInformation {
	/**
	 * 枠No
	 */
	private int frameNo;

	/** 開始: 勤怠打刻(実打刻付き) */
	private Optional<TimeActualStamp> start;

	/** 終了: 勤怠打刻(実打刻付き) */
	private Optional<TimeActualStamp> end;

	public ReflectionInformation(int frameNo, Optional<TimeActualStamp> start, Optional<TimeActualStamp> end) {
		super();
		this.frameNo = frameNo;
		this.start = start;
		this.end = end;
	}

}

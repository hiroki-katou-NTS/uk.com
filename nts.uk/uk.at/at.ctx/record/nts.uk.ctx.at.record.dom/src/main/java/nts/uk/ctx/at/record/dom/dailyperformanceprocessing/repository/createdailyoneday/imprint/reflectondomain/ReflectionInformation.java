package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflectondomain;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;

/**
 * 反映情報（Temporary）
 * 
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
@Setter
public class ReflectionInformation {
	/**
	 * 枠No
	 */
	private int frameNo;

	/** 開始: 勤怠打刻(実打刻付き) */
	private Optional<WorkStamp> start;

	/** 終了: 勤怠打刻(実打刻付き) */
	private Optional<WorkStamp> end;

	public ReflectionInformation(int frameNo, Optional<WorkStamp> start, Optional<WorkStamp> end) {
		super();
		this.frameNo = frameNo;
		this.start = start;
		this.end = end;
	}

}

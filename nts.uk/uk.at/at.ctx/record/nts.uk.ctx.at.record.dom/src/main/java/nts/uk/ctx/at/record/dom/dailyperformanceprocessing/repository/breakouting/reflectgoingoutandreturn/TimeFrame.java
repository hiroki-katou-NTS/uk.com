package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * 時間帯枠（Temporary）
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class TimeFrame {
	/**
	 * 反映回数
	 * 打刻\反映回数を
	 */
	private int numberOfReflections;
	/**
	 * 枠No
	 */
	private int frameNo;

	/**　開始: 勤怠打刻(実打刻付き) */
	private Optional<TimeActualStamp> start;
	
	/**　終了: 勤怠打刻(実打刻付き) */
	private Optional<TimeActualStamp> end;
	
	/**
	 * 外出理由
	 */
	private Optional<GoingOutReason> goOutReason;

	public TimeFrame(int numberOfReflections, int frameNo, Optional<TimeActualStamp> start,
			Optional<TimeActualStamp> end, GoingOutReason goOutReason) {
		super();
		this.numberOfReflections = numberOfReflections;
		this.frameNo = frameNo;
		this.start = start;
		this.end = end;
		this.goOutReason = Optional.ofNullable(goOutReason);
	}

	public static TimeFrame of(int numberOfReflections, int frameNo, Optional<WorkStamp> start,
			Optional<WorkStamp> end, GoingOutReason goOutReason) {
		
		TimeFrame domain = new TimeFrame();
		
		domain.numberOfReflections = numberOfReflections;
		domain.frameNo = frameNo;
		domain.start = Optional.of(new TimeActualStamp(start, start, 1, Optional.empty(), Optional.empty()));
		domain.end =  Optional.of(new TimeActualStamp(end, end, 1, Optional.empty(), Optional.empty()));
		domain.goOutReason = Optional.ofNullable(goOutReason);
		
		return domain;
	}
	
	
	public void setFrameNo(int frameNo) {
		this.frameNo = frameNo;
	}

	public void setGoOutReason(Optional<GoingOutReason> goOutReason) {
		this.goOutReason = goOutReason;
	}

	public void setEnd(Optional<TimeActualStamp> end) {
		this.end = end;
	}

	public void setNumberOfReflections(int numberOfReflections) {
		this.numberOfReflections = numberOfReflections;
	}

	public void setStart(Optional<TimeActualStamp> start) {
		this.start = start;
	}
	
	
}

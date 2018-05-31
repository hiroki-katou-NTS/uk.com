package nts.uk.ctx.at.record.dom.worktime;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author nampt
 * 勤怠打刻(実打刻付き)
 *
 */
@Getter
public class TimeActualStamp {
	
	private Optional<WorkStamp> actualStamp;
	
	private Optional<WorkStamp> stamp = Optional.of(new WorkStamp());
	
	private Integer numberOfReflectionStamp;
	
	/**
	 * 打刻時間を指定時間分経過させた勤怠打刻を返す
	 * @param moveTime　指定時間
	 * @return　勤怠打刻
	 */
	public TimeActualStamp moveAheadStampTime(int moveTime) {
		WorkStamp actualWorkStamp = new WorkStamp(this.actualStamp.get().getAfterRoundingTime().forwardByMinutes(moveTime),
												  this.actualStamp.get().getTimeWithDay().forwardByMinutes(moveTime),
												  this.actualStamp.get().getLocationCode().isPresent() ? this.actualStamp.get().getLocationCode().get() : null,
												  this.actualStamp.get().getStampSourceInfo());
		
		WorkStamp stamp = new WorkStamp(this.stamp.get().getAfterRoundingTime().forwardByMinutes(moveTime),
										this.stamp.get().getTimeWithDay().forwardByMinutes(moveTime),
										this.stamp.get().getLocationCode().isPresent() ? this.stamp.get().getLocationCode().get() : null,
										this.stamp.get().getStampSourceInfo());
		
		return new TimeActualStamp( actualWorkStamp,
									stamp,
									this.numberOfReflectionStamp);
	}
	/**
	 * 打刻時間を指定時間分戻した勤怠打刻を返す
	 * @param moveTime 指定時間
	 * @return　勤怠打刻
	 */
	public TimeActualStamp moveBackStampTime(int moveTime) {
		WorkStamp actualWorkStamp = new WorkStamp(this.actualStamp.get().getAfterRoundingTime().backByMinutes(moveTime),
				  this.actualStamp.get().getTimeWithDay().backByMinutes(moveTime),
				  this.actualStamp.get().getLocationCode().isPresent() ? this.actualStamp.get().getLocationCode().get() : null,
				  this.actualStamp.get().getStampSourceInfo());

		WorkStamp stamp = new WorkStamp(this.stamp.get().getAfterRoundingTime().forwardByMinutes(moveTime),
				  this.stamp.get().getTimeWithDay().backByMinutes(moveTime),
				  this.stamp.get().getLocationCode().isPresent() ? this.stamp.get().getLocationCode().get() : null,
				  this.stamp.get().getStampSourceInfo());
		
		return new TimeActualStamp(actualWorkStamp,
								   stamp,
                				   this.numberOfReflectionStamp);
	}
	public TimeActualStamp() {
		super();
		this.actualStamp = Optional.empty();
		this.stamp = Optional.empty();
		this.numberOfReflectionStamp = 0;
	}
	public TimeActualStamp(WorkStamp actualStamp, WorkStamp stamp, Integer numberOfReflectionStamp) {
		super();
		this.actualStamp = Optional.ofNullable(actualStamp);
		this.stamp = Optional.ofNullable(stamp);
		this.numberOfReflectionStamp = numberOfReflectionStamp;
	}
	public void setPropertyTimeActualStamp(Optional<WorkStamp> actualStamp, Optional<WorkStamp> stamp, Integer numberOfReflectionStamp){
		this.actualStamp = actualStamp == null ? Optional.empty() : actualStamp;
		this.stamp = stamp == null ? Optional.empty() : stamp;
		this.numberOfReflectionStamp = numberOfReflectionStamp;
	}
	
	/** 「打刻」を削除する */
	public void removeStamp() {
		this.stamp = Optional.empty();
	}
}

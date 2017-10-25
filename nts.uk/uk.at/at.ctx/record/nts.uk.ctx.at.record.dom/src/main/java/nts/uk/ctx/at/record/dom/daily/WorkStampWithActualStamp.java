package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;

/**
 * 勤怠打刻(実打刻付き)
 * @author keisuke_hoshina
 *
 */
@Value
public class WorkStampWithActualStamp {
	private WorkStamp engrave;
	private WorkStamp actualEngrave;
	private int numberOfReflectionEngrave;
	
	/**
	 * 打刻時間を指定時間分経過させた勤怠打刻を返す
	 * @param moveTime　指定時間
	 * @return　勤怠打刻
	 */
	public WorkStampWithActualStamp moveAheadStampTime(int moveTime) {
		return new WorkStampWithActualStamp(new WorkStamp(this.engrave.getTimesOfDay().forwardByMinutes(moveTime)),
											new WorkStamp(this.actualEngrave.getTimesOfDay().forwardByHours(moveTime)),
							                this.numberOfReflectionEngrave);
	}
	
	/**
	 * 打刻時間を指定時間分戻した勤怠打刻を返す
	 * @param moveTime 指定時間
	 * @return　勤怠打刻
	 */
	public WorkStampWithActualStamp moveBackStampTime(int moveTime) {
		return new WorkStampWithActualStamp(new WorkStamp(this.engrave.getTimesOfDay().backByMinutes(moveTime)),
				new WorkStamp(this.actualEngrave.getTimesOfDay().backByMinutes(moveTime)),
                this.numberOfReflectionEngrave);
	}
}

package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@AllArgsConstructor
public class ShiftAlarmInformation {
	/**
	 * エラー有無
	 */
	private ErrorState errorState;
	/**
	 * 実行状態
	 */
	private ExcutionState excutionState;
	/**
	 * 終了時刻
	 */
	private TimeWithDayAttr endTime;
	/**
	 * 開始時刻
	 */
	private TimeWithDayAttr startTime;
	/**
	 * アラーム
	 */
	private Optional<List<ShiftAlarm>> alarm;

	public boolean isError() {
		return (errorState == ErrorState.ERROR);
	}
}

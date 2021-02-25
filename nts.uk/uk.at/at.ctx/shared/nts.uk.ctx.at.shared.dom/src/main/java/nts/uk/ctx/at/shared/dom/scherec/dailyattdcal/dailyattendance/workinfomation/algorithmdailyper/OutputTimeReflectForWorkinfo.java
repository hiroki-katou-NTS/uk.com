package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class OutputTimeReflectForWorkinfo {
	/**
	 * 終了状態：正常/就業時間帯なし/勤務種類なし/休日出勤設定なし/労働条件なし
	 */
	private EndStatus endStatus;

	/**
	 * 打刻反映範囲
	 */
	private StampReflectRangeOutput stampReflectRangeOutput;

	public OutputTimeReflectForWorkinfo(EndStatus endStatus, StampReflectRangeOutput stampReflectRangeOutput) {
		super();
		this.endStatus = endStatus;
		this.stampReflectRangeOutput = stampReflectRangeOutput;
	}

}

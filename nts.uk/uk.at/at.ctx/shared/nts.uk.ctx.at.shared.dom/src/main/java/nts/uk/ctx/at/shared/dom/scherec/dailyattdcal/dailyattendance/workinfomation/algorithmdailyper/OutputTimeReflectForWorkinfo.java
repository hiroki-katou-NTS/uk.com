package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

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
	
	/**
	 * 
	 */
	private List<ErrorMessageInfo> error = new ArrayList<>();

	public OutputTimeReflectForWorkinfo(EndStatus endStatus, StampReflectRangeOutput stampReflectRangeOutput,
			List<ErrorMessageInfo> error) {
		super();
		this.endStatus = endStatus;
		this.stampReflectRangeOutput = stampReflectRangeOutput;
		this.error = error;
	}
	
}

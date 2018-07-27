package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class OverTimeFrame {

	/**
	 * 勤怠種類:
	 * 休憩時間 - 0
	 * 残業時間 - 1
	 * 休出時間 - 2
	 * 加給時間 - 3
	 * 特定日加給時間 - 4
	 */
	private int attendanceType;
	/**
	 * 勤怠項目NO ???
	 */
	private int frameNo;
	/**枠名称*/
	private String name;
	//加給申請時間設定.特定日加給時間 - loai 3 bonus moi co
	private Integer timeItemTypeAtr;
	/**
	 * 申請時間  - phut
	 */
	@Setter
	private Integer applicationTime;
	
	/**開始時間*/
	private Integer startTime;
	/**完了時間*/
	private Integer endTime;
}

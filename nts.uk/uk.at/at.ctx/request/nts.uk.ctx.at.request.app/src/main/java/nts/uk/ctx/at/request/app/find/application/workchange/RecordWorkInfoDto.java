package nts.uk.ctx.at.request.app.find.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class RecordWorkInfoDto {
	//申請日
	private String appDate;
	
	// 勤務種類コード
	private String workTypeCode;

	// 勤務種類名称
	private String workTypeName;

	// 就業時間帯コード
	private String workTimeCode;

	// 就業時間帯名称
	private String workTimeName;

	// 開始時刻1
	private Integer startTime1;

	// 終了時刻1
	private Integer endTime1;

	// 開始時刻2
	private Integer startTime2;

	// 終了時刻2
	private Integer endTime2;

	// 休憩時間開始時刻
	private Integer breakTimeStart;

	//休憩時間終了時刻
	private Integer breakTimeEnd;
}

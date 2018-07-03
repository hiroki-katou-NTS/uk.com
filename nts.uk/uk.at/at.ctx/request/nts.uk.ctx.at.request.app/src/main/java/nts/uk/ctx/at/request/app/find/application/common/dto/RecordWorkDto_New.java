package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RecordWorkDto_New {
	// 勤務種類コード
	private String workTypeCode;
	
	// 就業時間帯コード
	private String workTimeCode;
	
	// 開始時刻1
	private Integer attendanceStampTimeFirst;
	
	// 終了時刻1
	private Integer leaveStampTimeFirst;
	
	// 開始時刻2
	private Integer attendanceStampTimeSecond;
	
	// 終了時刻2
	private Integer leaveStampTimeSecond;
}

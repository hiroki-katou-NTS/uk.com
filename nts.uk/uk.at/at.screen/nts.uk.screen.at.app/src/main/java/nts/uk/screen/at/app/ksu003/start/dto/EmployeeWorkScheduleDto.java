package nts.uk.screen.at.app.ksu003.start.dto;
/**
 * 対象社員の社員勤務予定dto - 社員勤務予定dto
 * @author phongtq
 *
 */

import java.util.List;

import lombok.Value;

@Value
public class EmployeeWorkScheduleDto {
	//開始時刻１
	private Integer startTime1;
	 //開始時刻１編集状態
	private Integer startTime1Status;
	//終了時刻１
	private Integer endTime1;
	//終了時刻１編集状態 
	private Integer endTime1Status;
	//開始時刻2
	private Integer startTime2;
	//開始時刻2編集状態 
	private Integer startTime2Status;
	//終了時刻2
	private Integer endTime2;
	//終了時刻2編集状態 
	private Integer endTime2Status;
	//List<休憩時間帯>
	private List<BreakTimeOfDailyAttdDto> listBreakTimeZoneDto;
	//勤務種類コード
	private String workTypeCode;
	//休憩時間帯編集状態 
	private Integer breakTimeStatus;
	//勤務種類編集状態
	private Integer workTypeStatus;
	//就業時間帯コード
	private String workTimeCode;
	//就業時間帯編集状態
	private Integer workTimeStatus;
}

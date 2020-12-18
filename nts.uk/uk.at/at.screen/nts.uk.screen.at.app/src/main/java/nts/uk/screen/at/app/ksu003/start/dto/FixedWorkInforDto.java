package nts.uk.screen.at.app.ksu003.start.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 対象社員の勤務固定情報dto - 勤務固定情報dto
 * @author phongtq
 *
 */
@Value
@AllArgsConstructor
public class FixedWorkInforDto {
		//就業時間帯名称
	 	private String workTimeName; 
	 	//コア開始時刻
	 	private Integer coreStartTime; 
	 	//コア終了時刻
	 	private Integer coreEndTime; 
	 	//List<残業時間帯>
	 	private List<ChangeableWorkTimeDto> overtimeHours; 
	 	//日付開始時刻範囲時間帯1
	 	private TimeZoneDto startTimeRange1; 
	 	//日付終了時刻範囲時間帯1
	 	private TimeZoneDto endTimeRange1;
	 	//勤務種類名称
	 	private String workTypeName;
	 	//日付開始時刻範囲時間帯2
	 	private TimeZoneDto startTimeRange2; 
	 	//日付終了時刻範囲時間帯2
	 	private TimeZoneDto endTimeRange2;
	 	//休憩時間帯を固定にする (0:false 1:true)
	 	private Integer fixBreakTime; 
	 	//勤務タイプ : WorkTimeForm
	 	private Integer workType;
	
}

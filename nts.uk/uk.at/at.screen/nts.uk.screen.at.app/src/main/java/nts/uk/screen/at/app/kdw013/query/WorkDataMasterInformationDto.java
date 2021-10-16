package nts.uk.screen.at.app.kdw013.query;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.worklocation.WorkLocationDto;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.DailyAttendanceItemDto;
import nts.uk.screen.at.app.kdw013.a.TaskDto;

/**
 * @author thanhpv
 * @description output for 作業データマスタ情報を取得する
 */
@NoArgsConstructor
@Getter
public class WorkDataMasterInformationDto {

	//作業1一覧：List<作業>
	public List<TaskDto> taskFrameNo1;
	
	//作業2一覧：List<作業>
	public List<TaskDto> taskFrameNo2;
	
	//作業3一覧：List<作業>
	public List<TaskDto> taskFrameNo3;
	
	//作業4一覧：List<作業>
	public List<TaskDto> taskFrameNo4;
	
	//作業5一覧：List<作業>
	public List<TaskDto> taskFrameNo5;
	
	//勤務場所一覧：List<勤務場所>
	public List<WorkLocationDto> workLocation;
	
	//List<作業補足情報の選択肢詳細>
	public List<TaskSupInfoChoicesDetailDto> taskSupInfoChoicesDetails;
	
	//List<工数実績項目>
	public List<ManHourRecordItemDto> manHourRecordItems;
	
	//List<日次の勤怠項目>
	public List<DailyAttendanceItemDto> attendanceItems;
	
	//List<工数実績項目と勤怠項目の紐付け>
	public List<ManHourRecordAndAttendanceItemLinkDto> manHourRecordAndAttendanceItemLink; 
	
	public WorkDataMasterInformationDto(List<TaskDto> taskFrameNo1, List<TaskDto> taskFrameNo2,
			List<TaskDto> taskFrameNo3, List<TaskDto> taskFrameNo4, List<TaskDto> taskFrameNo5,
			List<WorkLocationDto> workLocation, List<TaskSupInfoChoicesDetailDto> taskSupInfoChoicesDetails,
			List<ManHourRecordItemDto> manHourRecordItems, List<DailyAttendanceItemDto> attendanceItems,
			List<ManHourRecordAndAttendanceItemLinkDto> manHourRecordAndAttendanceItemLink) {
		super();
		this.taskFrameNo1 = taskFrameNo1;
		this.taskFrameNo2 = taskFrameNo2;
		this.taskFrameNo3 = taskFrameNo3;
		this.taskFrameNo4 = taskFrameNo4;
		this.taskFrameNo5 = taskFrameNo5;
		this.workLocation = workLocation;
		this.taskSupInfoChoicesDetails = taskSupInfoChoicesDetails;
		this.manHourRecordItems = manHourRecordItems;
	}

}

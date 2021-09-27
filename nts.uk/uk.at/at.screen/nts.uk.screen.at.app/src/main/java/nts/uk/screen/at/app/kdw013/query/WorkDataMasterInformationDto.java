package nts.uk.screen.at.app.kdw013.query;

import java.util.List;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.worklocation.WorkLocationDto;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordItem;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.screen.at.app.kdw013.a.TaskDto;

/**
 * @author thanhpv
 * @description output for 作業データマスタ情報を取得する
 */
@NoArgsConstructor
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

	public WorkDataMasterInformationDto(List<Task> taskFrameNo1, List<Task> taskFrameNo2,
			List<Task> taskFrameNo3, List<Task> taskFrameNo4, List<Task> taskFrameNo5,
			List<WorkLocation> workLocation, List<TaskSupInfoChoicesDetail> taskSupInfoChoicesDetails,
			List<ManHourRecordItem> manHourRecordItems) {
		super();
		this.taskFrameNo1 = taskFrameNo1.stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList());
		this.taskFrameNo2 = taskFrameNo2.stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList());
		this.taskFrameNo3 = taskFrameNo3.stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList());
		this.taskFrameNo4 = taskFrameNo4.stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList());
		this.taskFrameNo5 = taskFrameNo5.stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList());
		this.workLocation = workLocation.stream().map(c->WorkLocationDto.fromDomain(c)).collect(Collectors.toList());
		this.taskSupInfoChoicesDetails = taskSupInfoChoicesDetails.stream().map(c-> new TaskSupInfoChoicesDetailDto(c)).collect(Collectors.toList());
		this.manHourRecordItems = manHourRecordItems.stream().map(c-> new ManHourRecordItemDto(c)).collect(Collectors.toList());
	}
	
	
}

package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.screen.at.app.kdw013.c.StartWorkInputPanelResult;
import nts.uk.screen.at.app.kdw013.c.TaskDisplayInfoDto;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
public class StartWorkInputPanelDto {

	/** 利用可能作業1リスト */
	private List<TaskDto> taskListDto1;

	/** 利用可能作業2リスト */
	private List<TaskDto> taskListDto2;

	/** 利用可能作業3リスト */
	private List<TaskDto> taskListDto3;

	/** 利用可能作業4リスト */
	private List<TaskDto> taskListDto4;

	/** 利用可能作業5リスト */
	private List<TaskDto> taskListDto5;

	public static StartWorkInputPanelDto toDto(StartWorkInputPanelResult result) {

		return new StartWorkInputPanelDto(setTaskListDto(result.getTaskList1()), 
				setTaskListDto(result.getTaskList2()),
				setTaskListDto(result.getTaskList3()), 
				setTaskListDto(result.getTaskList4()),
				setTaskListDto(result.getTaskList5()));
	}

	public static List<TaskDto> setTaskListDto(List<Task> taskList) {
		return taskList.stream().map(m -> new TaskDto(m.getCode().v(), m.getTaskFrameNo().v(),
				new ExternalCooperationInfoDto(m.getCooperationInfo().getExternalCode1().orElse(null).toString(),
						m.getCooperationInfo().getExternalCode2().orElse(null).v(),
						m.getCooperationInfo().getExternalCode3().orElse(null).v(),
						m.getCooperationInfo().getExternalCode4().orElse(null).v(),
						m.getCooperationInfo().getExternalCode5().orElse(null).v()),
				m.getChildTaskList().stream().map(n -> n.v()).collect(Collectors.toList()),
				m.getExpirationDate().start(), m.getExpirationDate().end(),
				new TaskDisplayInfoDto(m.getDisplayInfo().getTaskName().v(), m.getDisplayInfo().getTaskAbName().v(),
						m.getDisplayInfo().getColor().orElse(null).v(),
						m.getDisplayInfo().getTaskNote().orElse(null).v())))
				.collect(Collectors.toList());
	}
}

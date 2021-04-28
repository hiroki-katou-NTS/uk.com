package nts.uk.screen.at.app.kdw013.a;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.worklocation.WorkLocationDto;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.初期起動処理
 * 
 * @author tutt
 *
 */
@Stateless
public class StartProcess {

	@Inject
	private StartManHourInput startManHourInput;

	@Inject
	private GetRefWorkplaceAndEmployee getRefWorkplaceAndEmployee;

	public StartProcessDto startProcess() {
		StartProcessDto startProcessDto = new StartProcessDto();

		// 1: <call>()
		StartManHourInputResultDto startManHourInputResultDto = new StartManHourInputResultDto();

		StartManHourInputDto startManHourInputDto = startManHourInput.startManHourInput();

		TaskFrameUsageSetting taskFrameUsageSetting = startManHourInputDto.getTaskFrameUsageSetting();
		List<Task> tasks = startManHourInputDto.getTasks();
		List<WorkLocation> workLocations = startManHourInputDto.getWorkLocations();

		// convert to DTO
		TaskFrameUsageSettingDto taskFrameUsageSettingDto = new TaskFrameUsageSettingDto(taskFrameUsageSetting
				.getFrameSettingList().stream().map(m -> TaskFrameSettingDto.toDto(m)).collect(Collectors.toList()));

		List<TaskDto> taskDtos = tasks.stream().map(m -> TaskDto.toDto(m)).collect(Collectors.toList());

		List<WorkLocationDto> lstWorkLocationDto = workLocations.stream().map(m -> WorkLocationDto.fromDomain(m))
				.collect(Collectors.toList());

		startManHourInputResultDto.setTaskFrameUsageSetting(taskFrameUsageSettingDto);
		startManHourInputResultDto.setTasks(taskDtos);
		startManHourInputResultDto.setWorkLocations(lstWorkLocationDto);

		startProcessDto.setStartManHourInputResultDto(startManHourInputResultDto);

		// 2: [画面モード = 確認モード]: <call>()
		GetRefWorkplaceAndEmployeeDto refWorkplaceAndEmployeeDto = getRefWorkplaceAndEmployee.get(GeneralDate.today());
		startProcessDto.setRefWorkplaceAndEmployeeDto(new GetRefWorkplaceAndEmployeeResultDto(
				refWorkplaceAndEmployeeDto.getEmployeeInfos(), 
				refWorkplaceAndEmployeeDto.getLstEmployeeInfo(),
				refWorkplaceAndEmployeeDto.getWorkplaceInfos().stream().map(m -> 
				new WorkplaceInfoDto(m.getHistoryId(), m.getWorkplaceCode().v(), m.getWorkplaceName().v()
						, m.getWkpGenericName().v(), m.getWkpDisplayName().v(), m.getOutsideWkpCode().v())
				).collect(Collectors.toList())));

		return startProcessDto;
	}

}

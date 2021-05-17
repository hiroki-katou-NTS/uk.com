package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.command.workrecord.workmanagement.AddWorkRecodConfirmationCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workmanagement.DeleteWorkResultConfirmCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workmanagement.DeleteWorkResultConfirmationCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.workrecord.AddAttendanceTimeZoneCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workrecord.RegisterWorkManHoursCommandHandler;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.screen.at.app.kdw013.a.AddWorkRecordConfirmation;
import nts.uk.screen.at.app.kdw013.a.ConfirmerDto;
import nts.uk.screen.at.app.kdw013.a.DeleteWorkRecordConfirmation;
import nts.uk.screen.at.app.kdw013.a.RegisterWorkContent;
import nts.uk.screen.at.app.kdw013.a.RegisterWorkContentCommand;
import nts.uk.screen.at.app.kdw013.a.RegisterWorkContentDto;
import nts.uk.screen.at.app.kdw013.a.StartProcess;
import nts.uk.screen.at.app.kdw013.a.StartProcessDto;
import nts.uk.screen.at.app.kdw013.a.TaskDto;
import nts.uk.screen.at.app.kdw013.c.SelectWorkItem;
import nts.uk.screen.at.app.kdw013.c.StartWorkInputPanel;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kdw013")
@Produces("application/json")
public class KDW013WebService {

	@Inject
	private DeleteWorkResultConfirmationCommandHandler deleteHandler;

	@Inject
	private DeleteWorkRecordConfirmation deleteWorkRecordConfirmation;

	@Inject
	private AddWorkRecordConfirmation addWorkRecordConfirmation;

	@Inject
	private StartWorkInputPanel startWorkInputPanel;

	@Inject
	private SelectWorkItem selectWorkItem;

	@Inject
	private ChangeDate changeDate;

	@Inject
	private SelectTargetEmployee selectTargetEmployee;

	@Inject
	private StartProcess startProcess;

	@Inject
	private RegisterWorkManHoursCommandHandler addHander;
	
	@Inject
	private RegisterWorkContent registerWorkContent;

	// 作業工数を登録する
	@POST
	@Path("register_work_man_hours")
	public List<IntegrationOfDailyDto> registerWorkTime(RegisterWorkManHoursCommand param) {
		AddAttendanceTimeZoneCommand command = new AddAttendanceTimeZoneCommand();

		command.setEmployeeId(param.getEmployeeId());
		command.setEditStateSetting(EnumAdaptor.valueOf(param.getEditStateSetting(), EditStateSetting.class));
		command.setWorkDetails(param.getWorkDetails().stream().map(m -> WorkDetailDto.toDomain(m)).collect(Collectors.toList()));

		List<IntegrationOfDailyDto> result = addHander.handle(command).stream()
				.map(m -> IntegrationOfDailyDto.toDto(m)).collect(Collectors.toList());

		return result;
	}
	
	
	// 初期起動処理
	@POST
	@Path("a/start")
	public StartProcessDto startProcess() {
		return startProcess.startProcess();
	}
	
	// 対象社員を選択する
	@POST
	@Path("a/select")
	public SelectTargetEmployeeDto selectTargetEmployee(SelectTargetEmployeeParam param) {
		return selectTargetEmployee.select(param);
	}

	// 日付を変更する
	@POST
	@Path("a/changeDate")
	public ChangeDateDto changeDate(ChangeDateParam param) {
		return changeDate.changeDate(param.getEmployeeId(), param.getRefDate(), param.getDisplayPeriod().toDomain());
	}

	// 作業実績を確認する
	@POST
	@Path("a/add")
	public List<ConfirmerDto> registerConfirmation(AddWorkRecodConfirmationCommand param) {
		return addWorkRecordConfirmation.add(param);
	}

	// 作業実績の確認を解除する
	@POST
	@Path("a/delete_work_result_confirm")
	public List<ConfirmerDto> deleteConfirmation(DeleteWorkResultConfirmCommand param) {
		return deleteWorkRecordConfirmation.delete(param);
	}

	// A:工数入力.メニュー別OCD
	// 作業内容を登録する
	@POST
	@Path("a/register_work_content")
	public RegisterWorkContentDto registerWorkContent(RegisterWorkContentCommand command) {
		return registerWorkContent.registerWorkContent(command);
	}

	// C:作業入力パネル.メニュー別OCD.作業入力パネルを起動する
	@POST
	@Path("c/start")
	public StartWorkInputPanelDto startWorkInputPanel(StartWorkInputPanelParam param) {
		WorkGroupDto workGrp = param.getWorkGroupDto();
		return StartWorkInputPanelDto.toDto(startWorkInputPanel.startPanel(param.getSId(), param.getRefDate(),
				WorkGroup.create(workGrp.getWorkCD1(), workGrp.getWorkCD2(), workGrp.getWorkCD3(), workGrp.getWorkCD4(),
						workGrp.getWorkCD5())));
	}

	// C:作業入力パネル.メニュー別OCD.作業項目を選択する
	@POST
	@Path("c/select")
	public List<TaskDto> selectWorkItem(SelectWorkItemParam param) {
		return StartWorkInputPanelDto.setTaskListDto(selectWorkItem.select(param.getSId(), param.getRefDate(),
				new TaskFrameNo(param.getTaskFrameNo()), Optional.of(new TaskCode(param.getTaskCode()))));
	}

}

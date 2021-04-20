package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.workrecord.workmanagement.AddWorkRecodConfirmationCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workmanagement.DeleteWorkResultConfirmationCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workmanagement.DeleteWorkResultConfirmationCommandHandler;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.screen.at.app.kdw013.a.AddWorkRecordConfirmation;
import nts.uk.screen.at.app.kdw013.a.ConfirmerDto;
import nts.uk.screen.at.app.kdw013.a.DeleteWorkRecordConfirmation;
import nts.uk.screen.at.app.kdw013.c.SelectWorkItem;
import nts.uk.screen.at.app.kdw013.c.StartWorkInputPanel;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kdp013")
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

	// 応援作業別勤怠時間帯を登録する.作業実績の確認を解除する
	@POST
	@Path("delete")
	public void delete(DeleteWorkResultConfirmationCommand param) {
		deleteHandler.handle(param);
	}

	// 作業内容を登録する

	// A:工数入力.メニュー別OCD.作業実績の確認を解除するL
	@POST
	@Path("a/delete")
	public List<ConfirmerDto> deleteConfirmation(DeleteWorkResultConfirmationCommand param) {
		return deleteWorkRecordConfirmation.delete(param);
	}

	// 作業実績を確認する
	@POST
	@Path("a/add")
	public List<ConfirmerDto> registerConfirmation(AddWorkRecodConfirmationCommand param) {
		return addWorkRecordConfirmation.add(param);
	}

	// 初期起動処理

	// 対象社員を選択する

	// C:作業入力パネル.メニュー別OCD.作業入力パネルを起動する
	@POST
	@Path("c/start")
	public StartWorkInputPanelDto startWorkInputPanel(StartWorkInputPanelParam param) {
		WorkGroupDto workGrp = param.getWorkGroupDto();
		return StartWorkInputPanelDto
				.toDto(startWorkInputPanel.startPanel(param.sId, param.refDate, WorkGroup.create(workGrp.getWorkCD1(),
						workGrp.getWorkCD2(), workGrp.getWorkCD3(), workGrp.getWorkCD4(), workGrp.getWorkCD5())));
	}

	// C:作業入力パネル.メニュー別OCD.作業項目を選択する
	@POST
	@Path("c/select")
	public List<TaskDto> selectWorkItem(selectWorkItemDto param) {
		return StartWorkInputPanelDto.setTaskListDto(selectWorkItem.select(param.sId, param.refDate,
				new TaskFrameNo(param.taskFrameNo), Optional.of(new TaskCode(param.taskCode))));
	}

}

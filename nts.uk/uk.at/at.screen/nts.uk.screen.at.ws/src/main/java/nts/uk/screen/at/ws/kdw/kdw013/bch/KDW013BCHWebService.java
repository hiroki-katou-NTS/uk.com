package nts.uk.screen.at.ws.kdw.kdw013.bch;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.screen.at.app.kdw013.a.IntegrationOfDailyCommand;
import nts.uk.screen.at.app.kdw013.a.ItemValueCommand;
import nts.uk.screen.at.app.kdw013.a.TaskDto;
import nts.uk.screen.at.app.kdw013.c.SelectWorkItem;
import nts.uk.screen.at.app.kdw013.h.CreateAchievementRegistrationParam;
import nts.uk.screen.at.app.kdw013.query.AttendanceItemMasterInformationDto;
import nts.uk.screen.at.app.kdw013.query.GetWorkDataMasterInformation;
import nts.uk.screen.at.app.kdw013.query.WorkDataMasterInformationDto;
import nts.uk.screen.at.ws.kdw.kdw013.SelectWorkItemParam;
import nts.uk.screen.at.ws.kdw.kdw013.StartWorkInputPanelDto;

/**
 * @author thanhpv
 *
 */
@Path("screen/at/kdw013")
@Produces(MediaType.APPLICATION_JSON)
public class KDW013BCHWebService {

	@Inject
	private GetWorkDataMasterInformation getWorkDataMasterInformation;

	@Inject
	private CreateAchievementRegistrationParam createAchievementRegistrationParam;

	@Inject
	private SelectWorkItem selectWorkItem;
	
	@Inject
	private ScWorkplaceAdapter workplaceAdapter;

	
	/**
	 * @<ScreenQuery>作業データマスタ情報を取得する
	 */
	@POST
	@Path("common/start")
	public WorkDataMasterInformationDto start(StartParamDto param) {
		return getWorkDataMasterInformation.get(param.refDate, param.itemIds);
	}

	
	 // C:作業入力パネル.メニュー別OCD.作業入力パネルを起動する
	 
//	@POST
//	@Path("c/start")
//	public StartWorkInputPanelDto startWorkInputPanel(StartWorkInputPanelParam param) {
//		WorkGroupDto workGrp = param.getWorkGroupDto();
//		return StartWorkInputPanelDto.toDto(startWorkInputPanel.startPanel(param.getEmployeeId(), param.getRefDate(),
//				WorkGroup.create(workGrp.getWorkCD1(), workGrp.getWorkCD2(), workGrp.getWorkCD3(), workGrp.getWorkCD4(),
//						workGrp.getWorkCD5())));
//	}
	 

	// C:作業入力パネル.メニュー別OCD.作業項目を選択する
	@POST
	@Path("c/select")
	public List<TaskDto> selectWorkItem(SelectWorkItemParam param) {
		return StartWorkInputPanelDto.setTaskListDto(selectWorkItem.select(param.getEmployeeId(), param.getRefDate(),
				new TaskFrameNo(param.getTaskFrameNo()), Optional.of(new TaskCode(param.getTaskCode()))));
	}
	
	@POST
	@Path("h/start")
	public AttendanceItemMasterInformationDto startH(ParamH param) {
		return getWorkDataMasterInformation.getAttendanceItemMasterInformation(param.itemIds);
	}
	
	@POST
	@Path("h/getWorkPlaceId")
	public WorkPlaceId getWorkPlaceId(EmployeeIdDate param) {
		return workplaceAdapter.findWorkplaceById(param.employeeId, param.date).map(c-> new WorkPlaceId(c.getWorkplaceId())).orElse(null);
	}

	@POST
	@Path("h/save")
	public void saveH(KDW013HSaveCommand command) {
		createAchievementRegistrationParam.registerAchievements(
				command.empTarget, 
				command.targetDate, 
				command.items.stream().map(c-> ItemValueCommand.toDomain(c)).collect(Collectors.toList()), 
				IntegrationOfDailyCommand.toDomain(command.integrationOfDaily));
	}
	
}
@NoArgsConstructor
@Data
class ParamH {
	//勤怠項目リスト => List<勤怠項目ID>
	public List<Integer> itemIds;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class WorkPlaceId {
	public String workPlaceId;
}

@NoArgsConstructor
@Data
class EmployeeIdDate {
	public String employeeId;
	public GeneralDate date;
}
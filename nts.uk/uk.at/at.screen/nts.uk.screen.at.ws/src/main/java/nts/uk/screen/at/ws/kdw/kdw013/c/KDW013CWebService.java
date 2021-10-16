package nts.uk.screen.at.ws.kdw.kdw013.c;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.screen.at.app.kdw013.a.TaskDto;
import nts.uk.screen.at.app.kdw013.c.SelectWorkItem;
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
public class KDW013CWebService {

	@Inject
	private GetWorkDataMasterInformation getWorkDataMasterInformation;

//	@Inject
//	private StartWorkInputPanel startWorkInputPanel;

	@Inject
	private SelectWorkItem selectWorkItem;

	
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
	@Path("select")
	public List<TaskDto> selectWorkItem(SelectWorkItemParam param) {
		return StartWorkInputPanelDto.setTaskListDto(selectWorkItem.select(param.getEmployeeId(), param.getRefDate(),
				new TaskFrameNo(param.getTaskFrameNo()), Optional.of(new TaskCode(param.getTaskCode()))));
	}
}

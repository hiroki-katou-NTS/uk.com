package nts.uk.screen.at.ws.kdw.kdw006;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.DeleteTheChoiceCommand;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.DeleteTheChoiceCommandHandler;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.RegisterNewOptionsCommand;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.RegisterNewOptionsCommandHandeler;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.UpdateAndRegisterOptionsCommandHandler;
import nts.uk.screen.at.app.kdw006.k.AcquireSelectionHistoryOfWork;
import nts.uk.screen.at.app.kdw006.k.AcquireSelectionHistoryOfWorkDto;
import nts.uk.screen.at.app.kdw006.k.GetManHourRecordItemSpecifiedIDListDto;
import nts.uk.screen.at.app.kdw006.k.GetManHourRecordItemSpecifiedIDListParam;
import nts.uk.screen.at.app.kdw006.k.GetWorkInforDetails;
import nts.uk.screen.at.app.kdw006.k.GetWorkInforDetailsDto;
import nts.uk.screen.at.app.kdw006.k.GetWorkInforDetailsInput;
import nts.uk.screen.at.app.kdw006.k.WorkInfomations;

/**
 * 
 * @author chungnt
 *
 */

@Path("at/record/kdw006/")
@Produces("application/json")
public class Kdw006WS extends WebService {

	@Inject
	private WorkInfomations workInfomations;

	@Inject
	private AcquireSelectionHistoryOfWork acquireSelectionHistoryOfWork;

	@Inject
	private GetWorkInforDetails getWorkInforDetails;

	@Inject
	private RegisterNewOptionsCommandHandeler registerNewOption;

	@Inject
	private UpdateAndRegisterOptionsCommandHandler updateOption;

	@Inject
	private DeleteTheChoiceCommandHandler delete;

	// 作業補足情報の選択項目を取得する
	@POST
	@Path("view-k/get-list-work")
	public List<GetManHourRecordItemSpecifiedIDListDto> getListWork(GetManHourRecordItemSpecifiedIDListParam param) {
		return this.workInfomations.get(param);
	}

	// 作業補足情報の選択肢履歴を取得する
	@POST
	@Path("view-k/get-list-history")
	public List<AcquireSelectionHistoryOfWorkDto> getListHistory() {
		return this.acquireSelectionHistoryOfWork.get();
	}

	// 作業補足情報の選択肢詳細を取得する
	@POST
	@Path("view-k/get-work-info-detail")
	public List<GetWorkInforDetailsDto> getWorkInforDetails(GetWorkInforDetailsInput param) {
		return this.getWorkInforDetails.getWorkInforDetails(param);
	}

	// 選択肢を新規登録する
	@POST
	@Path("view-k/register-new-obtion")
	public void register(RegisterNewOptionsCommand command) {
		this.registerNewOption.handle(command);
	}

	// 選択肢を更新登録する
	@POST
	@Path("view-k/update-obtion")
	public void update(RegisterNewOptionsCommand command) {
		this.updateOption.handle(command);
	}

	// 選択肢を削除する
	@POST
	@Path("view-k/delete")
	public void delete(DeleteTheChoiceCommand command) {
		this.delete.handle(command);
	}

}

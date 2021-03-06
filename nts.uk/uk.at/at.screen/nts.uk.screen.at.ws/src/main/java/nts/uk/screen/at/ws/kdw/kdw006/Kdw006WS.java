package nts.uk.screen.at.ws.kdw.kdw006;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.j.RegisterNewFormatSettingsCommand;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.j.RegisterNewFormatSettingsCommandHandler;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.DeleteTheChoiceCommand;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.DeleteTheChoiceCommandHandler;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.RegisterNewOptionsCommand;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.RegisterNewOptionsCommandHandeler;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.k.UpdateAndRegisterOptionsCommandHandler;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.l.AddHistoryCommand;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.l.AddHistoryCommandHander;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.l.DeleteHistoryCommand;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.l.DeleteHistoryCommandHandler;
import nts.uk.ctx.at.record.app.command.kdw.kdw006.l.UpdateHistoryCommandHandler;
import nts.uk.screen.at.app.kdw006.j.AcquireManHourRecordItems;
import nts.uk.screen.at.app.kdw006.j.AcquireManHourRecordItemsDto;
import nts.uk.screen.at.app.kdw006.j.GetDisplayFormat;
import nts.uk.screen.at.app.kdw006.j.GetDisplayFormatDto;
import nts.uk.screen.at.app.kdw006.j.GetScreenUsageDetails;
import nts.uk.screen.at.app.kdw006.j.GetScreenUsageDetailsDto;
import nts.uk.screen.at.app.kdw006.k.AcquireSelectionHistoryOfWork;
import nts.uk.screen.at.app.kdw006.k.AcquireSelectionHistoryOfWorkDto;
import nts.uk.screen.at.app.kdw006.k.GetManHourRecordItemSpecifiedIDListDto;
import nts.uk.screen.at.app.kdw006.k.GetManHourRecordItemSpecifiedIDListParam;
import nts.uk.screen.at.app.kdw006.k.GetWorkInforDetails;
import nts.uk.screen.at.app.kdw006.k.GetWorkInforDetailsDto;
import nts.uk.screen.at.app.kdw006.k.GetWorkInforDetailsInput;
import nts.uk.screen.at.app.kdw006.k.GetWorkInforDetailsbyListInput;
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

	@Inject
	private AddHistoryCommandHander addHistory;

	@Inject
	private UpdateHistoryCommandHandler updateHistory;

	@Inject
	private AcquireManHourRecordItems acquireManHourRecordItems;

	@Inject
	private GetDisplayFormat getDisplayFormat;

	@Inject
	private DeleteHistoryCommandHandler remoteHistory;

	@Inject
	private RegisterNewFormatSettingsCommandHandler registerOrUpdateSetting;

	@Inject
	private GetScreenUsageDetails getScreenUsageDetails;

	// ????????????????????????????????????????????????
	@POST
	@Path("view-k/get-list-work")
	public List<GetManHourRecordItemSpecifiedIDListDto> getListWork(GetManHourRecordItemSpecifiedIDListParam param) {
		return this.workInfomations.get(param);
	}

	// ???????????????????????????????????????????????????
	@POST
	@Path("view-k/get-list-history")
	public List<AcquireSelectionHistoryOfWorkDto> getListHistory() {
		return this.acquireSelectionHistoryOfWork.get();
	}

	// ???????????????????????????????????????????????????
	@POST
	@Path("view-k/get-work-info-detail")
	public List<GetWorkInforDetailsDto> getWorkInforDetails(GetWorkInforDetailsInput param) {
		return this.getWorkInforDetails.getWorkInforDetails(param);
	}

	// ???????????????????????????????????????????????????
	@POST
	@Path("view-k/get-list-work-info-detail")
	public List<GetWorkInforDetailsDto> getWorkInforDetailsByList(GetWorkInforDetailsbyListInput param) {
		return this.getWorkInforDetails.getWorkInforDetailsbyList(param);
	}

	// ??????????????????????????????
	@POST
	@Path("view-k/register-new-obtion")
	public void register(RegisterNewOptionsCommand command) {
		this.registerNewOption.handle(command);
	}

	// ??????????????????????????????
	@POST
	@Path("view-k/update-obtion")
	public void update(RegisterNewOptionsCommand command) {
		this.updateOption.handle(command);
	}

	// ????????????????????????
	@POST
	@Path("view-k/delete")
	public void delete(DeleteTheChoiceCommand command) {
		this.delete.handle(command);
	}

	// ?????????????????????
	@POST
	@Path("view-l/register-history")
	public HisIdDto addHistory(AddHistoryCommand param) {
		String hisId = IdentifierUtil.randomUniqueId();
		param.setHistoryId(hisId);
		this.addHistory.handle(param);
		return new HisIdDto(hisId);
	}

	// ?????????????????????
	@POST
	@Path("view-l/update-history")
	public void updateHistory(AddHistoryCommand param) {
		this.updateHistory.handle(param);
	}

	// ?????????????????????
	@POST
	@Path("view-l/remote-history")
	public void remoteHistory(DeleteHistoryCommand param) {
		this.remoteHistory.handle(param);
	}

	// Query: ?????????????????????????????????
	@POST
	@Path("view-j/get-man-hour-record-item")
	public List<AcquireManHourRecordItemsDto> getManHourRecordItems() {
		return this.acquireManHourRecordItems.get();
	}

	// Query: ?????????????????????????????????
	@POST
	@Path("view-j/get-display-format")
	public GetDisplayFormatDto getDisplayFormat() {
		return this.getDisplayFormat.get();
	}

	// Command: ????????????????????????????????????????????? / ?????????????????????????????????????????????
	@POST
	@Path("view-j/register-or-upate")
	public void registerOrUpdate(RegisterNewFormatSettingsCommand command) {
		this.registerOrUpdateSetting.handle(command);
	}

	// ScreenQuery: ?????????????????????????????????
	@POST
	@Path("view-j/get-screen-usage-details")
	public GetScreenUsageDetailsDto getScreenUsageDetails(RegisterNewFormatSettingsCommand command) {
		return this.getScreenUsageDetails.get();
	}
}

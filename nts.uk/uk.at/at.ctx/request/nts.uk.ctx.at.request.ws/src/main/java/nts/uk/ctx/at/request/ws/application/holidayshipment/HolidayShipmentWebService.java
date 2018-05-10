package nts.uk.ctx.at.request.ws.application.holidayshipment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.ApproveHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.CancelHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.ChangeAbsDateCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.DeleteHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.DenyHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.HolidayShipmentCommand;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.ReleaseHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.SaveHolidayShipmentCommand;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.SaveHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.UpdateHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentScreenBFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentScreenCFinder;
/*import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.ChangeWorkTypeDto;*/
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;

@Path("at/request/application/holidayshipment")
@Produces("application/json")
public class HolidayShipmentWebService extends WebService {

	@Inject
	private HolidayShipmentScreenAFinder aFinder;
	@Inject
	private HolidayShipmentScreenCFinder cFinder;
	@Inject
	private HolidayShipmentScreenBFinder bFinder;
	@Inject
	private SaveHolidayShipmentCommandHandler saveHandler;
	@Inject
	private UpdateHolidayShipmentCommandHandler updateHandler;
	@Inject
	private DeleteHolidayShipmentCommandHandler deleteHanler;
	@Inject
	private CancelHolidayShipmentCommandHandler cancelHanler;
	@Inject
	private ChangeAbsDateCommandHandler changeAbsHanler;
	@Inject
	private ApproveHolidayShipmentCommandHandler approveHandler;
	@Inject
	private DenyHolidayShipmentCommandHandler denyHandler;
	@Inject
	private ReleaseHolidayShipmentCommandHandler releaseHandler;
	@Inject
	private ChangeAbsDateCommandHandler changeHander;

	@POST
	@Path("start")
	public HolidayShipmentDto startPage(StartAParam param) {
		//return this.aFinder.startPage(param.getSID(), param.getAppDate(), param.getUiType());
		return null;
	}

	/*@POST
	@Path("change_work_type")
	public ChangeWorkTypeDto changeWorkType(ChangeWorkTypeParam param) {
		return this.aFinder.changeWorkType(param.getWkTypeCD(), param.getWkTimeCD());
	}*/

	@POST
	@Path("update")
	public void update(SaveHolidayShipmentCommand command) {
		updateHandler.handle(command);
	}

	/*@POST
	@Path("change_day")
	public HolidayShipmentDto changeDay(ChangeDateParam param) {
		return this.aFinder.changeDay(param.getTakingOutDate(), param.getHolidayDate(), param.getComType(),
				param.getUiType());
	}*/

	@POST
	@Path("save")
	public void save(SaveHolidayShipmentCommand command) {
		saveHandler.handle(command);
	}

	@POST
	@Path("find_by_id")
	public HolidayShipmentDto findByID(StartBParam param) {
		return this.bFinder.findByID(param.getAppID());
	}

	@POST
	@Path("remove")
	public void remove(HolidayShipmentCommand command) {
		this.deleteHanler.handle(command);
	}

	@POST
	@Path("cancel")
	public void cancel(HolidayShipmentCommand command) {
		this.cancelHanler.handle(command);
	}

	@POST
	@Path("approve")
	public void approve(HolidayShipmentCommand command) {
		this.approveHandler.handle(command);
	}

	@POST
	@Path("deny")
	public void deny(HolidayShipmentCommand command) {
		this.denyHandler.handle(command);
	}

	@POST
	@Path("release")
	public void release(HolidayShipmentCommand command) {
		this.releaseHandler.handle(command);
	}

	@POST
	@Path("start_c")
	public HolidayShipmentDto startPageC(StartAParam param) {
		return this.cFinder.startPage(param.getSID(), param.getAppDate(), param.getUiType());
	}

	@POST
	@Path("change_date_c")
	public void changeDateC(SaveHolidayShipmentCommand command) {
		this.changeHander.handle(command);
	}

	@POST
	@Path("change_abs_date")
	public void changeAbsDate(SaveHolidayShipmentCommand command) {
		this.changeAbsHanler.handle(command);
	}

}

@Value
class StartAParam {
	private String sID;
	private GeneralDate appDate;
	private int uiType;
}

@Value
class StartBParam {
	private String appID;
}

@Value
class ChangeWorkTypeParam {
	private String wkTypeCD;
	private String wkTimeCD;
}

@Value
class ChangeDateParam {

	private String holidayDate;

	private String takingOutDate;

	/**
	 * 申請組み合わせ
	 */
	private int comType;
	private int uiType;

	public GeneralDate getHolidayDate() {
		return holidayDate != null ? GeneralDate.fromString(holidayDate, "yyyy/MM/dd") : null;
	}

	public GeneralDate getTakingOutDate() {
		return takingOutDate != null ? GeneralDate.fromString(takingOutDate, "yyyy/MM/dd") : null;
	}

}

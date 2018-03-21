package nts.uk.ctx.at.request.ws.application.holidayshipment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.SaveHolidayShipmentCommand;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.SaveHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentScreenBFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.ChangeWorkTypeDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;

@Path("at/request/application/holidayshipment")
@Produces("application/json")
public class HolidayShipmentWebService extends WebService {

	@Inject
	private HolidayShipmentScreenAFinder aFinder;
	@Inject
	private HolidayShipmentScreenBFinder bFinder;
	@Inject
	private SaveHolidayShipmentCommandHandler handler;

	@POST
	@Path("start")
	public HolidayShipmentDto startPage(StartAParam param) {
		return this.aFinder.startPage(param.getSID(), param.getAppDate(), param.getUiType());
	}

	@POST
	@Path("change_work_type")
	public ChangeWorkTypeDto changeWorkType(ChangeWorkTypeParam param) {
		return this.aFinder.changeWorkType(param.getWorkTypeCD(), param.getWkTimeCD());
	}

	@POST
	@Path("update")
	public void update(SaveHolidayShipmentCommand command) {
		handler.handle(command);
	}

	@POST
	@Path("change_day")
	public HolidayShipmentDto changeDay(ChangeDateParam param) {
		return this.aFinder.changeDay(param.getTakingOutDate(), param.getHolidayDate(), param.getComType(),
				param.getUiType());
	}

	@POST
	@Path("save")
	public void save(SaveHolidayShipmentCommand command) {
		handler.handle(command);
	}

	@POST
	@Path("find_by_id")
	public HolidayShipmentDto findByID(StartBParam param) {
		return this.bFinder.findByID(param.getAppID());
	}

}

@Value
class StartAParam {
	private String sID;
	private String appDate;
	private int uiType;
}

@Value
class StartBParam {
	private String sID;
	private String appID;
	private int uiType;
}

@Value
class ChangeWorkTypeParam {
	private String workTypeCD;
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

}

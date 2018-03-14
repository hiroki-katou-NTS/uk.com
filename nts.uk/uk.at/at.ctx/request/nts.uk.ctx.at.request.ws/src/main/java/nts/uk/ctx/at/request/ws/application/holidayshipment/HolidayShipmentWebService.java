package nts.uk.ctx.at.request.ws.application.holidayshipment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;

@Path("at/request/application/holidayshipment")
@Produces("application/json")
public class HolidayShipmentWebService extends WebService {

	@Inject
	private HolidayShipmentFinder finder;

	@POST
	@Path("start")
	public HolidayShipmentDto startPage(StartParam param) {
		return this.finder.getHolidayShipment(param.getSID(), param.getAppDate(), param.getUiType());
	}

}

@Value
class StartParam {
	private String sID;
	private String appDate;
	private int uiType;
}

package nts.uk.smile.ws.smilelinked.webservice;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.DailyRecordOperationFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.DailyRecordOperationDto;
import nts.uk.smile.app.smilelinked.ocd.screencommand.RegisterSmileCooperationAcceptanceSettingScreenCommand;

@Path("at/record/RegisterSmileCooperationAcceptanceSettingWebService")
@Produces("application/json")
public class RegisterSmileCooperationAcceptanceSettingWebService {
	@Inject
	private RegisterSmileCooperationAcceptanceSettingScreenCommand screenQuery;

	@POST
	@Path("get")
	public void get() {
		this.screenQuery.get();
	}
}

package nts.uk.ctx.pereg.ws.specialhd;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.specialholiday.SpecialHDEventHandler;
import nts.uk.ctx.pereg.app.command.specialholiday.SpecialHolidayTestDto;

@Path("ctx/pereg/sc")
@Produces(MediaType.APPLICATION_JSON)
public class SpecialHolidayWebserivces extends WebService{

	@Inject
	private SpecialHDEventHandler handler;
	
	
	@Path("test")
	@POST
	public void getByCreateType(SpecialHolidayTestDto obj) {
		obj.setEffective(true);
		handler.handler(obj);
	}
}

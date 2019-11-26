package nts.uk.ctx.hr.develop.ws.test;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.command.JMM018Cmd;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto.JMM018Dto;

@Path("hrtest/datesetting")
@Produces(MediaType.APPLICATION_JSON)
public class DateSettingTestWebservice {

	@POST
	@Path("/ag/1")
	public JMM018Dto ag1() {
		return null;
		// return finder.finderJMM018();
	}

	@POST
	@Path("/ag/2")
	public void ag2(JMM018Cmd command) {
		// eventMenu.handle(command);
	}

	@POST
	@Path("/ag/3")
	public void ag3(JMM018Cmd command) {
		// eventMenu.handle(command);
	}

	@POST
	@Path("/ag/4")
	public void ag4(JMM018Cmd command) {
		// eventMenu.handle(command);
	}

	@POST
	@Path("/itf/1")
	public void itf1(JMM018Cmd command) {
		// eventMenu.handle(command);
	}

	@POST
	@Path("/itf/2")
	public void itf2(JMM018Cmd command) {
		// eventMenu.handle(command);
	}
}

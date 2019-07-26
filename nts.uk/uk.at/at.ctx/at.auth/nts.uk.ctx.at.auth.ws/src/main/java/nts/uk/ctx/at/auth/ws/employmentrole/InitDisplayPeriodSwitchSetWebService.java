package nts.uk.ctx.at.auth.ws.employmentrole;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.auth.app.find.employmentrole.InitDisplayPeriodSwitchSetFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.DateProcessedDto;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.DisplayPeriodDto;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.InitDisplayPeriodSwitchSetDto;

@Path("at/auth/workplace/initdisplayperiod")
@Produces(MediaType.APPLICATION_JSON)
public class InitDisplayPeriodSwitchSetWebService {
	@Inject
	private InitDisplayPeriodSwitchSetFinder initDisplayFinder;
	
	@POST
	@Path("get-request-list-609")
	public DisplayPeriodDto getRq609(){
		InitDisplayPeriodSwitchSetDto data = this.initDisplayFinder.targetDateFromLogin();
		return new DisplayPeriodDto(
				data.getCurrentOrNextMonth(), 
				data.getListDateProcessed().stream().map(x -> new DateProcessedDto(
							x.getClosureID(),
							x.getTargetDate().v(),
							x.getDatePeriod().start().toString(),
							x.getDatePeriod().end().toString()
						)).collect(Collectors.toList()));
	}
}

package nts.uk.ctx.at.record.ws.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMonDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMonFinder;

@Path("at/record/condition/monthlycheckcondition")
@Produces(MediaType.APPLICATION_JSON)
public class FixedExtraItemMonWs {
	
	@Inject
	private FixedExtraItemMonFinder finder;

	@POST
	@Path("getallfixitemmonthly")
	public List<FixedExtraItemMonDto> getAllFixedExtraItemMon(){
		List<FixedExtraItemMonDto> data = finder.getAllFixedExtraItemMon();
		return data;
	}
}

package nts.uk.ctx.at.record.ws.workrecord.erroralarm;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.FixedConditionDataDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.FixedConditionDataFinder;

@Path("at/record/erroralarm/fixeddata")
@Produces(MediaType.APPLICATION_JSON)
public class FixedConditionDataWS {

	@Inject
	private FixedConditionDataFinder finder;
	
	@POST
	@Path("getallfixedcondata")
	public List<FixedConditionDataDto> getAllFixedConditionData(){
		List<FixedConditionDataDto> data = finder.getAllFixedConditionData();
		return data;
	}
}

package nts.uk.ctx.at.function.ws.alarm.mastercheck;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.alarm.mastercheck.FixedMasterCheckFinder;
import nts.uk.ctx.at.function.app.find.alarm.mastercheck.MasterCheckFixedExtractItemDto;

@Path("at/function/alarm/mastercheck")
@Produces("application/json")
public class FixedMasterCheckWS extends WebService {
	
	@Inject
	FixedMasterCheckFinder finder;

	@POST
	@Path("getallfixedmastercheckitem")
	public List<MasterCheckFixedExtractItemDto> getAllFixedMasterCheckItem(){
		List<MasterCheckFixedExtractItemDto> data = finder.getAllFixedMasterCheckItem();
		return data;
	}
}

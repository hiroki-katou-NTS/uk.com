package nts.uk.ctx.at.record.ws.stamp;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.stamp.StampDto;
import nts.uk.ctx.at.record.app.find.stamp.StampFinder;
import nts.uk.ctx.at.record.app.find.stamp.StampDetailParamDto;

@Path("at/record/stamp")
@Produces("application/json")
public class StampReferenceWebService extends WebService {
	@Inject
	private StampFinder stampDeFinder;
	
	@POST
	@Path("getListStampDetail")
	public List<StampDto> getListStampDetail(StampDetailParamDto stampDto){
		List<StampDto> data = stampDeFinder.getListStampDetail(stampDto);
		return data;
	}
}

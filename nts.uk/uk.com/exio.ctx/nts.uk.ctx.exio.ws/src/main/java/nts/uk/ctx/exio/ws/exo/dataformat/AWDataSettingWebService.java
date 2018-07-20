package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.awdataformatsetting.AWDataScreenService;
import nts.uk.ctx.exio.app.find.exo.awdataformatsetting.AwDataFormatDTO;

@Path("exio/exo/aw")
@Produces("application/json")
public class AWDataSettingWebService {

	@Inject
	AWDataScreenService aWDataScreenFinder;
	
	@POST
	@Path("getdatatype")
	public AwDataFormatDTO getAWDatatype(){
		return aWDataScreenFinder.getAWData();
	}
}

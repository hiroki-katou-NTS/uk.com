package nts.uk.ctx.exio.ws.exo.condset.datafomat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.awdataformatsetting.AWDataScreenFinder;
import nts.uk.ctx.exio.app.find.exo.awdataformatsetting.AWOutputTypeSettingDTO;
import nts.uk.ctx.exio.app.find.exo.awdataformatsetting.AwDataFormatDTO;

@Path("exio/exo/aw")
@Produces("application/json")
public class AWDataSettingWebService {

	@Inject
	AWDataScreenFinder aWDataScreenFinder;
	
	@POST
	@Path("getdatatype")
	public AwDataFormatDTO getAWDatatype(AWOutputTypeSettingDTO command){
		return aWDataScreenFinder.getAWData(command);
	}
}

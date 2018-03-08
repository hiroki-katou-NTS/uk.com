package nts.uk.ctx.exio.ws.exi.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exi.dataformat.DateDataFormSetDto;
//import nts.uk.ctx.exio.app.find.exi.dataformat.DateDataFormSetFinder;

@Path("exio/exi/dataformat")
@Produces("application/json")
public class DateDataFormSetWebService {
/*	@Inject
	private DateDataFormSetFinder dateDataFormSetFind;

	@POST
	@Path("getDateDataFormatSetting/{conditionSetCd}/{acceptItemNum}")
	public DateDataFormSetDto getDateDataFormatSetting(@PathParam("conditionSetCd") String conditionSetCd,
			@PathParam("acceptItemNum") int acceptItemNum) {
		return this.dateDataFormSetFind.getDateDataFormSetById(conditionSetCd, acceptItemNum);
	}*/
}

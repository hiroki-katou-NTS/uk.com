package nts.uk.screen.at.ws.knr.knr002.k;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.k.GetBentoMenu;
import nts.uk.screen.at.app.query.knr.knr002.k.GetBentoMenuDto;

/**
*
* @author xuannt
*
*/
@Path("screen/at/bentomenutransfer")
@Produces(MediaType.APPLICATION_JSON)
public class GetBentoMenuScreenWS {
	@Inject
	private GetBentoMenu getBentoMenu;
	@POST
	@Path("getbentomenu/{empInfoTerCode}")
	public GetBentoMenuDto getBentoMenu(@PathParam("empInfoTerCode") String empInforTerCode) {
		return this.getBentoMenu.getBentoMenu(empInforTerCode);
	}
}

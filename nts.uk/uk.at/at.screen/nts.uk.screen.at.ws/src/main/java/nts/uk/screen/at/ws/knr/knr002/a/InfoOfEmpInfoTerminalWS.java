package nts.uk.screen.at.ws.knr.knr002.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.knr.knr002.a.GetListInfoOfEmpInfoTerminal;
import nts.uk.screen.at.app.query.knr.knr002.a.InfoOfEmpInfoTerminalDto;


/**
 * 
 * @author dungbn
 *
 */
@Path("screen/at/infoempinfoterminal")
@Produces(MediaType.APPLICATION_JSON)
public class InfoOfEmpInfoTerminalWS extends WebService {

	@Inject
	private GetListInfoOfEmpInfoTerminal getListInfoOfEmpInfoTerminal;
	
	@POST
	@Path("getlistinfoempterminal")
	public InfoOfEmpInfoTerminalDto getListInfoOfEmpInfoTerminal() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		return getListInfoOfEmpInfoTerminal.getInfoOfEmpInfoTerminal();
	}
}

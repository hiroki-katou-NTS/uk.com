package nts.uk.screen.at.ws.knr.knr002.f;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.f.ExtractRecoveryTargetTerminal;
import nts.uk.screen.at.app.query.knr.knr002.f.ExtractRecoveryTargetTerminalDto;

/**
 * 
 * @author xuannt
 *
 */

@Path("screen/at/recoverytargetter")
@Produces(MediaType.APPLICATION_JSON)
public class RecoveryTargetTerScreenWS {
	@Inject
	private ExtractRecoveryTargetTerminal extractRecoveryTargetTerminal;
	
	@POST
	@Path("getAll/{modelEmpInfoTer}")
	public List<ExtractRecoveryTargetTerminalDto> getRecoveryTargetList(@PathParam("modelEmpInfoTer") int modelEmpInfoTer) {
		return this.extractRecoveryTargetTerminal.getRecoveryTargetList(modelEmpInfoTer);
	}
}

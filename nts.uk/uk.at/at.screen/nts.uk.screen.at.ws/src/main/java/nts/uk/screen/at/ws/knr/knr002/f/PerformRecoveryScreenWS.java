package nts.uk.screen.at.ws.knr.knr002.f;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.f.PerformRecovery;
import nts.uk.screen.at.app.query.knr.knr002.f.PerformRecoveryDto;

/**
 * 
 * @author xuannt
 *
 */
@Path("screen/at/performrecovery")
@Produces(MediaType.APPLICATION_JSON)
public class PerformRecoveryScreenWS {
	@Inject
	PerformRecovery performRecovery;
	
	@POST
	@Path("recovery")
	public PerformRecoveryDto recovery(PerformRecoveryDto dto) {		
		return this.performRecovery.recovery(dto.getEmpInfoTerCode(), dto.getTerminalCodeList());
	}
}
package nts.uk.screen.at.ws.knr.knr002.b;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.b.AcquisEmpInfoLog;
import nts.uk.screen.at.app.query.knr.knr002.b.AcquisEmpInfoLogDto;

/**
 * 
 * @author xuannt
 *
 */
@Path("screen/at/monitoringempinfoterminal")
@Produces(MediaType.APPLICATION_JSON)
public class MonitoringEmpInfoTerScreenWS {
	@Inject
	private AcquisEmpInfoLog acquisitionEmpInfoLogList;

	@POST
	@Path("getInPeriod")
	public List<AcquisEmpInfoLogDto> getInPeriod(AcquisEmpInfoLogDto dto) {
		
		return this.acquisitionEmpInfoLogList.getInPeriod(dto.getEmpInfoTerCode(), dto.getETime(), dto.getETime());
	}
}

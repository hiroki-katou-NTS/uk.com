package nts.uk.shr.infra.diagnose;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("diagnosis")
@Produces("application/json")
public class DiagnosisWebService {

	@POST
	@Path("setting/get")
	public DiagnosisSetting get() {
		return DiagnosisSetting.currentSetting();
	}
	
	@POST
	@Path("setting/update")
	public void update(DiagnosisSetting setting) {
		setting.apply();
	}
}

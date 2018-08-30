package nts.uk.ctx.exio.ws.monsalabonus.laborinsur;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.monsalabonus.laborinsur.EmpInsurHisDto;
import nts.uk.ctx.exio.app.find.monsalabonus.laborinsur.EmpInsurHisFinder;
import nts.uk.ctx.exio.app.find.monsalabonus.laborinsur.EmpInsurPreRateDto;
import nts.uk.ctx.exio.app.find.monsalabonus.laborinsur.EmpInsurPreRateFinder;


@Path("exio/monsalabonus/laborinsur")
@Produces("application/json")
public class EmpInsurWebService extends WebService {

	@Inject
	private EmpInsurPreRateFinder empInsurPreRateFinder;
	
	@Inject
	private EmpInsurHisFinder empInsurHisFinder;
	
	@POST
	@Path("getEmpInsurPreRate/{hisId}")
	public List<EmpInsurPreRateDto> getEmpInsurPreRate(@PathParam("hisId") String hisId) {
		return empInsurPreRateFinder.getListEmplInsurPreRate(hisId);
	}
	
	@POST
	@Path("getEmpInsurHis")
	public List<EmpInsurHisDto> getEmpInsurHis() {
		return empInsurHisFinder.getListEmplInsurHis();
	}

}

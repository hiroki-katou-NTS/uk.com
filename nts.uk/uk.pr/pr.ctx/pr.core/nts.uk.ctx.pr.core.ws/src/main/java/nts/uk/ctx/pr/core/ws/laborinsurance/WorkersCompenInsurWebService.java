package nts.uk.ctx.pr.core.ws.laborinsurance;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.monsalabonus.laborinsur.*;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurHis;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsHis;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRate;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.WorkersComInsurService;
import nts.uk.shr.com.context.AppContexts;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;


@Path("exio/monsalabonus/laborinsur")
@Produces("application/json")
public class WorkersCompenInsurWebService extends WebService {

    @Inject
    private WorkersComInsurService workersComInsurService;
    @Inject
    private OccAccIsPrRateFinder occAccIsPrRateFinder;
    @POST
    @Path("getListOccAccIsHis")
    public List<OccAccIsHis> getListOccAccIsHis() {
        String companyId = AppContexts.user().companyId();
        return workersComInsurService.initDataAcquisition(companyId);
    }
    @POST
    @Path("getOccAccIsPrRate/{hisId}")
    public OccAccIsPrRateDto getOccAccIsPrRate(@PathParam("hisId") String hisId) {
        return occAccIsPrRateFinder.getAllOccAccIsPrRate(hisId);
    }

}

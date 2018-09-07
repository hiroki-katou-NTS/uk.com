package nts.uk.ctx.pr.core.ws.laborinsurance;

import nts.arc.layer.ws.WebService;

import nts.uk.ctx.pr.core.app.find.laborinsurance.OccAccIsPrRateDto;
import nts.uk.ctx.pr.core.app.find.laborinsurance.OccAccIsPrRateFinder;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.WorkersComInsurService;
import nts.uk.shr.com.context.AppContexts;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Optional;


@Path("exio/monsalabonus/laborinsur")
@Produces("application/json")
public class WorkersCompenInsurWebService extends WebService {

    @Inject
    private WorkersComInsurService workersComInsurService;
    @Inject
    private OccAccIsPrRateFinder occAccIsPrRateFinder;
    @POST
    @Path("getListOccAccIsHis")
    public Optional<OccAccIsHis> getListOccAccIsHis() {
        String companyId = AppContexts.user().companyId();
        return workersComInsurService.initDataAcquisition(companyId);
    }
    @POST
    @Path("getOccAccIsPrRate/{hisId}")
    public OccAccIsPrRateDto getOccAccIsPrRate(@PathParam("hisId") String hisId) {
        return occAccIsPrRateFinder.getAllOccAccIsPrRate(hisId);
    }
    @POST
    @Path("getOccAccIsPrRate/{hisId}")
    public OccAccIsPrRateDto addOccAccIsHis(@PathParam("hisId") String hisId) {
        return occAccIsPrRateFinder.getAllOccAccIsPrRate(hisId);
    }

}

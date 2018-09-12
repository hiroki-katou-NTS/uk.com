package nts.uk.ctx.pr.core.ws.laborinsurance;

import nts.arc.layer.ws.WebService;

import nts.uk.ctx.pr.core.app.find.laborinsurance.*;


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
    private OccAccIsHisFinder occAccIsHisFinder ;
    @Inject
    private OccAccIsPrRateFinder occAccIsPrRateFinder;

    @POST
    @Path("getListOccAccIsHis")
    public  List<OccAccIsHisDto> getListOccAccIsHis() {
       return occAccIsHisFinder.getListEmplInsurHis();
    }

    @POST
    @Path("getOccAccIsPrRate/{hisId}")
    public List<OccAccIsPrRateDto> getOccAccIsPrRate(@PathParam("hisId") String hisId) {
        List<OccAccIsPrRateDto> a = occAccIsPrRateFinder.getAllOccAccIsPrRate(hisId);
        System.out.println(a);
        return occAccIsPrRateFinder.getAllOccAccIsPrRate(hisId);
    }

    @POST
    @Path("getOccAccInsurBus")
    public  List<OccAccInsurBusDto> getOccAccInsurBus() {
        return occAccIsHisFinder.getOccAccInsurBus();
    }



}

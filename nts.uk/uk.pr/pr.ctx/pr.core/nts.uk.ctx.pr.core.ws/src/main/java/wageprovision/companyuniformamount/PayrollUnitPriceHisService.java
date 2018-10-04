package wageprovision.companyuniformamount;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryFinder;


@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceHisService extends WebService {

    @Inject
    private PayrollUnitPriceHistoryFinder payrollUnitPriceHistoryFinder;


    @POST
    @Path("getPayrollUnitPriceHis/{hisId}")
    public PayrollUnitPriceHistoryDto getPayrollUnitPriceHis(@PathParam("hisId") String hisId) {
        return payrollUnitPriceHistoryFinder.getPayrollUnitPriceHis(hisId);
    }

    @POST
    @Path("getPayUnitPriceHist/{hisId}")
    public PayrollUnitPriceHistoryDto getPayUnitPriceHist(@PathParam("hisId") String hisId) {
        return payrollUnitPriceHistoryFinder.getPayrollUnitPriceHis(hisId);
    }


//
//    @POST
//    @Path("registerOccAccInsur")
//    public void registerOccAccInsurPreRate(AddOccAccIsPrRateCommand command){
//        addOccAccIsPrRateCommandHandler.handle(command);
//    }



}

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
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHisKey;


@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceHisService extends WebService {

    @Inject
    private PayrollUnitPriceHistoryFinder payrollUnitPriceHistoryFinder;



    @POST
    @Path("getPayrollUnitPriceHis")
    public PayrollUnitPriceHistoryDto getPayrollUnitPriceHis(PayrollUnitPriceHisKey mPayrollUnitPriceHisKey) {
        return payrollUnitPriceHistoryFinder.getPayrollUnitPriceHis(mPayrollUnitPriceHisKey.getHisId(),mPayrollUnitPriceHisKey.getCode());
    }
    @POST
    @Path("updatePayrollUnitPriceHis/{hisId}")
    public PayrollUnitPriceHistoryDto updatePayrollUnitPriceHis(@PathParam("hisId") String hisId) {
        return null;
    }
    @POST
    @Path("updatePayrollUnitPriceHis/{hisId}")
    public PayrollUnitPriceHistoryDto deletePayrollUnitPriceHis(@PathParam("hisId") String hisId) {
        return null;
    }



//
//    @POST
//    @Path("registerOccAccInsur")
//    public void registerOccAccInsurPreRate(AddOccAccIsPrRateCommand command){
//        addOccAccIsPrRateCommandHandler.handle(command);
//    }



}

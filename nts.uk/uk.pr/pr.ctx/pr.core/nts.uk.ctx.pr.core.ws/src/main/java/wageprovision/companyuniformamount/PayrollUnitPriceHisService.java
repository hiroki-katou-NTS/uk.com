package wageprovision.companyuniformamount;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.AddPayrollUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.PayrollUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHisKey;

import java.util.List;


@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceHisService extends WebService {

    @Inject
    private PayrollUnitPriceHistoryFinder payrollUnitPriceHistoryFinder;

    @Inject
    private AddPayrollUnitPriceHistoryCommandHandler addPayrollUnitPriceHistoryCommandHandler;



    @POST
    @Path("getPayrollUnitPriceHis")
    public List<PayrollUnitPriceHistoryDto> getPayrollUnitPriceHis(PayrollUnitPriceHisKey mPayrollUnitPriceHisKey) {
        return payrollUnitPriceHistoryFinder.getPayrollUnitPriceHis(mPayrollUnitPriceHisKey.getHisId(),mPayrollUnitPriceHisKey.getCode());
    }
    @POST
    @Path("submitPayrollUnitPriceHis")
    public void updatePayrollUnitPriceHis(PayrollUnitPriceHistoryCommand payrollUnitPriceHistoryCommand) {
        addPayrollUnitPriceHistoryCommandHandler.handle(payrollUnitPriceHistoryCommand);
    }




//
//    @POST
//    @Path("registerOccAccInsur")
//    public void registerOccAccInsurPreRate(AddOccAccIsPrRateCommand command){
//        addOccAccIsPrRateCommandHandler.handle(command);
//    }



}

package wageprovision.companyuniformamount;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.AddPayrollUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.PayrollUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryFinder;
import nts.uk.shr.com.context.AppContexts;



@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceHisWebService extends WebService {

    @Inject
    private PayrollUnitPriceHistoryFinder payrollUnitPriceHistoryFinder;

    @Inject
    private AddPayrollUnitPriceHistoryCommandHandler addPayrollUnitPriceHistoryCommandHandler;

    @POST
    @Path("getAllPayrollUnitPriceHistoryByCidAndCode/{code}")
    public List<PayrollUnitPriceHistoryDto> getAllPayrollUnitPriceHistoryByCidAndCode(@PathParam("code") String code) {
        String cid = AppContexts.user().companyId();
        return payrollUnitPriceHistoryFinder.getAllPayrollUnitPriceHistoryByCidAndCode(cid,code);
    }

    @POST
    @Path("addPayrollUnitPriceHis")
    public String addPayrollUnitPriceHis(PayrollUnitPriceHistoryCommand command) {
        return addPayrollUnitPriceHistoryCommandHandler.handle(command);
    }

}

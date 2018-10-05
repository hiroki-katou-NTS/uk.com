package wageprovision.companyuniformamount;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.PayrollUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.UpdatePayrollUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHisKey;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryFinder;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;


@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceHisWebService extends WebService {

    @Inject
    private PayrollUnitPriceHistoryFinder payrollUnitPriceHistoryFinder;


    @Inject
    private UpdatePayrollUnitPriceHistoryCommandHandler updatePayrollUnitPriceHistoryCommandHandler;



    @POST
    @Path("getPayrollUnitPriceHis")
    public List<PayrollUnitPriceHistoryDto> getPayrollUnitPriceHis(PayrollUnitPriceHisKey mPayrollUnitPriceHisKey) {
        return payrollUnitPriceHistoryFinder.getPayrollUnitPriceHis(mPayrollUnitPriceHisKey.getHisId(),mPayrollUnitPriceHisKey.getCode());
    }
    @POST
    @Path("submitPayrollUnitPriceHis")
    public void updatePayrollUnitPriceHis(PayrollUnitPriceHistoryCommand payrollUnitPriceHistoryCommand) {
        updatePayrollUnitPriceHistoryCommandHandler.handle(payrollUnitPriceHistoryCommand);
    }
    @POST
    @Path("getAllPayrollUnitPriceHistoryByCidAndCode/{code}")
    public List<PayrollUnitPriceHistoryDto> getAllPayrollUnitPriceHistoryByCidAndCode(@PathParam("code") String code) {
        String cid = AppContexts.user().companyId();
        return payrollUnitPriceHistoryFinder.getAllPayrollUnitPriceHistoryByCidAndCode(cid,code);
    }

}

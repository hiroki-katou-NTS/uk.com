package wageprovision.companyuniformamount;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceFinder;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceWebService extends WebService {

    @Inject
    PayrollUnitPriceFinder payrollUnitPriceFinder;

    @POST
    @Path("getAllPayrollUnitPriceByCID")
    public List<PayrollUnitPriceDto> getAllPayrollUnitPriceByCID(){
        String cid = AppContexts.user().companyId();
        return payrollUnitPriceFinder.getAllPayrollUnitPriceByCID(cid);
    }
}

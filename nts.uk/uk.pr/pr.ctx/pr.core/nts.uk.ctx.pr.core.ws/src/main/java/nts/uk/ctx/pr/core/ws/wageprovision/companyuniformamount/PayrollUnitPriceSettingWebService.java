package nts.uk.ctx.pr.core.ws.wageprovision.companyuniformamount;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceSettingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceSettingFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Optional;

@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceSettingWebService extends WebService {

    @Inject
    PayrollUnitPriceSettingFinder payrollUnitPriceSettingFinder;

    @POST
    @Path("getPayrollUnitPriceSettingById/{hisId}")
    public PayrollUnitPriceSettingDto getpayrollUnitPriceSettingById(@PathParam("hisId") String hisId){
        Optional<PayrollUnitPriceSettingDto> payrollUnitPriceSettingDto = payrollUnitPriceSettingFinder.getpayrollUnitPriceSettingById(hisId);
        if(payrollUnitPriceSettingDto.isPresent()){
            return payrollUnitPriceSettingDto.get();
        }
        return null;
    }
}

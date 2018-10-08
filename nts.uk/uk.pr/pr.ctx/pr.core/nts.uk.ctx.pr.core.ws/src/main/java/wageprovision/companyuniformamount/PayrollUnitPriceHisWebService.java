package wageprovision.companyuniformamount;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;

import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceFinder;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.PayrollUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.UpdatePayrollUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHisKey;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryFinder;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Optional;


@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceHisWebService extends WebService {

    @Inject
    private PayrollUnitPriceHistoryFinder payrollUnitPriceHistoryFinder;

    @Inject
    private PayrollUnitPriceFinder payrollUnitPriceFinder;

    @Inject
    private AddPayrollUnitPriceHistoryCommandHandler addPayrollUnitPriceHistoryCommandHandler;

    @Inject
    private AddPayrollUnitPriceCommandHandler addPayrollUnitPriceCommandHandler;

    @Inject
    private RegisterPayrollUnitPriceSettingCommandHandler registerPayrollUnitPriceSettingCommandHandler;


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
    @Path("getPayrollUnitPriceHistoryByCidCode/{code}")
    public List<PayrollUnitPriceHistoryDto> getPayrollUnitPriceHistoryByCidCode(@PathParam("code") String code) {
        String cid = AppContexts.user().companyId();
        return payrollUnitPriceHistoryFinder.getPayrollUnitPriceHistoryByCidCode(cid,code);
    }

    @POST
    @Path("getPayrollUnitPriceHistoryByCidCode/{hisId}/{code}")
    public List<PayrollUnitPriceHistoryDto> getPayrollUnitPriceHistoryById(@PathParam("hisId") String hisId, @PathParam("code") String code) {
        return payrollUnitPriceHistoryFinder.getPayrollUnitPriceHis(hisId,code);
    }

    @POST
    @Path("register")
    public void register(RegisterPayrollUnitPriceSettingCommand command) {
        registerPayrollUnitPriceSettingCommandHandler.handle(command);
    }

    @POST
    @Path("getPayrollUnitPriceHisById/{code}/{hisId}")
    public PayrollUnitPriceHistoryDto getPayrollUnitPriceHisById(@PathParam("code") String code, @PathParam("hisId") String hisId){
        String cid = AppContexts.user().companyId();
        Optional<PayrollUnitPriceHistoryDto> payrollUnitPriceHistoryDto = payrollUnitPriceHistoryFinder.getPayrollUnitPriceHistoryById(cid,code,hisId);
        if(payrollUnitPriceHistoryDto.isPresent()){
            return payrollUnitPriceHistoryDto.get();
        }
        return null;
    }
}

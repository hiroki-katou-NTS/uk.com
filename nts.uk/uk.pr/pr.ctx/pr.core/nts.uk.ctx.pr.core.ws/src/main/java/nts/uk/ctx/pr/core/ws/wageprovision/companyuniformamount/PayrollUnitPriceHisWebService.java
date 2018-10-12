package nts.uk.ctx.pr.core.ws.wageprovision.companyuniformamount;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;

import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.*;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.PayrollUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount.UpdatePayrollUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHisKey;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistorySettingDto;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;


@Path("core/wageprovision/companyuniformamount")
@Produces("application/json")
public class PayrollUnitPriceHisWebService extends WebService {

    @Inject
    private PayrollUnitPriceHistoryFinder payrollUnitPriceHistoryFinder;

    @Inject
    private AddPayrollUnitPriceHistoryCommandHandler addPayrollUnitPriceHistoryCommandHandler;


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

    /*@POST
    @Path("getPayrollUnitPriceHisById/{code}/{hisId}")
    public PayrollUnitPriceHistoryDto getPayrollUnitPriceHisById(@PathParam("code") String code, @PathParam("hisId") String hisId){
        String cid = AppContexts.user().companyId();
        Optional<PayrollUnitPriceHistoryDto> payrollUnitPriceHistoryDto = payrollUnitPriceHistoryFinder.getPayrollUnitPriceHistoryById(cid,code,hisId);
        if(payrollUnitPriceHistoryDto.isPresent()){
            return payrollUnitPriceHistoryDto.get();
        }
        return null;
    }*/

    @POST
    @Path("getPayrollUnitPriceHisById/{code}/{hisId}")
    public PayrollUnitPriceHistorySettingDto getPayrollUnitPriceHisById(@PathParam("code") String code, @PathParam("hisId") String hisId){
        String cid = AppContexts.user().companyId();
        Object[] object = payrollUnitPriceHistoryFinder.getPayrollUnitPriceHistory(cid,code,hisId);
        PayrollUnitPriceHistorySettingDto payrollUnitPriceHistorySettingDto = null;
        if(object != null || object.length > 0){
            YearMonthHistoryItem item = (YearMonthHistoryItem)object[0];
            PayrollUnitPriceSetting payrollUnitPriceSet = (PayrollUnitPriceSetting)object[1];
            payrollUnitPriceHistorySettingDto = new PayrollUnitPriceHistorySettingDto(
                    code,hisId,item.start().v(),
                    item.end().v(),
                    payrollUnitPriceSet.getAmountOfMoney().v(),
                    payrollUnitPriceSet.getFixedWage().getFlatAllEmployees().isPresent() ? payrollUnitPriceSet.getFixedWage().getFlatAllEmployees().get().getTargetClass().value : null,
                    payrollUnitPriceSet.getFixedWage().getPerSalaryConType().isPresent() ? payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getMonthSalaryPerDay().value : null,
                    payrollUnitPriceSet.getFixedWage().getPerSalaryConType().isPresent() ? payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getADayPayee().value : null,
                    payrollUnitPriceSet.getFixedWage().getPerSalaryConType().isPresent() ? payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getHourlyPay().value : null,
                    payrollUnitPriceSet.getFixedWage().getPerSalaryConType().isPresent() ? payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getMonthlySalary().value : null,
                    payrollUnitPriceSet.getFixedWage().getSetClassification().value,
                    payrollUnitPriceSet.getNotes().isPresent() ? payrollUnitPriceSet.getNotes().get().v() : null);
        }

        return payrollUnitPriceHistorySettingDto;
    }

    @POST
    @Path("addPayrollUnitPriceHis")
    public Object addPayrollUnitPriceHis(PayrollUnitPriceHistoryCommand command) {
        String result = addPayrollUnitPriceHistoryCommandHandler.handle(command);
        return new Object[]{result};
    }

    @POST
    @Path("getAllHistoryById")
    public Object getAllHistoryById(PayrollUnitPriceHistoryCommand command) {
        String cid  = AppContexts.user().companyId();
        return payrollUnitPriceHistoryFinder.getAllHistoryById(cid);
    }
}

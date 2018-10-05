package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.AddPaymentDateSettingCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.PaymentDateSettingListCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.UpdatePaymentDateSettingCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("ctx/pr/core/wageprovision/processdatecls")
@Produces(MediaType.APPLICATION_JSON)
public class SetPaymentDateWebService extends WebService {

    @Inject
    ProcessInformationFinder processInformationFinder;

    @Inject
    SetDaySupportFinder setDaySupportFinder;

    @Inject
    SelectProcessingYearFinder selectProcessingYearFinder;

    @Inject
    ValPayDateSetFinder valPayDateSetFinder;

    @Inject
    AddPaymentDateSettingCommandHandler addPaymentDateSettingCommandHandler;

    @Inject
    UpdatePaymentDateSettingCommandHandler updatePaymentDateSettingCommandHandler;

    @POST
    @Path("/getProcessInfomation/{processCateNo}")
    public ProcessInformationDto getProcessInfomation(
            @PathParam("processCateNo") int processCateNo) {
        return processInformationFinder.getProcessInformation(processCateNo);
    }

    @POST
    @Path("/getSetDaySupport/{processCateNo}")
    public List<SetDaySupportDto> getSetDaySupport(
            @PathParam("processCateNo") int processCateNo) {
        return setDaySupportFinder.getListByCateNoAndCid(processCateNo);
    }

    @POST
    @Path("/getSelectProcessingYear/{processCateNo}/{year}")
    public SelectProcessingYearDto getSelectProcessingYear(
            @PathParam("processCateNo") int processCateNo, @PathParam("year") int year) {
        return selectProcessingYearFinder.getByCateNoAndCid(processCateNo, year);
    }


    @POST
    @Path("/getValPayDateSet/{processCateNo}")
    public ValPayDateSetDto getValPayDateSet(
            @PathParam("processCateNo") int processCateNo) {
        return valPayDateSetFinder.getValPayDateSet(processCateNo);
    }


    @POST
    @Path("/addDomainModel")
    public void addDomainModel(PaymentDateSettingListCommand command) {
        addPaymentDateSettingCommandHandler.handle(command);
    }

    @POST
    @Path("/updateDomainModel")
    public void updateDomainModel(PaymentDateSettingListCommand command) {
        updatePaymentDateSettingCommandHandler.handle(command);
    }

}

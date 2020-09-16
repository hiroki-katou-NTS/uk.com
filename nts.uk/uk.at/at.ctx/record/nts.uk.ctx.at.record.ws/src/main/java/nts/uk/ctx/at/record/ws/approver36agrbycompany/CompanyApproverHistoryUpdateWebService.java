package nts.uk.ctx.at.record.ws.approver36agrbycompany;

import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.CompanyApproverHistoryUpdateCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.CompanyApproverHistoryUpdateCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("approve/company")
@Produces("application/json")
public class CompanyApproverHistoryUpdateWebService extends WebService {

    @Inject
    private CompanyApproverHistoryUpdateCommandHandler commandHandler;

    @Path("update")
    @POST
    public void update(CompanyApproverHistoryUpdateDto dto){
        val command = new CompanyApproverHistoryUpdateCommand(dto.getCompanyId(),
                new DatePeriod(dto.getStarDate(),dto.getEndDate()),dto.getApproveList(),dto.getConfirmedList(),
                dto.getStartDateBeforeChange());
        this.commandHandler.handle(command);
    }

}

package nts.uk.ctx.at.record.ws.approver36agrbycompany;

/**
 * @author chinh.hm
 */

import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.CompanyApproverHistoryAddCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.CompanyApproverHistoryAddCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("approve/company")
@Produces("application/json")
public class CompanyApproverHistoryAddWebService extends WebService {

    @Inject
    private CompanyApproverHistoryAddCommandHandler commandHandler;

    @Path("register")
    @POST
    public void register(CompanyApproverHistoryAddDto dto){
        val command = new CompanyApproverHistoryAddCommand(dto.getCompanyId(),
                new DatePeriod(dto.getStarDate(),dto.getStarDate()),dto.getApproveList(),dto.getConfirmedList());
        this.commandHandler.handle(command);
    }

}

package nts.uk.ctx.at.record.ws.approver36agrbycompany;

import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryAddEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryAddEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryUpdateEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryUpdateEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d.CompanyApproverHistoryUpdateDateCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("approve/company")
@Produces("application/json")
public class CompanyApproverHistoryWebService extends WebService {

    @Inject
    private CompanyApproverHistoryUpdateEmployeeIdCommandHandler updateCommandHandler;
    @Inject
    private CompanyApproverHistoryAddEmployeeIdCommandHandler addCommandHandler;

    @Path("register")
    @POST
    public void register(CompanyApproveHistoryAddDto dto){
        val command = new CompanyApproverHistoryAddEmployeeIdCommand(dto.getCompanyId(),
                new DatePeriod(dto.getStarDate(),dto.getStarDate()),dto.getApproveList(),dto.getConfirmedList());
        this.addCommandHandler.handle(command);
    }
    @Path("update")
    @POST
    public void update(CompanyApproveHistoryUpdateDto dto){
        val command = new CompanyApproverHistoryUpdateEmployeeIdCommand(dto.getCompanyId(),
                new DatePeriod(dto.getStarDate(),dto.getEndDate()),dto.getApproveList(),dto.getConfirmedList(),
                dto.getStartDateBeforeChange());
        this.updateCommandHandler.handle(command);
    }

}

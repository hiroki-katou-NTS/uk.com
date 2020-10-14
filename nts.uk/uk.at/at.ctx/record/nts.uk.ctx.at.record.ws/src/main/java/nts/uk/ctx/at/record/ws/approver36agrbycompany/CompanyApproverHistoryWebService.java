package nts.uk.ctx.at.record.ws.approver36agrbycompany;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryAddEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryAddEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryUpdateEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryUpdateEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d.CompanyApproverHistoryDeleteDateCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d.CompanyApproverHistoryDeleteDateCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d.CompanyApproverHistoryUpdateDateCommand;
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
    @Inject
    private CompanyApproverHistoryUpdateDateCommandHandler updateDateCommandHandler;
    @Inject
    private CompanyApproverHistoryDeleteDateCommandHandler deleteDateCommandHandler;
    @Path("screen/a/register")
    @POST
    public void register(CompanyApproverHistoryAddEmployeeIdCommand command){
        this.addCommandHandler.handle(command);
    }
    @Path("screen/a/update")
    @POST
    public void update(CompanyApproverHistoryUpdateEmployeeIdCommand command){
        this.updateCommandHandler.handle(command);
    }
    @Path("screen/d/update")
    @POST
    public void updateDate(CompanyApproverHistoryUpdateDateCommand command) {
        this.updateDateCommandHandler.handle(command);
    }
    @Path("screen/d/delete")
    @POST
    public void deleteDate(CompanyApproverHistoryDeleteDateCommand command) {
        this.deleteDateCommandHandler.handle(command);
    }

}

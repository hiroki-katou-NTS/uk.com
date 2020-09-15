package nts.uk.ctx.at.record.ws.approver36agrbycompany;

/**
 * @author chinh.hm
 */

import nts.arc.layer.ws.WebService;
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
    public void register(CompanyApproverHistoryAddCommand command){
        this.commandHandler.handle(command);
    }

}

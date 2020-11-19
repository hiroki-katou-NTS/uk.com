package nts.uk.ctx.at.record.ws.approver36agrbyworkplace;



import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryAddEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryAddEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryUpdateEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryUpdateEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d.WorkPlaceApproverHistoryDeleteDateCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d.WorkPlaceApproverHistoryDeleteDateCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d.WorkPlaceApproverHistoryUpdateDateCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d.WorkPlaceApproverHistoryUpdateDateCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("approve/byworkplace")
public class WorkPlaceApproverHistoryWebService extends WebService {

    @Inject
    private WorkPlaceApproverHistoryAddEmployeeIdCommandHandler addEmployeeIdCommandHandler;
    @Inject
    private WorkPlaceApproverHistoryUpdateEmployeeIdCommandHandler updateEmployeeIdCommandHandler;

    @Inject
    private WorkPlaceApproverHistoryUpdateDateCommandHandler updateDateCommandHandler;
    @Inject
    private WorkPlaceApproverHistoryDeleteDateCommandHandler deleteDateCommandHandler;

    @Path("screen/b/register")
    @POST
    public void register(WorkPlaceApproverHistoryAddEmployeeIdCommand command) {
        this.addEmployeeIdCommandHandler.handle(command);
    }

    @Path("screen/b/update")
    @POST
    public void updateEmployeeId(WorkPlaceApproverHistoryUpdateEmployeeIdCommand command) {
        this.updateEmployeeIdCommandHandler.handle(command);
    }
    @Path("screen/d/update")
    @POST
    public void updateDate(WorkPlaceApproverHistoryUpdateDateCommand command) {
        this.updateDateCommandHandler.handle(command);
    }
    @Path("screen/d/delete")
    @POST
    public void deleteDate(WorkPlaceApproverHistoryDeleteDateCommand command) {
        this.deleteDateCommandHandler.handle(command);
    }
}

package nts.uk.ctx.at.record.ws.approver36agrbyworkplace;


import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryAddEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryAddEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryUpdateEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryUpdateEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d.WorkPlaceApproverHistoryDeleteDateCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d.WorkPlaceApproverHistoryDeleteDateCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d.WorkPlaceApproverHistoryUpdateDateCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d.WorkPlaceApproverHistoryUpdateDateCommandHandler;
import nts.uk.ctx.at.record.ws.approver36agrbyworkplace.screen_b.WorkPlaceApproveHistoryAddEmployeeIdDto;
import nts.uk.ctx.at.record.ws.approver36agrbyworkplace.screen_b.WorkPlaceApproverHistoryUpdateEmployeeIdDto;
import nts.uk.ctx.at.record.ws.approver36agrbyworkplace.screen_d.WorkPlaceApproveHistoryDeleteDateDto;
import nts.uk.ctx.at.record.ws.approver36agrbyworkplace.screen_d.WorkPlaceApproveHistoryUpdateDateDto;


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
    public void register(WorkPlaceApproveHistoryAddEmployeeIdDto dto) {
        val command = new WorkPlaceApproverHistoryAddEmployeeIdCommand(dto.getWorkPlaceId(),
                new DatePeriod(dto.getStartDate(), dto.getEndDate())
                , dto.getApproveList(), dto.getConfirmedList());
        this.addEmployeeIdCommandHandler.handle(command);
    }

    @Path("screen/b/update")
    @POST
    public void updateEmployeeId(WorkPlaceApproverHistoryUpdateEmployeeIdDto dto) {
        val command = new WorkPlaceApproverHistoryUpdateEmployeeIdCommand(dto.getWorkPlaceId(),
                new DatePeriod(dto.getStartDate(), dto.getEndDate()),
                dto.getApprovedList(), dto.getConfirmedList(), dto.getStartDateBeforeChange());
        this.updateEmployeeIdCommandHandler.handle(command);
    }
    @Path("screen/d/update")
    @POST
    public void updateDate(WorkPlaceApproveHistoryUpdateDateDto dto) {
        val command = new WorkPlaceApproverHistoryUpdateDateCommand(dto.getWorkPlaceId(),
                new DatePeriod(dto.getStartDate(), dto.getEndDate())
                , dto.getStartDateBeforeChange());
        this.updateDateCommandHandler.handle(command);
    }
    @Path("screen/d/delete")
    @POST
    public void deleteDate(WorkPlaceApproveHistoryDeleteDateDto dto) {
        val command = new WorkPlaceApproverHistoryDeleteDateCommand(dto.getWorkPlaceId(),
                new DatePeriod(dto.getStartDate(), dto.getEndDate()) );
        this.deleteDateCommandHandler.handle(command);
    }
}

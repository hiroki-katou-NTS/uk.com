package nts.uk.ctx.at.record.ws.approver36agrbyworkplace;


import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.DatePeriod;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("approve/byworkplace")
public class WorkPlaceApproverHistoryWebService extends WebService {

    @Inject
    private WorkPlaceApproverHistoryAddCommandHandler addCommandHandler;
    @Inject
    private WorkPlaceApproverHistoryUpdateCommandHandler updateCommandHandler;



    @Path("register")
    @POST
    public void register(WorkPlaceApproveHistoryAddDto dto) {
        val command = new WorkPlaceApproverHistoryAddCommand(dto.getWorkPlaceId(), new DatePeriod(dto.getStarDate(), dto.getEndDate())
                , dto.getApproveList(), dto.getConfirmedList());
        this.addCommandHandler.handle(command);
    }

    @Path("register")
    @POST
    public void update(WorkPlaceApproverHistoryUpdateDto dto) {
        val command = new WorkPlaceApproverHistoryUpdateCommand(dto.getWorkPlaceId(), new DatePeriod(dto.getStarDate(), dto.getEndDate()),
                dto.getApprovedList(), dto.getConfirmedList(), dto.getStartDateBeforeChange());
        this.updateCommandHandler.handle(command);
    }
}

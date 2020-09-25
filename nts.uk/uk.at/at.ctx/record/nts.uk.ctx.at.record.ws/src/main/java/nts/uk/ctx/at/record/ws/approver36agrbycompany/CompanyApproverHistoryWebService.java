package nts.uk.ctx.at.record.ws.approver36agrbycompany;

import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryAddEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryAddEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryUpdateEmployeeIdCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a.CompanyApproverHistoryUpdateEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d.CompanyApproverHistoryDeleteDateCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d.CompanyApproverHistoryDeleteDateCommandHandler;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d.CompanyApproverHistoryUpdateDateCommand;
import nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d.CompanyApproverHistoryUpdateDateCommandHandler;
import nts.uk.ctx.at.record.ws.approver36agrbycompany.screen_a.CompanyApproveHistoryAddEmployeeIdDto;
import nts.uk.ctx.at.record.ws.approver36agrbycompany.screen_a.CompanyApproveHistoryUpdateEmployeeIdDto;
import nts.uk.ctx.at.record.ws.approver36agrbycompany.screen_d.CompanyPlaceApproveHistoryDeleteDateDto;
import nts.uk.ctx.at.record.ws.approver36agrbycompany.screen_d.CompanyPlaceApproveHistoryUpdateDateDto;

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
    public void register(CompanyApproveHistoryAddEmployeeIdDto dto){
        val command = new CompanyApproverHistoryAddEmployeeIdCommand(dto.getCompanyId(),
                new DatePeriod(dto.getStarDate(),dto.getStarDate()),dto.getApproveList(),dto.getConfirmedList());
        this.addCommandHandler.handle(command);
    }
    @Path("screen/a/update")
    @POST
    public void update(CompanyApproveHistoryUpdateEmployeeIdDto dto){
        val command = new CompanyApproverHistoryUpdateEmployeeIdCommand(dto.getCompanyId(),
                new DatePeriod(dto.getStarDate(),dto.getEndDate()),dto.getApproveList(),dto.getConfirmedList(),
                dto.getStartDateBeforeChange());
        this.updateCommandHandler.handle(command);
    }
    @Path("screen/d/update")
    @POST
    public void updateDate(CompanyPlaceApproveHistoryUpdateDateDto dto) {
        val command = new CompanyApproverHistoryUpdateDateCommand(dto.getCompanyId(), new DatePeriod(dto.getStarDate(), dto.getEndDate())
                , dto.getStartDateBeforeChange());
        this.updateDateCommandHandler.handle(command);
    }
    @Path("screen/d/delete")
    @POST
    public void deleteDate(CompanyPlaceApproveHistoryDeleteDateDto dto) {
        val command = new CompanyApproverHistoryDeleteDateCommand(dto.getCompanyId(), new DatePeriod(dto.getStarDate(), dto.getEndDate()) );
        this.deleteDateCommandHandler.handle(command);
    }

}

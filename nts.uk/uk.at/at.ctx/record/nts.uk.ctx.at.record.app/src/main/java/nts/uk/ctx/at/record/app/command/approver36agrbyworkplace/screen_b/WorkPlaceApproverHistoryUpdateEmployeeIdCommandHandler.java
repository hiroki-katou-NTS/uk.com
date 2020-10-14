package nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b;


import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplaceRepo;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 * Screen B: 36申請の承認者/確認者を更新登録する Renew and register
 */
@Stateless
public class WorkPlaceApproverHistoryUpdateEmployeeIdCommandHandler extends CommandHandler<WorkPlaceApproverHistoryUpdateEmployeeIdCommand>{
    @Inject
    private Approver36AgrByWorkplaceRepo repo;

    @Override
    protected void handle(CommandHandlerContext<WorkPlaceApproverHistoryUpdateEmployeeIdCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val domain =  Approver36AgrByWorkplace.create(command.getWorkPlaceId(),new DatePeriod(command.getStartDate(),command.getEndDate()),
                command.getApprovedList()
                ,command.getConfirmedList());
        repo.updateStartDate(domain,command.getStartDateBeforeChange());

    }
}

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
        val domainPrevOpt = repo.getByWorkplaceIdAndEndDate(command.getWorkPlaceId(),command.getStartDateBeforeChange().addDays(-1));
        val domain = new Approver36AgrByWorkplace(command.getWorkPlaceId(),new DatePeriod(command.getPeriod().start(), GeneralDate.max()),
                command.getApprovedList()
                ,command.getConfirmedList());
        if(domainPrevOpt.isPresent()){
            DatePeriod period = new DatePeriod(domainPrevOpt.get().getPeriod().start(),command.getPeriod().start().addDays(-1));
            val domainPrevUpdate = new Approver36AgrByWorkplace(domainPrevOpt.get().getWorkplaceId(),period,domainPrevOpt.get().getApproverIds(),domainPrevOpt.get().getConfirmerIds());
            repo.update(domainPrevUpdate,period.start());
        }
        repo.insert(domain);

    }
}

package nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplaceRepo;
import nts.uk.ctx.at.record.dom.workplaceapproverhistory.ChangeWorkplaceApproverHistoryDomainService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

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
        val cid = AppContexts.user().companyId();
        val domain = new Approver36AgrByWorkplace(cid,command.getWorkPlaceId(),command.getPeriod(),command.getApprovedList(),command.getConfirmedList());
        RequireImpl require = new RequireImpl(repo);
        AtomTask persist = ChangeWorkplaceApproverHistoryDomainService.changeWorkplaceApproverHistory(require,command.getStartDateBeforeChange(),domain);
        transaction.execute(persist::run);
    }
    @AllArgsConstructor
    public class  RequireImpl implements ChangeWorkplaceApproverHistoryDomainService.Require{
        private Approver36AgrByWorkplaceRepo repo;
        @Override
        public Optional<Approver36AgrByWorkplace> getPrevHistory(String workplaceId, GeneralDate lastDate) {
            return repo.getByWorkplaceIdAndDate(workplaceId,lastDate);
        }

        @Override
        public void changeHistory(Approver36AgrByWorkplace domain) {
            repo.insert(domain);

        }
    }
}

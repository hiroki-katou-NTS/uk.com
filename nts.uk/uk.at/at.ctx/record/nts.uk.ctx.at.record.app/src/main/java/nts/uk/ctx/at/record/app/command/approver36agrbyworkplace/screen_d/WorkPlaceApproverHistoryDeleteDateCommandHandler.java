package nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplaceRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.DeleteWorkplaceApproverHistoryDomainService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class WorkPlaceApproverHistoryDeleteDateCommandHandler extends CommandHandler<WorkPlaceApproverHistoryDeleteDateCommand> {

    @Inject
    private Approver36AgrByWorkplaceRepo repo;

    @Override
    protected void handle(CommandHandlerContext<WorkPlaceApproverHistoryDeleteDateCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val domainOpt = repo.getByWorkplaceIdAndEndDate(command.getWorkPlaceId(), command.getEndDate());
        RequireImpl require = new RequireImpl(repo);
        if (domainOpt.isPresent()) {
            AtomTask persist = DeleteWorkplaceApproverHistoryDomainService.changeHistory(require,domainOpt.get());
            transaction.execute(persist::run);
        }

    }

    @AllArgsConstructor
    private class RequireImpl implements DeleteWorkplaceApproverHistoryDomainService.Require {
        private Approver36AgrByWorkplaceRepo byWorkplaceRepo;

        @Override
        public Optional<Approver36AgrByWorkplace> getLastHistory(String workplaceId, GeneralDate endDate) {
            return byWorkplaceRepo.getByWorkplaceIdAndEndDate(workplaceId, endDate);
        }

        @Override
        public void changeLatestHistory(Approver36AgrByWorkplace domain,GeneralDate date) {
            byWorkplaceRepo.updateStartDate(domain,date);
        }

        @Override
        public void deleteHistory(Approver36AgrByWorkplace domain) {
            byWorkplaceRepo.delete(domain);
        }
    }
}

package nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplaceRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.ChangeWorkplaceApproverHistoryDomainService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 *Screen D: 履歴を更新登録する（職場別）
 * @author chinh.hm
 */
@Stateless
public class WorkPlaceApproverHistoryUpdateDateCommandHandler extends CommandHandler<WorkPlaceApproverHistoryUpdateDateCommand> {
    @Inject
    private Approver36AgrByWorkplaceRepo repo;

    @Override
    protected void handle(CommandHandlerContext<WorkPlaceApproverHistoryUpdateDateCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        RequireImpl require = new RequireImpl(repo);
        val domainOpt = repo.getByWorkplaceIdAndDate(command.getWorkPlaceId(),command.getStartDateBeforeChange());
        if(domainOpt.isPresent()){
            val domain =  Approver36AgrByWorkplace.create(command.getWorkPlaceId(),new DatePeriod(command.getStartDate(),command.getEndDate())
                    ,domainOpt.get().getApproverIds(),domainOpt.get().getConfirmerIds());
            AtomTask persist = ChangeWorkplaceApproverHistoryDomainService.changeWorkplaceApproverHistory(require,command.getStartDateBeforeChange()
                    ,domain);
            transaction.execute(persist::run);
        }

    }
    @AllArgsConstructor
    private class RequireImpl implements ChangeWorkplaceApproverHistoryDomainService.Require{
        private Approver36AgrByWorkplaceRepo byWorkplaceRepo;
        @Override
        public Optional<Approver36AgrByWorkplace> getPrevHistory(String workplaceId, GeneralDate lastDate) {
            return byWorkplaceRepo.getByWorkplaceIdAndEndDate(workplaceId,lastDate);
        }

        @Override
        public void changeHistory(Approver36AgrByWorkplace domain,GeneralDate date) {
            byWorkplaceRepo.updateStartDate(domain,date);
        }
    }
}

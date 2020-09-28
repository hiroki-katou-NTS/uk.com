package nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b;



import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AddWorkplaceApproverHistoryDomainService;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplaceRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen B: 36申請の承認者/確認者を新規登録する Regist
 * @author chinh.hm
 */
@Stateless
public class WorkPlaceApproverHistoryAddEmployeeIdCommandHandler extends CommandHandler<WorkPlaceApproverHistoryAddEmployeeIdCommand> {
    @Inject
    private Approver36AgrByWorkplaceRepo repo;
    @Override

    protected void handle(CommandHandlerContext<WorkPlaceApproverHistoryAddEmployeeIdCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val domain = new Approver36AgrByWorkplace(
                command.getWorkPlaceId(),
                command.getPeriod(),
                command.getApproveList(),
                command.getConfirmedList());
        RequireImpl require = new RequireImpl(repo);
        AtomTask persist = AddWorkplaceApproverHistoryDomainService.addNewWorkplaceApproverHistory(require,domain);
        transaction.execute(()->persist.run());

    }

    @AllArgsConstructor
    public class RequireImpl implements AddWorkplaceApproverHistoryDomainService.Requeire {

        private Approver36AgrByWorkplaceRepo workplaceRepo;
        @Override
        public Optional<Approver36AgrByWorkplace> getLatestHistory(String workplaceId, GeneralDate endDate) {

            return workplaceRepo.getByWorkplaceIdAndEndDate(workplaceId,endDate);
        }

        @Override
        public void addHistory(Approver36AgrByWorkplace domain) {
            workplaceRepo.insert(domain);
        }

        @Override
        public void changeLatestHistory(Approver36AgrByWorkplace domain) {
            workplaceRepo.update(domain);
        }
    }
}

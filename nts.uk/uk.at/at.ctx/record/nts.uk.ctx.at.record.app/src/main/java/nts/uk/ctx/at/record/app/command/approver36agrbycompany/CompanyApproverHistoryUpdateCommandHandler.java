package nts.uk.ctx.at.record.app.command.approver36agrbycompany;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.CompanyApproverHistoryChangeDomainService;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CompanyApproverHistoryUpdateCommandHandler extends CommandHandler<CompanyApproverHistoryUpdateCommand> {
    @Inject
    private Approver36AgrByCompanyRepo approver36AgrByCompanyRepo;
    @Override
    protected void handle(CommandHandlerContext<CompanyApproverHistoryUpdateCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val cid = command.getCompanyId();
        val domain = new Approver36AgrByCompany(cid, command.getPeriod(), command.getApprovedList(),
                command.getConfirmedList());
        RequireImp requireImp = new RequireImp(cid,approver36AgrByCompanyRepo);
        AtomTask presist = CompanyApproverHistoryChangeDomainService.changeApproverHistory(requireImp,
                command.getStartDateBeforeChange(),domain);
        transaction.execute(()->presist.run());
    }

    @AllArgsConstructor
    private class RequireImp implements CompanyApproverHistoryChangeDomainService.Require{
        private String cid;
        private Approver36AgrByCompanyRepo approver36AgrByCompanyRepo;

        @Override
        public Optional<Approver36AgrByCompany> getPrevHistory(GeneralDate endDate) {
            return approver36AgrByCompanyRepo.getByCompanyIdAndEndDate(cid,endDate);
        }

        @Override
        public void changeHistory(Approver36AgrByCompany hist) {
            approver36AgrByCompanyRepo.update(hist);
        }
    }
}

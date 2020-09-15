package nts.uk.ctx.at.record.app.command.approver36agrbycompany;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.CompanyApproverHistoryAddDomainService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CompanyApproverHistoryAddCommandHandler extends CommandHandler<CompanyApproverHistoryAddCommand> {
    @Inject
    private Approver36AgrByCompanyRepo repo;

    @Override
    protected void handle(CommandHandlerContext<CompanyApproverHistoryAddCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val cid = AppContexts.user().companyId();
        RequireImpl require = new RequireImpl(repo,cid);
        val domain = new Approver36AgrByCompany(cid, command.getPeriod(), command.getApproverList(), command.getConfirmerList());
        AtomTask persist = CompanyApproverHistoryAddDomainService.addApproverHistory(require, domain);
        transaction.execute(() ->
                persist.run()
        );
    }

    @AllArgsConstructor
    private class RequireImpl implements CompanyApproverHistoryAddDomainService.Require {
        private Approver36AgrByCompanyRepo approver36AgrByCompanyRepo;
        private String cid;

        @Override
        public Optional<Approver36AgrByCompany> getLatestHistory(GeneralDate baseDate) {
            return approver36AgrByCompanyRepo.getByCompanyIdAndEndDate(cid,baseDate);
        }

        @Override
        public void addHistory(Approver36AgrByCompany hist) {
            approver36AgrByCompanyRepo.insert(hist);
        }

        @Override
        public void changeLatestHistory(Approver36AgrByCompany hist) {
            approver36AgrByCompanyRepo.update(hist);
        }
    }

}

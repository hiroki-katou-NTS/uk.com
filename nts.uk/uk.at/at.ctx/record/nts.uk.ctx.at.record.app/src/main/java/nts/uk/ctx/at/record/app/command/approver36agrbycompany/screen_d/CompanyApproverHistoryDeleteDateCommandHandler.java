package nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.CompanyApproverHistoryDeleteDomainService;
import nts.uk.shr.com.context.AppContexts;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen D: 履歴を更新登録する（会社別）
 * @author : chinh.hm
 */
@Stateless
public class CompanyApproverHistoryDeleteDateCommandHandler extends CommandHandler<CompanyApproverHistoryDeleteDateCommand> {
    @Inject
    private Approver36AgrByCompanyRepo repo;

    @Override
    protected void handle(CommandHandlerContext<CompanyApproverHistoryDeleteDateCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String cid = command.getCompanyId();
        if(StringUtil.isNullOrEmpty(command.getCompanyId(),true)){
             cid = AppContexts.user().companyId();
        }
        RequireImpl require = new RequireImpl(repo);
        val optDomain = repo.getByCompanyIdAndEndDate(cid,command.getEndDate());
        if(optDomain.isPresent()){
            AtomTask persist = CompanyApproverHistoryDeleteDomainService.deleteApproverHistory(require,optDomain.get());
            transaction.execute(persist::run);
        }

    }
    @AllArgsConstructor
    private class RequireImpl implements CompanyApproverHistoryDeleteDomainService.Require{
        private Approver36AgrByCompanyRepo byCompanyRepo;

        @Override
        public Optional<Approver36AgrByCompany> getPrevHistory(GeneralDate endDate) {
            return byCompanyRepo.getByCompanyIdAndEndDate(AppContexts.user().companyId(),endDate);
        }

        @Override
        public void changeHistory(Approver36AgrByCompany hist,GeneralDate date) {
            byCompanyRepo.updateStartDate(hist,date);
        }

        @Override
        public void deleteHistory(Approver36AgrByCompany hist) {
            byCompanyRepo.delete(hist);
        }
    }
}

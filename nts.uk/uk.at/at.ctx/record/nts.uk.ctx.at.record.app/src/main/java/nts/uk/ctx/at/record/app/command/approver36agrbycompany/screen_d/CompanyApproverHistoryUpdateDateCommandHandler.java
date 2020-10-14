package nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.CompanyApproverHistoryChangeDomainService;
import nts.uk.shr.com.context.AppContexts;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen D: 履歴を更新登録する（会社別）
 */
@Stateless
public class CompanyApproverHistoryUpdateDateCommandHandler extends CommandHandler<CompanyApproverHistoryUpdateDateCommand>{

    @Inject
    private Approver36AgrByCompanyRepo repo;
    @Override
    protected void handle(CommandHandlerContext<CompanyApproverHistoryUpdateDateCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String cid = command.getCompanyId();
        if(StringUtil.isNullOrEmpty(cid,true)){
            cid = AppContexts.user().companyId();
        }
        RequireImpl require = new RequireImpl(repo);
        val domainOpt = repo.getByCompanyIdAndDate(cid,GeneralDate.max());
        if(domainOpt.isPresent()){
            val domain =  Approver36AgrByCompany.create(cid,new DatePeriod(command.getStartDate(),GeneralDate.max()),
                    domainOpt.get().getApproverList()
                    ,domainOpt.get().getConfirmerList());

            AtomTask persist = CompanyApproverHistoryChangeDomainService.changeApproverHistory(require,command.getStartDateBeforeChange(),domain);
            transaction.execute(persist::run);
        }

    }
    @AllArgsConstructor
    private class RequireImpl implements CompanyApproverHistoryChangeDomainService.Require{
        private Approver36AgrByCompanyRepo byCompanyRepo;

        @Override
        public Optional<Approver36AgrByCompany> getPrevHistory(GeneralDate endDate) {
            return byCompanyRepo.getByCompanyIdAndEndDate(AppContexts.user().companyId(),endDate);
        }

        @Override
        public void changeHistory(Approver36AgrByCompany hist,GeneralDate date) {
            byCompanyRepo.updateStartDate(hist,date);
        }
    }
}

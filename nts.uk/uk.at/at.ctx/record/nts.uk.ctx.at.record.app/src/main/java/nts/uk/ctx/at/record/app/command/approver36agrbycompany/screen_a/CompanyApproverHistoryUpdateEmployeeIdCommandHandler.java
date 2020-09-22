package nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.CompanyApproverHistoryChangeDomainService;
import nts.uk.shr.com.context.AppContexts;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen A: 36申請の承認者/確認者履歴を更新登録する Update History
 * @author chinh.hm
 */
@Stateless
public class CompanyApproverHistoryUpdateEmployeeIdCommandHandler extends CommandHandler<CompanyApproverHistoryUpdateEmployeeIdCommand> {
    @Inject
    private Approver36AgrByCompanyRepo approver36AgrByCompanyRepo;
    @Override
    protected void handle(CommandHandlerContext<CompanyApproverHistoryUpdateEmployeeIdCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String cid = command.getCompanyId();
        if(StringUtil.isNullOrEmpty(cid,true)){
            cid = AppContexts.user().companyId();
        }
        val domain = new Approver36AgrByCompany(cid, command.getPeriod(), command.getApprovedList(),
                command.getConfirmedList());
        RequireImp requireImp = new RequireImp(cid,approver36AgrByCompanyRepo);
        AtomTask persist = CompanyApproverHistoryChangeDomainService.changeApproverHistory(requireImp,
                command.getStartDateBeforeChange(),domain);
        transaction.execute(()->persist.run());
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

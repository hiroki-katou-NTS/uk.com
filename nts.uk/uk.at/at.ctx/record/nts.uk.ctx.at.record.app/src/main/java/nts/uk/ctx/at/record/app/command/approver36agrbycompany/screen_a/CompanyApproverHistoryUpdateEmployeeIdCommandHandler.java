package nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a;


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
 * Screen A: 36申請の承認者/確認者履歴を更新登録する Update History
 * @author chinh.hm
 */
@Stateless
public class CompanyApproverHistoryUpdateEmployeeIdCommandHandler extends CommandHandler<CompanyApproverHistoryUpdateEmployeeIdCommand> {
    @Inject
    private Approver36AgrByCompanyRepo repo;
    @Override
    protected void handle(CommandHandlerContext<CompanyApproverHistoryUpdateEmployeeIdCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String cid = command.getCompanyId();
        if(StringUtil.isNullOrEmpty(cid,true)){
            cid = AppContexts.user().companyId();
        }
        val domainUpdate = Approver36AgrByCompany.create(cid,new DatePeriod(command.getPeriod().start(),GeneralDate.max()),
                command.getApprovedList(),
                command.getConfirmedList());
        val domainPrevOpt = repo.getByCompanyIdAndEndDate(cid,command.getStartDateBeforeChange().addDays(-1));
        if(domainPrevOpt.isPresent()){
            val domainPrev = domainPrevOpt.get();
            DatePeriod period = new DatePeriod(domainPrev.getPeriod().start(),command.getPeriod().start().addDays(-1));
            val domain =  Approver36AgrByCompany.create(domainPrev.getCompanyId(),period,domainPrev.getApproverList()
                    ,domainPrev.getConfirmerList() );
            repo.update(domain,period.start());
        }
        repo.update(domainUpdate,command.getStartDateBeforeChange());

    }


}

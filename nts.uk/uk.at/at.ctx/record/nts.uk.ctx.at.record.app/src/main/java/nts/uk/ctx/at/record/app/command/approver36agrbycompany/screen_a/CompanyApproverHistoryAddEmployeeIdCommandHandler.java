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
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.CompanyApproverHistoryAddDomainService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen A: 36申請の承認者/確認者履歴を新規登録する Regist History
 * @author chinh.hm
 */
@Stateless
public class CompanyApproverHistoryAddEmployeeIdCommandHandler extends CommandHandler<CompanyApproverHistoryAddEmployeeIdCommand> {
    @Inject
    private Approver36AgrByCompanyRepo repo;

    @Override
    protected void handle(CommandHandlerContext<CompanyApproverHistoryAddEmployeeIdCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String cid = command.getCompanyId();
        if(StringUtil.isNullOrEmpty(cid,true)){
            cid = AppContexts.user().companyId();
        }
        val domainPrev = repo.getByCompanyIdAndEndDate(cid,command.getPeriod().start().addDays(-1));
        val domainRegister = new Approver36AgrByCompany(cid, command.getPeriod(), command.getApproveList(), command.getConfirmedList());
        if(domainPrev.isPresent()){
            DatePeriod period = new DatePeriod(domainPrev.get().getPeriod().start(),command.getPeriod().start().addDays(-1));
            val domainUpdate = new Approver36AgrByCompany(cid,period,domainPrev.get().getApproverList(),domainPrev.get().getConfirmerList());
            repo.update(domainUpdate,period.start());
        }
        repo.insert(domainRegister);

    }

}

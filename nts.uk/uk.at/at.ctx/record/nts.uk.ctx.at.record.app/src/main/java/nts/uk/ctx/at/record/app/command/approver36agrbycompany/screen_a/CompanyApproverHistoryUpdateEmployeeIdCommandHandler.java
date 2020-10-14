package nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_a;


import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
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
        if (StringUtil.isNullOrEmpty(cid, true)) {
            cid = AppContexts.user().companyId();
        }
        val domainUpdate = Approver36AgrByCompany.create(cid, new DatePeriod(command.getStartDate(), command.getEndDate()),
                command.getApprovedList(),
                command.getConfirmedList());
        repo.updateStartDate(domainUpdate, command.getStartDateBeforeChange());
    }
}

package nts.uk.ctx.workflow.app.command.agent;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.workflow.dom.adapter.sys.env.maildestination.MailDestinationAdapter;
import nts.uk.ctx.workflow.dom.adapter.sys.env.maildestination.MailDestinationImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Stateless
@Transactional
public class SendMailToApproverCommandHandler extends CommandHandler<SendEmailCommand> {
    @Inject
    private MailSender mailSender;

    @Inject
    private MailDestinationAdapter mailDestinationAdapter;

    @Override
    protected void handle(CommandHandlerContext<SendEmailCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        //imported（申請承認）「社員メールアドレス」を取得する  - Rq225 (419)
        List<MailDestinationImport> lstApplicantMail = mailDestinationAdapter.getEmpEmailAddress(companyId, Arrays.asList(commandHandlerContext.getCommand().getApproverId()), 6);
        if(lstApplicantMail.isEmpty()
                || lstApplicantMail.get(0).getOutGoingMails().isEmpty()
                || StringUtils.isEmpty(lstApplicantMail.get(0).getOutGoingMails().get(0))){
            throw new BusinessException("Msg_791");
        }
        //代行承認者へ代行依頼メールを送信する
        mailSender.sendFromAdmin(lstApplicantMail.get(0).getOutGoingMails().get(0),
                new MailContents("email subject", commandHandlerContext.getCommand().getEmailContent()));
    }
}

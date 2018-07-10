package nts.uk.ctx.at.request.app.command.application.approvalstatus;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Subject;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
@Transactional
public class RegisterApprovalStatusMailTempCommandHandler extends CommandHandler<List<ApprovalStatusMailTempCommand>> {

	@Inject
	private ApprovalStatusMailTempRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<List<ApprovalStatusMailTempCommand>> context) {
		String companyID = AppContexts.user().companyId();
		List<ApprovalStatusMailTempCommand> listCommand = context.getCommand();
		for (ApprovalStatusMailTempCommand command : listCommand) {
			// ドメインモデル「承認状況メールテンプレート」を更新する
			if (command.getEditMode() == 0) {
				repository.add(new ApprovalStatusMailTemp(companyID,
						EnumAdaptor.valueOf(command.getMailType(), ApprovalStatusMailType.class),
						EnumAdaptor.valueOf(command.getUrlApprovalEmbed(), NotUseAtr.class),
						EnumAdaptor.valueOf(command.getUrlDayEmbed(), NotUseAtr.class),
						EnumAdaptor.valueOf(command.getUrlMonthEmbed(), NotUseAtr.class),
						new Subject(command.getMailSubject()), new Content(command.getMailContent())));
			} else {
				repository.update(new ApprovalStatusMailTemp(companyID,
						EnumAdaptor.valueOf(command.getMailType(), ApprovalStatusMailType.class),
						EnumAdaptor.valueOf(command.getUrlApprovalEmbed(), NotUseAtr.class),
						EnumAdaptor.valueOf(command.getUrlDayEmbed(), NotUseAtr.class),
						EnumAdaptor.valueOf(command.getUrlMonthEmbed(), NotUseAtr.class),
						new Subject(command.getMailSubject()), new Content(command.getMailContent())));
			}
		}
	}
}

package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEdit;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEditRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 応援カード編集設定の更新を行う
 * @author NWS_namnv
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateSupportCardSettingCommandHandler extends CommandHandler<SupportCardSettingCommand> {
	
	@Inject
	private SupportCardEditRepository supportCardEditRepository;

	@Override
	protected void handle(CommandHandlerContext<SupportCardSettingCommand> context) {
		SupportCardSettingCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		// get 応援カード編集設定
		Optional<SupportCardEdit> supportCardEdit = this.supportCardEditRepository.get(cid);
		if (supportCardEdit.isPresent()) {
			this.supportCardEditRepository.update(command.toDomain());
		} else {
			this.supportCardEditRepository.insert(command.toDomain());
		}
	}

}

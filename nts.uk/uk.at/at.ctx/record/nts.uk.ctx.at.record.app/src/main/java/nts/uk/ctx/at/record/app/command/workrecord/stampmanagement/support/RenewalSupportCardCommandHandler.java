package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 応援カードの更新登録を行う
 * @author NWS_namnv
 *
 */
@Stateless
public class RenewalSupportCardCommandHandler extends CommandHandler<SupportCardCommand> {
	
	@Inject
	private SupportCardRepository supportCardRepository;

	@Override
	protected void handle(CommandHandlerContext<SupportCardCommand> context) {
		SupportCardCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		// カードを編集する
//		Optional<SupportCardEdit> supportCardEdit = this.supportCardEditRepository.get(cid);
		
		// get(応援カード番号, 会社ID): 応援カード
		Optional<SupportCard> supportCard = this.supportCardRepository.get(cid, command.getSupportCardNumber());
		supportCard.ifPresent(t -> {
			SupportCard cardUpdate = new SupportCard(t.getCid(), t.getSupportCardNumber(), command.getWorkplaceId());
			this.supportCardRepository.update(Arrays.asList(cardUpdate));
		});
	}

}

package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEdit;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEditRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;

/**
 * 応援カードの更新登録を行う
 * @author NWS_namnv
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RenewalSupportCardCommandHandler extends CommandHandler<SupportCardCommand> {
	
	@Inject
	private SupportCardRepository supportCardRepository;
	
	@Inject
	private SupportCardEditRepository supportCardEditRepository;

	@Override
	protected void handle(CommandHandlerContext<SupportCardCommand> context) {
		SupportCardCommand command = context.getCommand();
		
		// カードを編集する(編集前番号): 応援カード番号
		Optional<SupportCardEdit> optEdit = this.supportCardEditRepository.get(command.getCompanyId());
		optEdit.ifPresent(cardEdit -> {
			SupportCardNumber supportCardNumber = cardEdit
					.editTheCard(new SupportCardNumber(command.getSupportCardNumber()));
			
			// get(応援カード番号, 会社ID): 応援カード
			Optional<SupportCard> supportCard = this.supportCardRepository.getBySupportCardNo(supportCardNumber.v());
			supportCard.ifPresent(t -> {
				t.setWorkplaceId(command.getWorkplaceId());
				this.supportCardRepository.update(Arrays.asList(t));
			});
		});
	}

}

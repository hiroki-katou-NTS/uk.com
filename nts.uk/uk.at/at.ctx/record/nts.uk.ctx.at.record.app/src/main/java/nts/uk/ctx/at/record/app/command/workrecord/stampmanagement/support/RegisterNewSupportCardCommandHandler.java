package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;

/**
 * 応援カードの新規登録を行う
 * @author NWS_namnv
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterNewSupportCardCommandHandler extends CommandHandler<SupportCardCommand> {
	
	@Inject
	private SupportCardRepository supportCardRepository;

	@Override
	protected void handle(CommandHandlerContext<SupportCardCommand> context) {
		SupportCardCommand command = context.getCommand();
		
		// get(応援カード番号): 応援カード
		Optional<SupportCard> supportCard = this.supportCardRepository.getBySupportCardNo(command.getSupportCardNumber());
		
		// not 応援カード番号.empty
		if (supportCard.isPresent()) {
			throw new BusinessException("Msg_2137");
		}
		
		// 応援カード作成する(Require, 会社ID, 応援カード番号, 職場ID): 応援カード
		this.supportCardRepository.insert(Arrays.asList(command.toDomain()));
	}

}

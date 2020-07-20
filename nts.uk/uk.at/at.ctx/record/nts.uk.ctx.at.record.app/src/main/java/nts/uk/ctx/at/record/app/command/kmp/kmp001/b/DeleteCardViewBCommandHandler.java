package nts.uk.ctx.at.record.app.command.kmp.kmp001.b;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.B：カードNO指定による個人の登録.メニュー別OCD.カードNOの削除を行う
 * 
 * @author chungnt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteCardViewBCommandHandler extends CommandHandler<DeleteCardViewBCommand> {

	@Inject
	private StampCardRepository stampCardRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteCardViewBCommand> context) {
		DeleteCardViewBCommand command = context.getCommand();
		String contactCode = AppContexts.user().contractCode();
		StampCard card = new StampCard(contactCode, command.getCardNumber(), null);

		Optional<StampCard> stampCard = stampCardRepo.getStampCardByEmployeeCardNumber(command.getEmployeeId(),
				command.getCardNumber());

		if (stampCard.isPresent()) {
			stampCardRepo.update(card);
		}
	}

}

package nts.uk.ctx.at.record.app.command.kmp.kmp001.c;

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
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.C：IDカード未登録打刻指定.メニュー別OCD.個人未登録のカードに個人を登録する
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterStampCardViewCCommandHandler extends CommandHandler<RegisterStampCardViewCCommand>{

	@Inject
	private StampCardRepository stampcardRepo;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterStampCardViewCCommand> context) {
		RegisterStampCardViewCCommand command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		
		Optional<StampCard> stampCard = this.stampcardRepo.getStampCardByEmployeeCardNumber(command.getEmployeeId(), command.getCardNumber());
		
		if (stampCard.isPresent()) {
			StampCard card = new StampCard(contractCode, command.getCardNumber(), command.getEmployeeId());
			this.stampcardRepo.update(card);
		} else {
			StampCard card = new StampCard(contractCode, command.getCardNumber(), command.getEmployeeId());
			this.stampcardRepo.add(card);
		}
	}
}

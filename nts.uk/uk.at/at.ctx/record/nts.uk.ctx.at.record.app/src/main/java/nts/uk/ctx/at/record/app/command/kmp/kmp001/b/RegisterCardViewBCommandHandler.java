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

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.B：カードNO指定による個人の登録.メニュー別OCD.カードNOの登録を行う
 * 
 * @author chungnt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterCardViewBCommandHandler extends CommandHandler<RegisterCardViewBCommand> {

	@Inject
	private StampCardRepository stampcardRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterCardViewBCommand> context) {
		RegisterCardViewBCommand command = context.getCommand();
		
		//1: get....
		Optional<StampCard> stampCard = stampcardRepo.getStampCardByEmployeeCardNumber(command.getEmployeeId(),
				command.getCardNumber());
		
		stampCard.ifPresent(stamp -> {
			stamp.setEmployeeId(command.getEmployeeIdSelect());
			stampcardRepo.update(stamp);
		});
	}
}

package nts.uk.ctx.at.record.app.command.kmp.kmp001.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.A：個人指定によるIDカード登録.メニュー別OCD.新規モード時にIDカードNOの登録を行う
 * @author chungnt
 *
 */
@Stateless
public class RegisterEmployeeCardInformationCommandHandler
		extends CommandHandler<RegisterEmployeeCardInformationCommand> {

	@Inject
	private StampCardRepository stampCardRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterEmployeeCardInformationCommand> context) {
		RegisterEmployeeCardInformationCommand command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		
		Optional<StampCard> stampCard = stampCardRepo.getStampCardByEmployeeCardNumber(command.getEmployeeId(), command.getCardNumber());
		
		if (stampCard.isPresent()) {
			throw new BusinessException("Msg_1659");
		}
		
		StampCard card = new StampCard(contractCode, command.getCardNumber(), command.getEmployeeId());
		stampCardRepo.add(card);
	}
}

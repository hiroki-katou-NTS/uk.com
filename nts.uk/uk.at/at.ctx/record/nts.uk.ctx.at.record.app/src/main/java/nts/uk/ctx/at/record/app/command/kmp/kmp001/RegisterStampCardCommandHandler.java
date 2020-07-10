package nts.uk.ctx.at.record.app.command.kmp.kmp001;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.kmp.kmp001.b.RegisterEmployeeCardCommand;
import nts.uk.ctx.at.record.app.command.kmp.kmp001.c.RegisterStampCardViewCCommand;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */
public class RegisterStampCardCommandHandler {

	@Inject
	private StampCardRepository stampCardRepo;

	public void saveStarpCardViewB(RegisterEmployeeCardCommand command) {
		String contractCd = AppContexts.user().contractCode();
		StampCard card = new StampCard(contractCd, command.getCardNumber(), command.getEmployeeId());

		Optional<StampCard> stampCard = stampCardRepo.getStampCardByEmployeeCardNumber(command.getEmployeeId(),
				command.getCardNumber());

		if (stampCard.isPresent()) {
			stampCardRepo.update(card);
		}

		stampCardRepo.add(card);
	}

	public void saveStampCardViewC(RegisterStampCardViewCCommand command) {
		String contractCd = AppContexts.user().contractCode();
		StampCard card = new StampCard(contractCd, command.getCardNumber(), command.getEmployeeId());

		Optional<StampCard> stampCard = stampCardRepo.getStampCardByContractCdEmployeeCardNumber(contractCd,
				command.getEmployeeId(), command.getCardNumber());

		if (stampCard.isPresent()) {
			stampCardRepo.update(card);
		}

		stampCardRepo.add(card);
	}
}

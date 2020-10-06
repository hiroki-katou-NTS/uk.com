package nts.uk.screen.at.app.query.kdp.KDP005.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhpv
 *
 */
@Stateless
public class ICCardStampFinder {

	@Inject
	private StampCardRepository stampCardRepo;

	public Optional<String> getEmployeeIdByICCard(String cardNumber) {
		String contractCode = AppContexts.user().contractCode();
		Optional<StampCard> stamp = this.stampCardRepo.getByCardNoAndContractCode(cardNumber, contractCode);
		return stamp.isPresent() ? Optional.of(stamp.get().getEmployeeId()) : Optional.empty();
	}
}

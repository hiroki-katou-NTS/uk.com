package nts.uk.screen.at.app.query.kmp.kmp001;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class StampCardDigit {

	@Inject
	private StampCardEditingRepo stampCardEditingRepo;
	
	public StampCardDigitDto get() {
		String companyId = AppContexts.user().companyId();
		
		Optional<StampCardEditing> cardEditing = stampCardEditingRepo.get(companyId);
		
		if (!cardEditing.isPresent()) {
			throw new RuntimeException("Not found");
		}
		
		StampCardDigitDto cardDigitNumberDto = new StampCardDigitDto(cardEditing.get().getDigitsNumber().v());
		
		return cardDigitNumberDto;
	}
	
}

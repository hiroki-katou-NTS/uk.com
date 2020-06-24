package nts.uk.screen.at.app.query.kmp.kmp001.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerformInitialStartup {

	@Inject
	private StampCardEditingRepo stampCardEditRepo;

	public PerformInitialStartupDto findDto() {

		String cId = AppContexts.user().companyId();
		Optional<StampCardEditing> stampCardEditingOpt = stampCardEditRepo.get(cId);

		return stampCardEditingOpt.map(s -> new PerformInitialStartupDto(s.getDigitsNumber().v() == null ? 0 : s.getDigitsNumber().v(), s.getMethod().value))
				.orElseThrow(RuntimeException :: new);
	}
}

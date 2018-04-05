package nts.uk.ctx.at.shared.app.find.entranceexit;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExit;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ManageEntryExitFinder.
 *
 * @author hoangdd
 */
@Stateless
public class ManageEntryExitFinder {
	
	/** The repository. */
	@Inject
	ManageEntryExitRepository repository;
	
	/**
	 * Find data.
	 *
	 * @return the manage entry exit dto
	 */
	public ManageEntryExitDto findData() {
		String companyId = AppContexts.user().companyId();
		
		Optional<ManageEntryExit> optDomain = repository.findByID(companyId);
		if (optDomain.isPresent()) {
			ManageEntryExitDto dto = new ManageEntryExitDto();
			dto.setUseClassification(optDomain.get().getUseClassification().value);
			return dto;
		}
		return null;
	}
}


/**
 * 
 */
package nts.uk.ctx.at.record.app.find.workrecord.temporarywork;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ManageWorkTemporaryFinder.
 *
 * @author hoangdd
 */
@Stateless
public class ManageWorkTemporaryFinder {
	
	/** The repository. */
	@Inject
	private ManageWorkTemporaryRepository repository;
	
	/**
	 * Find data.
	 *
	 * @return the manage work temporary dto
	 */
	public ManageWorkTemporaryDto findData() {
		String companyID = AppContexts.user().companyId();
		ManageWorkTemporaryDto dto = new ManageWorkTemporaryDto();
		Optional<ManageWorkTemporary> optDomain = repository.findByCID(companyID);
		if (optDomain.isPresent()) {
			dto.setMaxUsage(optDomain.get().getMaxUsage().v());
			dto.setTimeTreatTemporarySame(optDomain.get().getTimeTreatTemporarySame().v());
			return dto;
		}
		
		return null;
	}
}
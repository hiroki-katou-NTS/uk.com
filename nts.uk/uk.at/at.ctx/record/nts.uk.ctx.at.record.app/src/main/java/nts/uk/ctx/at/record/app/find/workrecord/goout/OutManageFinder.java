package nts.uk.ctx.at.record.app.find.workrecord.goout;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutManageFinder.
 *
 * @author hoangdd
 */
@Stateless
public class OutManageFinder {
	
	/** The repository. */
	@Inject
	private OutManageRepository repository;
	
	/**
	 * Find data.
	 *
	 * @return the out manage dto
	 */
	public OutManageDto findData() {
		String companyId = AppContexts.user().companyId();
		Optional<OutManage> optDomain = repository.findByID(companyId);
		if (optDomain.isPresent()) {
			OutManageDto dto = new OutManageDto();
			dto.setInitValueReasonGoOut(optDomain.get().getInitValueReasonGoOut().value);
			dto.setMaxUsage(optDomain.get().getMaxUsage().v());
			return dto;
		}
		return null;
	}
}


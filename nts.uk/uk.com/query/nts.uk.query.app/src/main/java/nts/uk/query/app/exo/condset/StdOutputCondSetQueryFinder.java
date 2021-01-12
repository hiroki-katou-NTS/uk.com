package nts.uk.query.app.exo.condset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.query.app.exo.condset.dto.StdOutputCondSetDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class Standard output condition setting query finder.
 * 
 * @author nws-minhnb
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StdOutputCondSetQueryFinder {

	/**
	 * The Standard output condition setting repository.
	 */
	@Inject
	private StdOutputCondSetRepository stdCondSetRepo;

	/**
	 * Gets standard output condition settings by company id.
	 * 
	 * @return the <code>StdOutputCondSetDto</code>
	 */
	public List<StdOutputCondSetDto> getStdOutputCondSetsByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return this.stdCondSetRepo.getStdOutCondSetByCid(companyId)
								  .stream()
								  .map(StdOutputCondSetDto::createFromDomain)
								  .collect(Collectors.toList());
	}

}

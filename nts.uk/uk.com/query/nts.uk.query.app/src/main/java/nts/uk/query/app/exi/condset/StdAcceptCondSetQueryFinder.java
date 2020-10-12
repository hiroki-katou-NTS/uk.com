package nts.uk.query.app.exi.condset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.query.app.exi.condset.dto.StdAcceptCondSetDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class Standard acceptance condition setting query finder.
 * 
 * @author nws-minhnb
 *
 */
@Stateless
public class StdAcceptCondSetQueryFinder {

	/**
	 * The Standard acceptance condition setting repository.
	 */
	@Inject
	private StdAcceptCondSetRepository stdAcceptCondSetRepo;

	/**
	 * Gets standard acceptance condition settings by company id.
	 * 
	 * @return the <code>StdAcceptCondSetDto</code>
	 */
	public List<StdAcceptCondSetDto> getStdAcceptCondSetsByCompanyId() {
		String companyId = AppContexts.user().companyId();
		return this.stdAcceptCondSetRepo.findAllStdAcceptCondSetsByCompanyId(companyId)
										.stream()
										.map(StdAcceptCondSetDto::fromDomain)
										.collect(Collectors.toList());
	}

}

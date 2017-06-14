package nts.uk.ctx.sys.portal.app.find.standardmenu;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class StandardMenuFinder.
 */
@Stateless
public class StandardMenuFinder {
	@Inject
	private StandardMenuRepository standardMenuRepository;
	
	/**
	 * find all StandardMenu by companyID
	 * @param conpanyID
	 * @return List
	 */
	public List<StandardMenuDto> findAll() {
		String companyID = AppContexts.user().companyId();
		return this.standardMenuRepository.findAll(companyID).stream().map(item -> StandardMenuDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	
	/**
	 * find all StandardMenu by companyID and have AfterLoginDisplayIndicator = true
	 * @param conpanyID
	 * @return List
	 */
	public List<StandardMenuDto> findAllWithAfterLoginDisplayIndicatorIsTrue() {
		String companyID = AppContexts.user().companyId();
		return this.standardMenuRepository.findAllWithAfterLoginDisplayIndicatorIsTrue(companyID).stream().map(item -> StandardMenuDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}

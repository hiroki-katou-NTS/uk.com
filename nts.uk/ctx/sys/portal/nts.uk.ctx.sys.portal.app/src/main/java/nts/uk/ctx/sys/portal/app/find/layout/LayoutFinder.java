package nts.uk.ctx.sys.portal.app.find.layout;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;

/**
 * @author LamDT
 */
@Stateless
public class LayoutFinder {

	@Inject
	private LayoutRepository layoutRepository;
	
	/**
	 * Find Layout by ID
	 * @param layoutID
	 * @return Optional Layout
	 */
	public Optional<LayoutDto> findLayout(String layoutID) {
		return layoutRepository.find(layoutID).map(item -> LayoutDto.fromDomain(item));
	}
}
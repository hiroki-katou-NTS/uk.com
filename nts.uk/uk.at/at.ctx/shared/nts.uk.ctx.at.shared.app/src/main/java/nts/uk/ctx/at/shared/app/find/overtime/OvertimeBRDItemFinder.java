package nts.uk.ctx.at.shared.app.find.overtime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.overtime.dto.OvertimeBRDItemDto;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeFinder.
 */
@Stateless
public class OvertimeBRDItemFinder {
	
	/** The repository. */
	@Inject
	private OutsideOTBRDItemRepository repository;
	
	/**
	 * Find by id.
	 *
	 * @return the list
	 */
	public List<OvertimeBRDItemDto> findAll() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		List<OutsideOTBRDItem> overtimeBRDItems = this.repository.findAll(companyId);

		return overtimeBRDItems.stream().map(domain -> {
			OvertimeBRDItemDto dto = new OvertimeBRDItemDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
	
	
}

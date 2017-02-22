package nts.uk.ctx.pr.core.app.find.itemmaster;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.ItemMasterDto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class ItemMasterFinder {
	@Inject
	private ItemMasterRepository itemMasterRepo;
	
	/**
	 * Find all item master by ave Pay Attribute
	 * @param avePayAtr
	 * @return
	 */
	public List<ItemMasterDto> findAllByItemAtr(ItemAtr itemAtr) {
		return this.itemMasterRepo.findAllByCategory(AppContexts.user().companyCode(), itemAtr.value).stream()
				.map(item -> ItemMasterDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	
	/**
	 * finder all items by company code and category type
	 * 
	 * @param companyCode
	 * @param categoryAtr
	 * @return
	 */
	public List<ItemMasterDto> findBy(int categoryAtr) {
		return this.itemMasterRepo.findAllByCategory(AppContexts.user().companyCode(), categoryAtr).stream()
				.map(item -> ItemMasterDto.fromDomain(item)).collect(Collectors.toList());
	}

	/**
	 * finder item by company code, category type, item code
	 * 
	 * @param companyCode
	 * @param categoryAtr
	 * @param itemCode
	 * @return
	 */
	public ItemMasterDto find(int categoryAtr, String itemCode) {
		Optional<ItemMasterDto> itemOp = this.itemMasterRepo.find(AppContexts.user().companyCode(), categoryAtr, itemCode)
				.map(item -> ItemMasterDto.fromDomain(item));
		
		return !itemOp.isPresent() ? null : itemOp.get();
	}
}

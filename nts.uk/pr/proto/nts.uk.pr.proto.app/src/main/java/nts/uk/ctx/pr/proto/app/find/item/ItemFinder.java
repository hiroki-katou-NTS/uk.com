package nts.uk.ctx.pr.proto.app.find.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * item finder
 *
 */
@RequestScoped
public class ItemFinder {
	@Inject
	private ItemMasterRepository repostirory;
	/**
	 * finder all items by company code and category type
	 * @param companyCode
	 * @param categoryAtr
	 * @return
	 */
	public List<ItemDto> getAllItems(int categoryAtr){
		return this.repostirory.getAllItemMaster(AppContexts.user().companyCode(), categoryAtr).stream()
				.map(item -> ItemDto.fromDomain(item))
				.collect(Collectors.toList());
	}
/**
 * finder item by company code, category type, item code
 * @param companyCode
 * @param categoryAtr
 * @param itemCode
 * @return
 */
	public Optional<ItemDto> getItem(int categoryAtr, String itemCode){
		return this.repostirory.getItemMaster(AppContexts.user().companyCode(), categoryAtr, itemCode)
				.map(item->ItemDto.fromDomain(item));
	}
}

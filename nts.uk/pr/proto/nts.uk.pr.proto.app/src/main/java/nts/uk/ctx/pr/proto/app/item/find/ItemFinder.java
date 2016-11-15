package nts.uk.ctx.pr.proto.app.item.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;

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
	 * @param categoryType
	 * @return
	 */
	public List<ItemDto> findAll(String companyCode, int categoryType){
		return this.repostirory.findAll(companyCode, categoryType).stream()
				.map(item -> ItemDto.fromDomain(item))
				.collect(Collectors.toList());
	}
/**
 * finder item by company code, category type, item code
 * @param companyCode
 * @param categoryType
 * @param itemCode
 * @return
 */
	public Optional<ItemDto> find(String companyCode, int categoryType, String itemCode){
		return this.repostirory.find(companyCode, categoryType, itemCode)
				.map(item->ItemDto.fromDomain(item));
	}
}

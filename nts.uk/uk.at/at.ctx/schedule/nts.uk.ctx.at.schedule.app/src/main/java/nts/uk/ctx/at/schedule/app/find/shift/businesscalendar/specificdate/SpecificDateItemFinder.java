package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.specificdate;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SpecificDateItemFinder {

	@Inject
	private SpecificDateItemRepository specificDateItemRepository;

	/**
	 * get All
	 * 
	 * @return
	 */
	public List<SpecificDateItemDto> getAllByCompany() {
		String companyId = AppContexts.user().companyId();
		List<SpecificDateItemDto> lst= specificDateItemRepository.getAll(companyId)
				.stream()
				.map(c -> toSpecificDateItemDto(c))
				.collect(Collectors.toList());
		return lst;
	}

	/**
	 * get Specific Date Item is USE or NOT
	 * 
	 * @return
	 */
	public List<SpecificDateItemDto> getSpecDateItemIsUse(int useAtr) {
		String companyId = AppContexts.user().companyId();
		List<SpecificDateItemDto> lst =  specificDateItemRepository.getSpecifiDateByUse(companyId, useAtr)
				.stream()
				.map(c -> toSpecificDateItemDto(c))
				.collect(Collectors.toList());
		return lst;
	}

	private SpecificDateItemDto toSpecificDateItemDto(SpecificDateItem specificDateItem) {
		return new SpecificDateItemDto(specificDateItem.getUseAtr().value,
				specificDateItem.getSpecificDateItemNo().v(), specificDateItem.getSpecificName().v());
	}

	/**
	 * get Specific Date Item By List Code
	 * @param lstSpecificDateItem
	 * @return
	 */
	public List<SpecificDateItemDto> getSpecificDateItemByListCode(List<Integer> lstSpecificDateItem){
		String companyId = AppContexts.user().companyId();
		return specificDateItemRepository.getSpecifiDateByListCode(companyId, lstSpecificDateItem)
				.stream()
				.map(c-> toSpecificDateItemDto(c))
				.collect(Collectors.toList());
	}
}

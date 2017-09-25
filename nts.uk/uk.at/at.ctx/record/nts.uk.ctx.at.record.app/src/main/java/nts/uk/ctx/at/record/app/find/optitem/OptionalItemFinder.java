/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemName;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OptionalItemFinder.
 */
@Stateless
public class OptionalItemFinder {

	/** The repo. */
	@Inject
	private OptionalItemRepository repo;

	/**
	 * Find.
	 *
	 * @return the optional item dto
	 */
	public OptionalItemDto find(String optionalItemNo) {
		OptionalItemDto dto = new OptionalItemDto();
		//OptionalItem dom = this.repo.find(AppContexts.user().companyId(), optionalItemNo).get();
		//dom.saveToMemento(dto);

		// TODO mock data
		dto.setCalcResultRange(new CalcResultRangeDto());
		dto.setOptionalItemNo(new OptionalItemNo("1"));
		dto.setOptionalItemName(new OptionalItemName("abcx"));
		return dto;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<OptionalItemHeaderDto> findAll() {
		//List<OptionalItem> list = this.repo.findAll(AppContexts.user().companyId());
		List<OptionalItem> list = new ArrayList<OptionalItem>();

		List<OptionalItemHeaderDto> listDto = list.stream().map(item -> {
			OptionalItemHeaderDto dto = new OptionalItemHeaderDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

		for (int i = 0; i < 10; i++) {
			OptionalItemHeaderDto dto = new OptionalItemHeaderDto();
			dto.setItemNo(""+i);
			dto.setItemName("item"+i);
			dto.setUsageAtr(1);
			dto.setPerformanceAtr(PerformanceAtr.DAILY_PERFORMANCE);
			listDto.add(dto);
		}

		return listDto;
	}
}

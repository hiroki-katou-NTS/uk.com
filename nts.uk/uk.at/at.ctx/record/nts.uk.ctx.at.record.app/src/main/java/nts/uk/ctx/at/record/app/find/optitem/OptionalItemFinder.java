/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.optitem.OptionalItem;

/**
 * The Class OptionalItemFinder.
 */
@Stateless
public class OptionalItemFinder {

	/** The repo. */
	// @Inject
	// private OptionalItemRepository repo;

	/**
	 * Find.
	 *
	 * @return the optional item dto
	 */
	public OptionalItemDto find() {
		// this.repo.find(AppContexts.user().companyId(), "");
		// TODO mock data
		OptionalItemDto dto = new OptionalItemDto();
		return dto;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<OptionalItemHeaderDto> findAll() {
		// this.repo.findAll(AppContexts.user().companyId());
		// TODO mock data
		List<OptionalItem> list = new ArrayList<OptionalItem>();

		List<OptionalItemHeaderDto> listDto = list.stream().map(item -> {
			OptionalItemHeaderDto dto = new OptionalItemHeaderDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

		return listDto;
	}
}

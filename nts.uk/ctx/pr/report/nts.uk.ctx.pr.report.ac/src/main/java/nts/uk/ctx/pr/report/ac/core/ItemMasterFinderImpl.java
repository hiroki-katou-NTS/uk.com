/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ac.core;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.core.finder.itemmaster.ItemMasterDto;
import nts.uk.ctx.pr.core.finder.itemmaster.ItemMasterPub;
import nts.uk.ctx.pr.report.app.itemmaster.find.ItemMasterCategory;
import nts.uk.ctx.pr.report.app.itemmaster.find.ItemMaterFinder;
import nts.uk.ctx.pr.report.app.itemmaster.find.MasterItemDto;

/**
 * The Class ItemMasterFinderImpl.
 */
@Stateless
public class ItemMasterFinderImpl implements ItemMaterFinder {
	
	/** The publisher. */
	@Inject
	private ItemMasterPub publisher;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.app.itemmaster.find.ItemMaterFinder
	 * #findAll(java.lang.String)
	 */
	@Override
	public List<MasterItemDto> findAll(String companyCode) {
		return this.publisher.findAll(companyCode).stream()
				.map(ItemMasterFinderImpl::convertToDto)
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.app.itemmaster.find.ItemMaterFinder
	 * #findByCodes(java.lang.String, java.util.List)
	 */
	@Override
	public List<MasterItemDto> findByCodes(String companyCode, List<String> codes) {
		if (CollectionUtil.isEmpty(codes)) {
			return Collections.emptyList();
		}
		return this.publisher.findBy(companyCode, codes).stream()
				.map(ItemMasterFinderImpl::convertToDto)
				.collect(Collectors.toList());
	}
	
	/**
	 * Convert to dto.
	 *
	 * @param dto the dto
	 * @return the master item dto
	 */
	private static MasterItemDto convertToDto(ItemMasterDto dto) {
		return MasterItemDto.builder()
				.code(dto.getCode())
				.name(dto.getName())
				.category(ItemMasterCategory.valueOf(dto.getCategoryAtr()))
				.build();
	}
}

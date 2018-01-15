/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.pubimp.optitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import optitem.OptionalItemExport;
import optitem.OptionalItemPub;

/**
 * The Class OptionalItemPubImp.
 */
@Stateless
public class OptionalItemPubImp implements OptionalItemPub {

	/** The opt item repo. */
	@Inject
	private OptionalItemRepository optItemRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see optitem.OptionalItemPub#getOptionalItems(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public List<OptionalItemExport> getOptionalItems(String companyId, List<Integer> optionalItemNos) {
		val convertedNos = optionalItemNos.stream().map(no -> new OptionalItemNo(no.toString()).v())
				.collect(Collectors.toList());
		return this.optItemRepo.findByListNos(companyId, convertedNos).stream()
				.map(optItem -> OptionalItemExport.builder().optionalItemName(optItem.getOptionalItemName().v())
						.optionalItemNo(Integer.parseInt(optItem.getOptionalItemNo().v())).build())
				.collect(Collectors.toList());
	}

}

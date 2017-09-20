/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;

/**
 * The Class JpaOptionalItemRepository.
 */
@Stateless
public class JpaOptionalItemRepository extends JpaRepository implements OptionalItemRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository#update(nts.uk.ctx
	 * .at.record.dom.optitem.OptionalItem)
	 */
	@Override
	public void update(OptionalItem dom) {
		System.out.print(dom);
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository#find(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public Optional<OptionalItem> find(String companyId, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository#findAll(java.lang
	 * .String)
	 */
	@Override
	public List<OptionalItem> findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}

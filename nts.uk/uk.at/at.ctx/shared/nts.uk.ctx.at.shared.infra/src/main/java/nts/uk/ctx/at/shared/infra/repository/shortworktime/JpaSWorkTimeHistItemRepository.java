/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItem;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItemPK;

/**
 * The Class JpaSWorkTimeHistItemRepository.
 */
@Stateless
public class JpaSWorkTimeHistItemRepository extends JpaRepository implements SWorkTimeHistItemRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#add(
	 * nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem)
	 */
	@Override
	public void add(ShortWorkTimeHistoryItem domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#update
	 * (nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem)
	 */
	@Override
	public void update(ShortWorkTimeHistoryItem domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the bshmt worktime hist item
	 */
	private BshmtWorktimeHistItem toEntity(ShortWorkTimeHistoryItem domain) {
		BshmtWorktimeHistItem entity = this.queryProxy()
				.find(new BshmtWorktimeHistItemPK(domain.getEmployeeId(), domain.getHistoryId()),
						BshmtWorktimeHistItem.class)
				.orElse(new BshmtWorktimeHistItem(new BshmtWorktimeHistItemPK()));
		JpaSWorkTimeHistItemSetMemento memento = new JpaSWorkTimeHistItemSetMemento(entity);
		domain.saveToMemento(memento);
		return entity;
	}
}

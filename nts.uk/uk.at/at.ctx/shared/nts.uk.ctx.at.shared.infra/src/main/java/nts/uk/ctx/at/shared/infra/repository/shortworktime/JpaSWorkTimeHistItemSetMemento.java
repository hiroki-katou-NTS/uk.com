/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemSetMemento;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.KshmtShorttimeTs;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.KshmtShorttimeTsPK;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.KshmtShorttimeHistItem;

/**
 * The Class JpaSWorkTimeHistItemSetMemento.
 */
public class JpaSWorkTimeHistItemSetMemento implements SWorkTimeHistItemSetMemento {

	/** The entity. */
	private KshmtShorttimeHistItem entity;

	/**
	 * Instantiates a new jpa S work time hist item set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSWorkTimeHistItemSetMemento(KshmtShorttimeHistItem entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemSetMemento#
	 * setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.entity.getKshmtShorttimeHistItemPK().setSid(employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemSetMemento#
	 * setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.entity.getKshmtShorttimeHistItemPK().setHistId(historyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemSetMemento#
	 * setChildCareAtr(nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr)
	 */
	@Override
	public void setChildCareAtr(ChildCareAtr childCareAtr) {
		this.entity.setChildCareAtr(childCareAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemSetMemento#
	 * setLstTimeZone(java.util.List)
	 */
	@Override
	public void setLstTimeSlot(List<SChildCareFrame> lstTimeZone) {
		List<KshmtShorttimeTs> newListKshmtShorttimeTs = new ArrayList<>();

		for (SChildCareFrame schildCareFrame : lstTimeZone) {

			KshmtShorttimeTsPK pk = new KshmtShorttimeTsPK(this.entity.getKshmtShorttimeHistItemPK().getSid(),
					this.entity.getKshmtShorttimeHistItemPK().getHistId(), schildCareFrame.getTimeSlot());
			KshmtShorttimeTs newEntity = new KshmtShorttimeTs(pk);
			if (this.entity.getLstKshmtShorttimeTs() != null){
				newEntity = this.entity.getLstKshmtShorttimeTs().stream().filter(
						entity -> entity.getKshmtShorttimeTsPK().getTimeNo() == schildCareFrame.getTimeSlot())
						.findFirst().orElse(new KshmtShorttimeTs(pk));
			}
			newEntity.setStrClock(schildCareFrame.getStartTime().v());
			newEntity.setEndClock(schildCareFrame.getEndTime().v());
			
			newListKshmtShorttimeTs.add(newEntity);
		}
		this.entity.setLstKshmtShorttimeTs(newListKshmtShorttimeTs);
	}

}

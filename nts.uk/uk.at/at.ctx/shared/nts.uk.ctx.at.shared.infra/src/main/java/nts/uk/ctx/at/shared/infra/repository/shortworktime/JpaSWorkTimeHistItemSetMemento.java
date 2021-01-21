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
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtSchildCareFramePK;
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
		this.entity.getBshmtWorktimeHistItemPK().setSid(employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemSetMemento#
	 * setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.entity.getBshmtWorktimeHistItemPK().setHistId(historyId);
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
		List<KshmtShorttimeTs> newListBshmtSchildCareFrame = new ArrayList<>();

		for (SChildCareFrame schildCareFrame : lstTimeZone) {

			BshmtSchildCareFramePK pk = new BshmtSchildCareFramePK(this.entity.getBshmtWorktimeHistItemPK().getSid(),
					this.entity.getBshmtWorktimeHistItemPK().getHistId(), schildCareFrame.getTimeSlot());
			KshmtShorttimeTs newEntity = new KshmtShorttimeTs(pk);
			if (this.entity.getLstBshmtSchildCareFrame() != null){
				newEntity = this.entity.getLstBshmtSchildCareFrame().stream().filter(
						entity -> entity.getBshmtSchildCareFramePK().getTimeNo() == schildCareFrame.getTimeSlot())
						.findFirst().orElse(new KshmtShorttimeTs(pk));
			}
			newEntity.setStrClock(schildCareFrame.getStartTime().v());
			newEntity.setEndClock(schildCareFrame.getEndTime().v());
			
			newListBshmtSchildCareFrame.add(newEntity);
		}
		this.entity.setLstBshmtSchildCareFrame(newListBshmtSchildCareFrame);
	}

}

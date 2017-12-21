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
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtSchildCareFrame;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtSchildCareFramePK;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItem;

/**
 * The Class JpaSWorkTimeHistItemSetMemento.
 */
public class JpaSWorkTimeHistItemSetMemento implements SWorkTimeHistItemSetMemento {

	/** The entity. */
	private BshmtWorktimeHistItem entity;

	/**
	 * Instantiates a new jpa S work time hist item set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSWorkTimeHistItemSetMemento(BshmtWorktimeHistItem entity) {
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
		List<BshmtSchildCareFrame> newListBshmtSchildCareFrame = new ArrayList<>();

		for (SChildCareFrame schildCareFrame : lstTimeZone) {

			BshmtSchildCareFramePK pk = new BshmtSchildCareFramePK(this.entity.getBshmtWorktimeHistItemPK().getSid(),
					this.entity.getBshmtWorktimeHistItemPK().getHistId(), schildCareFrame.getTimeSlot());
			BshmtSchildCareFrame newEntity = new BshmtSchildCareFrame(pk);
			if (this.entity.getLstBshmtSchildCareFrame() != null){
				newEntity = this.entity.getLstBshmtSchildCareFrame().stream().filter(
						entity -> entity.getBshmtSchildCareFramePK().getTimeNo() == schildCareFrame.getTimeSlot())
						.findFirst().orElse(new BshmtSchildCareFrame(pk));
			}
			newEntity.setStrClock(schildCareFrame.getStartTime().v());
			newEntity.setEndClock(schildCareFrame.getEndTime().v());
			
			newListBshmtSchildCareFrame.add(newEntity);
		}
		this.entity.setLstBshmtSchildCareFrame(newListBshmtSchildCareFrame);
	}

}

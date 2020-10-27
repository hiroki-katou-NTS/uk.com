/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfItemSelect;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfItemSelectPK;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.AddSubOperator;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SelectedAttendanceItemGetMemento;

/**
 * The Class JpaSelectedAttendanceItemGetMemento.
 */
public class JpaSelectedAttendanceItemGetMemento implements SelectedAttendanceItemGetMemento{
	
	/** The entity. */
	private KrcmtAnyfItemSelect entity;

	/**
	 * Instantiates a new jpa selected attendance item get memento.
	 *
	 * @param entity the entity
	 */
	public JpaSelectedAttendanceItemGetMemento(KrcmtAnyfItemSelect entity) {
		if(entity.getKrcmtAnyfItemSelectPK() == null){
			entity.setKrcmtAnyfItemSelectPK(new KrcmtAnyfItemSelectPK());
		}
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.SelectedAttendanceItemGetMemento#getAttItemId()
	 */
	@Override
	public int getAttItemId() {
		return this.entity.getKrcmtAnyfItemSelectPK().getAttendanceItemId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.SelectedAttendanceItemGetMemento#getOperator()
	 */
	@Override
	public AddSubOperator getOperator() {
		return AddSubOperator.valueOf(this.entity.getOperator());
	}

}

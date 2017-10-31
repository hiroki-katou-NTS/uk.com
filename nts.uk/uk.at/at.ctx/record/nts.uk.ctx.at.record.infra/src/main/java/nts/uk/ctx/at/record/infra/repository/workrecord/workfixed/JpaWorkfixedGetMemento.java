/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.workfixed;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.ConfirmClsStatus;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixedPK;

/**
 * The Class JpaWorkfixedGetMemento.
 */
public class JpaWorkfixedGetMemento implements WorkFixedGetMemento {
	
	/** The type value. */
	private KrcstWorkFixed typeValue;
	
	/**
	 * Instantiates a new jpa workfixed get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaWorkfixedGetMemento(KrcstWorkFixed typeValue){
		this.typeValue = typeValue;
		if (this.typeValue.getKrcstWorkFixedPK() == null) {
			this.typeValue.setKrcstWorkFixedPK(new KrcstWorkFixedPK());
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getClosureId()
	 */
	@Override
	public Integer getClosureId() {	
		return this.typeValue.getKrcstWorkFixedPK().getClosureId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getConfirmPId()
	 */
	@Override
	public String getConfirmPId() {
		return this.typeValue.getConfirmPid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getWorkPlaceId()
	 */
	@Override
	public String getWorkPlaceId() {
		return this.typeValue.getKrcstWorkFixedPK().getWkpid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getProcessDate()
	 */
	@Override
	public Integer getProcessDate() {
		return this.typeValue.getProcessYm();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getConfirmClsStatus()
	 */
	@Override
	public ConfirmClsStatus getConfirmClsStatus() {
		return ConfirmClsStatus.valueOf(Integer.valueOf(this.typeValue.getConfirmCls()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getFixedDate()
	 */
	@Override
	public GeneralDate getFixedDate() {
		GeneralDate result = GeneralDate.legacyDate(this.typeValue.getFixedDate());
		return result;
	}
}

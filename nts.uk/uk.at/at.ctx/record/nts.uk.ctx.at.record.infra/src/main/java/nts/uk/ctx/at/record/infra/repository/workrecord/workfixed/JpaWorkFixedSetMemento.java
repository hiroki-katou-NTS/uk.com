/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.workfixed;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.ConfirmClsStatus;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixedPK;

/**
 * The Class JpaWorkFixedSetMemento.
 */
public class JpaWorkFixedSetMemento implements WorkFixedSetMemento {

	/** The typed value. */
	private KrcstWorkFixed typedValue;

	/**
	 * Instantiates a new jpa work fixed set memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaWorkFixedSetMemento(KrcstWorkFixed typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getKrcstWorkFixedPK() == null) {
			this.typedValue.setKrcstWorkFixedPK(new KrcstWorkFixedPK());
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setClosureId(java.lang.Short)
	 */
	@Override
	public void setClosureId(Integer closureId) {
		this.typedValue.getKrcstWorkFixedPK().setClosureId(closureId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setConfirmPId(java.lang.String)
	 */
	@Override
	public void setConfirmPId(String confirmPid) {
		if (confirmPid == null) {
			return;
		}
		this.typedValue.setConfirmPid(confirmPid);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setWorkplaceId(java.lang.String)
	 */
	@Override
	public void setWorkplaceId(String wkpId) {
		this.typedValue.getKrcstWorkFixedPK().setWkpid(wkpId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setConfirmClsStatus(nts.uk.ctx.at.record.dom.workrecord.workfixed.ConfirmClsStatus)
	 */
	@Override
	public void setConfirmClsStatus(ConfirmClsStatus confirmClsStatus) {
		this.typedValue.setConfirmCls(confirmClsStatus.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setFixedDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setFixedDate(GeneralDate fixedDate) {
		if (fixedDate == null) {
			return;
		}
		this.typedValue.setFixedDate(fixedDate.date());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setProcessDate(java.lang.Integer)
	 */
	@Override
	public void setProcessYm(YearMonth processDate) {
		this.typedValue.setProcessYm(processDate.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setCid(java.lang.String)
	 */
	@Override
	public void setCid(String cid) {
		this.typedValue.getKrcstWorkFixedPK().setCid(cid);	
	}
}

package nts.uk.ctx.at.record.infra.repository.workrecord.goout;

import nts.uk.ctx.at.record.dom.workrecord.goout.MaxGoOut;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.goout.KrcstOutManage;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * The Class JpaOutManageGetMemento.
 *
 * @author hoangdd
 */
public class JpaOutManageGetMemento implements OutManageGetMemento{

	/** The krcst out manage. */
	private KrcstOutManage krcstOutManage;
	
	/**
	 * Instantiates a new jpa out manage get memento.
	 *
	 * @param entity the entity
	 */
	public JpaOutManageGetMemento(KrcstOutManage entity) {
		this.krcstOutManage = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.krcstOutManage.getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageGetMemento#getMaxUsage()
	 */
	@Override
	public MaxGoOut getMaxUsage() {
		return new MaxGoOut(this.krcstOutManage.getMaxUsage().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageGetMemento#getInitValueReasonGoOut()
	 */
	@Override
	public GoingOutReason getInitValueReasonGoOut() {
		return GoingOutReason.valueOf(this.krcstOutManage.getInitValueReasonGoOut().intValue());
	}

}


package nts.uk.ctx.at.record.infra.repository.workrecord.goout;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.goout.KrcstOutManage;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaOutManageSetMemento.
 *
 * @author hoangdd
 */
public class JpaOutManageSetMemento implements OutManageSetMemento{

	/** The krcst out manage. */
	private KrcstOutManage krcstOutManage; 
	
	/**
	 * Instantiates a new jpa out manage set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOutManageSetMemento(KrcstOutManage entity) {
		this.krcstOutManage = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		if (companyID == null) {
			this.krcstOutManage.setCid(AppContexts.user().companyId());
		} else {
			this.krcstOutManage.setCid(companyID);
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageSetMemento#setMaxUsage(int)
	 */
	@Override
	public void setMaxUsage(int maxUsage) {
		this.krcstOutManage.setMaxUsage(new BigDecimal(maxUsage));
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageSetMemento#setInitValueReasonGoOut(int)
	 */
	@Override
	public void setInitValueReasonGoOut(int initValueReasonGoOut) {
		this.krcstOutManage.setInitValueReasonGoOut(new BigDecimal(initValueReasonGoOut));
	}

}


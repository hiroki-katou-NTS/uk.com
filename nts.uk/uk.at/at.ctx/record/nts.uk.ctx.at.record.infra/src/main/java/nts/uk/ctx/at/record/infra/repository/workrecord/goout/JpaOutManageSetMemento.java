package nts.uk.ctx.at.record.infra.repository.workrecord.goout;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.goout.KrcmtGooutMng;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaOutManageSetMemento.
 *
 * @author hoangdd
 */
public class JpaOutManageSetMemento implements OutManageSetMemento{

	/** The krcst out manage. */
	private KrcmtGooutMng krcmtGooutMng; 
	
	/**
	 * Instantiates a new jpa out manage set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOutManageSetMemento(KrcmtGooutMng entity) {
		this.krcmtGooutMng = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		if (companyID == null) {
			this.krcmtGooutMng.setCid(AppContexts.user().companyId());
		} else {
			this.krcmtGooutMng.setCid(companyID);
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageSetMemento#setMaxUsage(int)
	 */
	@Override
	public void setMaxUsage(int maxUsage) {
		this.krcmtGooutMng.setMaxUsage(new BigDecimal(maxUsage));
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.goout.OutManageSetMemento#setInitValueReasonGoOut(int)
	 */
	@Override
	public void setInitValueReasonGoOut(int initValueReasonGoOut) {
		this.krcmtGooutMng.setInitValueReasonGoOut(new BigDecimal(initValueReasonGoOut));
	}

}


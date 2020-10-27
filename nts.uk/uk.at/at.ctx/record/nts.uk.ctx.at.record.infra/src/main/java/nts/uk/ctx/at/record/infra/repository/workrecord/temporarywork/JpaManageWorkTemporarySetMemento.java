/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.temporarywork;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporarySetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork.KrcmtTemporaryMng;

/**
 * @author hoangdd
 *
 */
public class JpaManageWorkTemporarySetMemento implements ManageWorkTemporarySetMemento{

	private KrcmtTemporaryMng krcmtTemporaryMng;
	
	/**
	 * @param krcmtTemporaryMng
	 */
	public JpaManageWorkTemporarySetMemento(KrcmtTemporaryMng krcmtTemporaryMng) {
		this.krcmtTemporaryMng = krcmtTemporaryMng;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporarySetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		this.krcmtTemporaryMng.setCid(companyID);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporarySetMemento#setMaxUsage(int)
	 */
	@Override
	public void setMaxUsage(int maxUsage) {
		this.krcmtTemporaryMng.setMaxUsage(new BigDecimal(maxUsage));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporarySetMemento#setTimeTreatTemporarySame(int)
	 */
	@Override
	public void setTimeTreatTemporarySame(int time) {
		this.krcmtTemporaryMng.setTimeTreatTempSame(new BigDecimal(time));
	}

}


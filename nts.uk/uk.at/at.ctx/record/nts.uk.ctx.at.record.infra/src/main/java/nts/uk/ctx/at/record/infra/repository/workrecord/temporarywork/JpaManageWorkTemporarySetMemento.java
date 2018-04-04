/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.temporarywork;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporarySetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork.KrcstManageWorkTemp;

/**
 * @author hoangdd
 *
 */
public class JpaManageWorkTemporarySetMemento implements ManageWorkTemporarySetMemento{

	private KrcstManageWorkTemp krcstManageWorkTemp;
	
	/**
	 * @param krcstManageWorkTemp
	 */
	public JpaManageWorkTemporarySetMemento(KrcstManageWorkTemp krcstManageWorkTemp) {
		this.krcstManageWorkTemp = krcstManageWorkTemp;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporarySetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		this.krcstManageWorkTemp.setCid(companyID);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporarySetMemento#setMaxUsage(int)
	 */
	@Override
	public void setMaxUsage(int maxUsage) {
		this.krcstManageWorkTemp.setMaxUsage(new BigDecimal(maxUsage));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporarySetMemento#setTimeTreatTemporarySame(int)
	 */
	@Override
	public void setTimeTreatTemporarySame(int time) {
		this.krcstManageWorkTemp.setTimeTreatTempSame(new BigDecimal(time));
	}

}


package nts.uk.ctx.at.shared.infra.repository.entranceexit;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitSetMemento;
import nts.uk.ctx.at.shared.infra.entity.entranceexit.KshmtGateMng;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaManageEntryExitSetMemento.
 *
 * @author hoangdd
 */
public class JpaManageEntryExitSetMemento implements ManageEntryExitSetMemento{

	/** The kshst manage entry exit. */
	private KshmtGateMng kshmtGateMng;

	/**
	 * Instantiates a new jpa manage entry exit set memento.
	 *
	 * @param kshmtGateMng the kshst manage entry exit
	 */
	public JpaManageEntryExitSetMemento(KshmtGateMng kshmtGateMng) {
		this.kshmtGateMng = kshmtGateMng;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		if (companyID == null) {
			this.kshmtGateMng.setCid(AppContexts.user().companyId());
		} else {
			this.kshmtGateMng.setCid(companyID);
		}
		
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitSetMemento#setUseCls(int)
	 */
	@Override
	public void setUseCls(int useCls) {
		this.kshmtGateMng.setUseCls(new BigDecimal(useCls));
	}

}


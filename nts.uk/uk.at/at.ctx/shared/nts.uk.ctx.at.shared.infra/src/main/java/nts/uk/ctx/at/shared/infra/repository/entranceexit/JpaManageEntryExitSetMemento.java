package nts.uk.ctx.at.shared.infra.repository.entranceexit;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitSetMemento;
import nts.uk.ctx.at.shared.infra.entity.entranceexit.KshstManageEntryExit;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaManageEntryExitSetMemento.
 *
 * @author hoangdd
 */
public class JpaManageEntryExitSetMemento implements ManageEntryExitSetMemento{

	/** The kshst manage entry exit. */
	private KshstManageEntryExit kshstManageEntryExit;

	/**
	 * Instantiates a new jpa manage entry exit set memento.
	 *
	 * @param kshstManageEntryExit the kshst manage entry exit
	 */
	public JpaManageEntryExitSetMemento(KshstManageEntryExit kshstManageEntryExit) {
		this.kshstManageEntryExit = kshstManageEntryExit;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		if (companyID == null) {
			this.kshstManageEntryExit.setCid(AppContexts.user().companyId());
		} else {
			this.kshstManageEntryExit.setCid(companyID);
		}
		
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitSetMemento#setUseCls(int)
	 */
	@Override
	public void setUseCls(int useCls) {
		this.kshstManageEntryExit.setUseCls(new BigDecimal(useCls));
	}

}


package nts.uk.ctx.at.shared.infra.repository.entranceexit;

import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitGetMemento;
import nts.uk.ctx.at.shared.infra.entity.entranceexit.KshstManageEntryExit;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaManageEntryExitGetMemento.
 *
 * @author hoangdd
 */
public class JpaManageEntryExitGetMemento implements ManageEntryExitGetMemento{

	/** The kshst manage entry exit. */
	private KshstManageEntryExit kshstManageEntryExit;
	
	/**
	 * Instantiates a new jpa manage entry exit get memento.
	 *
	 * @param kshstManageEntryExit the kshst manage entry exit
	 */
	public JpaManageEntryExitGetMemento(KshstManageEntryExit kshstManageEntryExit) {
		this.kshstManageEntryExit = kshstManageEntryExit;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.kshstManageEntryExit.getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitGetMemento#getUseCls()
	 */
	@Override
	public NotUseAtr getUseCls() {
		return NotUseAtr.valueOf(this.kshstManageEntryExit.getUseCls().intValue());
	}

}


package nts.uk.ctx.at.shared.pubimp.mastercopy.handler;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.infra.repository.mastercopy.handler.KmfmtRetentionYearlyDataCopyHandler;
import nts.uk.ctx.at.shared.pub.mastercopy.handler.CopyDataPub;

@Stateless
public class CopyDataPubImpl implements CopyDataPub {

	/** The copy handler. */
	@Inject
	KmfmtRetentionYearlyDataCopyHandler copyHandler;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.pub.mastercopy.handler.CopyDataPub#doCopy()
	 */
	@Override
	public void doCopy() {
		copyHandler.doCopy();		
	}
}

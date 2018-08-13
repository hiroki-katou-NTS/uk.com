package nts.uk.ctx.bs.employee.pubimp.mastercopy.handler;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.infra.repository.mastercopy.handler.BsystTempAbsenceFrameDataCopyHandler;
import nts.uk.ctx.bs.employee.pub.mastercopy.handler.CopyDataPub;

@Stateless
public class CopyDataPubImpl implements CopyDataPub {
	
	/** The copy handler. */
	@Inject
	BsystTempAbsenceFrameDataCopyHandler copyHandler;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.pub.mastercopy.handler.CopyDataPub#doCopy()
	 */
	@Override
	public void doCopy() {
		copyHandler.doCopy();
	}

}

package nts.uk.ctx.bs.employee.pubimp.mastercopy.handler;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.dom.mastercopy.CopyMethod;
import nts.uk.ctx.bs.employee.infra.repository.mastercopy.handler.BsystTempAbsenceFrameDataCopyHandler;
import nts.uk.ctx.bs.employee.pub.mastercopy.handler.CopyDataPub;

@Stateless
public class CopyDataPubImpl implements CopyDataPub {
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.pub.mastercopy.handler.CopyDataPub#doCopy()
	 */
	@Override
	public void doCopy(int copyMethod,String companyId) {
		BsystTempAbsenceFrameDataCopyHandler copyHandler = new BsystTempAbsenceFrameDataCopyHandler(CopyMethod.valueOf(copyMethod), companyId);
		copyHandler.doCopy();
	}

}

package nts.uk.ctx.at.request.pubimp.mastercopy;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.request.infra.repository.mastercopy.handler.KrqstContentOfRemandMailDataCopyHandler;
import nts.uk.ctx.at.request.pub.mastercopy.CopyDataPub;

/**
 * The Class CopyDataPubImp.
 */
@Stateless
public class CopyDataPubImp implements CopyDataPub {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.pub.mastercopy.CopyDataPub#doCopy(CopyMethod, int, String)
	 */
	@Override
	public void doCopy(int copyMethod, String companyId) {
		DataCopyHandler handler = new KrqstContentOfRemandMailDataCopyHandler(CopyMethod.valueOf(copyMethod), companyId); 
		handler.doCopy();
	}

}

package nts.uk.ctx.sys.assist.ac.mastercopy;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.mastercopy.PpemtNewLayoutCopyAdapter;

/**
 * The Class PpemtNewLayoutCopyAdapterImp.
 */
@Stateless
public class PpemtNewLayoutCopyAdapterImp implements PpemtNewLayoutCopyAdapter{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.PpemtNewLayoutCopyAdapter#copy(java.lang.String, int)
	 */
	@Override
	public void copy(String companyId, int copyMethod) {		
		//Call pub
	}

}

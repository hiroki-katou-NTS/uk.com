package nts.uk.ctx.sys.assist.ac.mastercopy;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.mastercopy.PpemtPInfoItemGroupCopyAdapter;

/**
 * The Class PpemtPInfoItemGroupCopyAdapterImp.
 */
@Stateless
public class PpemtPInfoItemGroupCopyAdapterImp implements PpemtPInfoItemGroupCopyAdapter{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.PpemtPInfoItemGroupCopyAdapter#copy(java.lang.String, int)
	 */
	@Override
	public void copy(String companyId, int copyMethod) {
		
		//Call pub
	}

}

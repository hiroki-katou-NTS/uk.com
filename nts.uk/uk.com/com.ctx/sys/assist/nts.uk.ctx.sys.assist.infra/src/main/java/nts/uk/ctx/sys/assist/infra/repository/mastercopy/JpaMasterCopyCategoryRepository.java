package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategory;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryRepository;

/**
 * The Class JpaMasterCopyCategoryRepository.
 */
@Stateless
public class JpaMasterCopyCategoryRepository extends JpaRepository implements MasterCopyCategoryRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryRepository#findAllMasterCopyCategory(java.lang.String)
	 */
	@Override
	public List<MasterCopyCategory> findAllMasterCopyCategory(String companyId) {
		List<MasterCopyCategory> listDomain = new ArrayList<MasterCopyCategory>();
		return listDomain;
	}

}

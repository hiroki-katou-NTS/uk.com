package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.List;

/**
 * The Interface MasterCopyCategoryRepository.
 */
public interface MasterCopyCategoryRepository {
	
	/**
	 * Find all master copy category.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<MasterCopyCategory> findAllMasterCopyCategory(String companyId);
}

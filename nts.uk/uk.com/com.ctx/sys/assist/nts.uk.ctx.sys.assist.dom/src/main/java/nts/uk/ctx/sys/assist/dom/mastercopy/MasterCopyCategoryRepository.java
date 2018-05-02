package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.List;

/**
 * The Interface MasterCopyCategoryRepository.
 */
public interface MasterCopyCategoryRepository {
	
	/**
	 * Find all master copy category.
	 *
	 * @return the list
	 */
	List<MasterCopyCategory> findAllMasterCopyCategory();
}

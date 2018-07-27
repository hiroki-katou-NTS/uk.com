package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.List;
import java.util.Optional;

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
	
	/**
	 * Find by master copy id.
	 *
	 * @param masterCopyId the master copy id
	 * @return the master copy category
	 */
	Optional<MasterCopyCategory> findByMasterCopyId(String masterCopyId);
}

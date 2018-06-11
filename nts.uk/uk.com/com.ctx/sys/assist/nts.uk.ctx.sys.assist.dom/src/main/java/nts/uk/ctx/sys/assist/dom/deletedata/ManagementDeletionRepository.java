package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.Optional;
import java.util.List;

/**
 * データ削除動作管理
 */

public interface ManagementDeletionRepository {

	List<ManagementDeletion> getAllManagementDeletion();

	Optional<ManagementDeletion> getManagementDeletionById(String delId);

	/**
	 * @author nam.lh
	 *
	 */
	void add(ManagementDeletion domain);

	/**
	 * @param delId
	 * @param operatingCondition
	 * @param categoryTotalCount
	 */
	void updateTotalCatCount(String delId, int categoryTotalCount);
	
	/**
	 * @param delId
	 * @param operatingCondition
	 * @param categoryCount
	 */
	void updateCatCountAnCond(String delId, int categoryCount,
			OperatingCondition operatingCondition);
	
	/**
	 * 
	 * @param managementDeletion
	 */
	void update(ManagementDeletion managementDeletion);
	
	/**
	 * @param delId
	 * @param categoryCount
	 */
	void updateCatCount(String delId, int categoryCount);
	
	/**
	 * @param delId
	 * @param operatingCondition
	 */
	void updateOperationCond(String delId, OperatingCondition operatingCondition);
	
	/**
	 * @param delId
	 * @param interruptedFlg
	 * @param operatingCondition
	 */
	void setInterruptDeleting(String delId, int interruptedFlg , OperatingCondition operatingCondition);
	
	/**
	 * @param delId
	 */
	void remove(String delId);

}

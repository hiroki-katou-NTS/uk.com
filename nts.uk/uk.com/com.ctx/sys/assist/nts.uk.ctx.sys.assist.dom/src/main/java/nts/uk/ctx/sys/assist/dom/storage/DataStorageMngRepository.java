package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;
import java.util.List;

/**
 * データ保存動作管理
 */

public interface DataStorageMngRepository {

	List<DataStorageMng> getAllDataStorageMng();

	Optional<DataStorageMng> getDataStorageMngById(String storeProcessingId);

	/**
	 * @author nam.lh
	 *
	 */
	void add(DataStorageMng domain);

	/**
	 * @param storeProcessingId
	 * @param operatingCondition
	 * @param categoryCount
	 * @param categoryTotalCount
	 */
	void update(String storeProcessingId, int categoryTotalCount, int categoryCount,
			OperatingCondition operatingCondition);
	void update(String storeProcessingId, OperatingCondition operatingCondition);
	
	void update(DataStorageMng domain);
	
	void remove(String storeProcessingId);

}

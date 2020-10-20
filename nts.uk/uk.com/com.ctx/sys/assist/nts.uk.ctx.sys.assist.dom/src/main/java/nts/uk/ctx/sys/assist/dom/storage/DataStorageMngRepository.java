package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;

/**
 * データ保存動作管理
 */

public interface DataStorageMngRepository {

	List<DataStorageMng> getAllDataStorageMng();
	
//	List<DataStorageMng> getDataStorageMng(
//			 String cid,
//			 GeneralDateTime startDateOperator,
//			 GeneralDateTime endDateOperator,
//			 List<String>  listOperatorEmployeeId
//		);

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
	boolean update(String storeProcessingId, int categoryTotalCount, int categoryCount,
			OperatingCondition operatingCondition);
	boolean update(String storeProcessingId, OperatingCondition operatingCondition);
	
	boolean increaseCategoryCount(String storeProcessingId);
	
	void update(String storeProcessingId, NotUseAtr doNotInterrupt);
	
	void remove(String storeProcessingId);

}

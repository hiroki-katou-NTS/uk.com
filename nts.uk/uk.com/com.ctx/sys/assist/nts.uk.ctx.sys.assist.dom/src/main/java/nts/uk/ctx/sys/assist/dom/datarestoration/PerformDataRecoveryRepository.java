package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

/**
 * データ復旧の実行
 */
public interface PerformDataRecoveryRepository {

	Optional<PerformDataRecovery> getPerformDatRecoverById(String dataRecoveryProcessId);

	void add(PerformDataRecovery domain);

	void update(PerformDataRecovery domain);

	void remove(String dataRecoveryProcessId);
	
	List<TableList> getByStorageRangeSaved(String categoryId,String storageRangeSaved);
	
	List<Target> findByDataRecoveryId(String dataRecoveryProcessId);
	
	Optional<TableList> getByInternal(String internalFileName, String dataRecoveryProcessId);
	
	int countDataExitTableByVKeyUp(Map<String, String> filedWhere, String tableName);
	
	void deleteDataExitTableByVkey(Map<String, String> filedWhere, String tableName);
	
	void insertDataTable(Map<String, String> dataInsertDB, String tableName);


	List<TableList> getByRecoveryProcessingId(String dataRecoveryProcessId);

	List<TableList> getAllTableList();
	
	void deleteEmployeeHis(String tableName, String whereCid, String whereSid ,String cid, String employeeId);
	
	void addTargetEmployee(Target domain);
	
	void updatePerformDataRecoveryById(String dataRecoveryProcessId);
}

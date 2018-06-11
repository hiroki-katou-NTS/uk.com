package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.List;
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

	List<TableList> getByRecoveryProcessingId(String dataRecoveryProcessId);

	List<TableList> getAllTableList();
}

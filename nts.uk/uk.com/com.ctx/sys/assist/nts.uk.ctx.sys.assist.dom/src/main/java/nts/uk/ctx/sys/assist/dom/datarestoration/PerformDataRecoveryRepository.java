package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;

/**
 * データ復旧の実行
 */
public interface PerformDataRecoveryRepository {

	Optional<PerformDataRecovery> getPerformDatRecoverById(String dataRecoveryProcessId);

	void add(PerformDataRecovery domain);

	void update(PerformDataRecovery domain);

	void remove(String dataRecoveryProcessId);
}

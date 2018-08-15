package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;

/**
 * データ復旧の結果
 */
public interface DataRecoveryResultRepository {

	Optional<DataRecoveryResult> getDataRecoverResultById(String dataRecoveryProcessId);

	void add(DataRecoveryResult domain);

	void update(DataRecoveryResult domain);

	void remove(String dataRecoveryProcessId);
	
	void updateEndDateTimeExecutionResult(String dataRecoveryProcessId, DataRecoveryOperatingCondition dataRecoveryOperatingCondition);
}

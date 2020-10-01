package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;

/**
 * データ復旧の結果
 */
public interface DataRecoveryResultRepository {

	Optional<DataRecoveryResult> getDataRecoverResultById(String dataRecoveryProcessId);

	List<DataRecoveryResult> getResultOfRestoration(
			 String cid,
			 GeneralDateTime startDateOperator,
			 GeneralDateTime endDateOperator,
			 List<String>  listOperatorEmployeeId
		);
	
	void add(DataRecoveryResult domain);

	void update(DataRecoveryResult domain);

	void remove(String dataRecoveryProcessId);
	
	void updateEndDateTimeExecutionResult(String dataRecoveryProcessId, DataRecoveryOperatingCondition dataRecoveryOperatingCondition);
}

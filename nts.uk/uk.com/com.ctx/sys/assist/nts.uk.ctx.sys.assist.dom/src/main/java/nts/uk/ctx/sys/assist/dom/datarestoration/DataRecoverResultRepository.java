package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;
import java.util.List;

/**
* データ復旧の結果
*/
public interface DataRecoverResultRepository
{

    List<DataRecoverResult> getAllDataRecoverResult();

    Optional<DataRecoverResult> getDataRecoverResultById(String dataRecoveryProcessId);

    void add(DataRecoverResult domain);

    void update(DataRecoverResult domain);

    void remove(String dataRecoveryProcessId);

}

package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;
import java.util.List;

/**
* データ復旧の結果ログ
*/
public interface DataRecoverLogRepository
{

    List<DataRecoverLog> getAllDataRecoverLog();

    Optional<DataRecoverLog> getDataRecoverLogById(String recoveryProcessId, String target);

    void add(DataRecoverLog domain);

    void update(DataRecoverLog domain);

    void remove(String recoveryProcessId, String target);

}

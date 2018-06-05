package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;
import java.util.List;

/**
* データ復旧動作管理
*/
public interface DataRecoveryMngRepository
{

    List<DataRecoveryMng> getAllDataRecoveryMng();

    Optional<DataRecoveryMng> getDataRecoveryMngById(String dataRecoveryProcessId);

    void add(DataRecoveryMng domain);

    void update(DataRecoveryMng domain);

    void remove(String dataRecoveryProcessId);

}

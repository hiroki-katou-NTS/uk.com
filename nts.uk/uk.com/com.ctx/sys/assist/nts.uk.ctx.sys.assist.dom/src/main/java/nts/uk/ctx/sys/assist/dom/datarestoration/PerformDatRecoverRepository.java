package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;
import java.util.List;

/**
* データ復旧の実行
*/
public interface PerformDatRecoverRepository
{

    List<PerformDatRecover> getAllPerformDatRecover();

    Optional<PerformDatRecover> getPerformDatRecoverById(String dataRecoveryProcessId);

    void add(PerformDatRecover domain);

    void update(PerformDatRecover domain);

    void remove(String dataRecoveryProcessId);

}

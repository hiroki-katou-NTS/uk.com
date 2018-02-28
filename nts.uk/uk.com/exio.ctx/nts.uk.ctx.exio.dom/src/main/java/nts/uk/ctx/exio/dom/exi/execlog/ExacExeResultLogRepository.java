package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.Optional;
import java.util.List;

/**
* 外部受入実行結果ログ
*/
public interface ExacExeResultLogRepository
{

    List<ExacExeResultLog> getAllExacExeResultLog();

    Optional<ExacExeResultLog> getExacExeResultLogById(String cid, String conditionSetCd, String externalProcessId);

    void add(ExacExeResultLog domain);

    void update(ExacExeResultLog domain);

    void remove(String cid, String conditionSetCd, String externalProcessId);
    
    List<ExacExeResultLog> getExacExeResultLogByProcessId(String externalProcessId);

}

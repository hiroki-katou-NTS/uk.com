package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.Optional;
import java.util.List;

/**
* 外部受入エラーログ
*/
public interface ExacErrorLogRepository
{

    List<ExacErrorLog> getAllExacErrorLog();

    Optional<ExacErrorLog> getExacErrorLogById(int logSeqNumber, String cid, String externalProcessId);

    void add(ExacErrorLog domain);

    void update(ExacErrorLog domain);

    void remove(int logSeqNumber, String cid, String externalProcessId);
    
    List<ExacErrorLog> getExacErrorLogByProcessId(String externalProcessId);
    
    void addList(List<ExacErrorLog> domain);
}

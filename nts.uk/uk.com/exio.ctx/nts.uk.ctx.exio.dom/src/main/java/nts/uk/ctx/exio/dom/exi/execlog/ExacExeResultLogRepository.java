package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import java.util.List;

/**
* 外部受入実行結果ログ
*/
public interface ExacExeResultLogRepository
{

    List<ExacExeResultLog> getAllExacExeResultLog(String cid, List<Integer> listSystem, GeneralDateTime startDate, GeneralDateTime endDate);

    Optional<ExacExeResultLog> getExacExeResultLogById(String cid, String conditionSetCd, String externalProcessId);

    void add(ExacExeResultLog domain);

    void update(ExacExeResultLog domain);

    void remove(String cid, String conditionSetCd, String externalProcessId);
    
    List<ExacExeResultLog> getExacExeResultLogByProcessId(String externalProcessId);

}

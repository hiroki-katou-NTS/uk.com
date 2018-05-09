package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import java.util.Optional;
import java.util.List;

/**
* 月別実績の修正の機能
*/
public interface MonPerformanceFunRepository
{

    List<MonPerformanceFun> getAllMonPerformanceFun();

    Optional<MonPerformanceFun> getMonPerformanceFunById(String cid);

    void add(MonPerformanceFun domain);

    void update(MonPerformanceFun domain);

    void remove(String cid);

}

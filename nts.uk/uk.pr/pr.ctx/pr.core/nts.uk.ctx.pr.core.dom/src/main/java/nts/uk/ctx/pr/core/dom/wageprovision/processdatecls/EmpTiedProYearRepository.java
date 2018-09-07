package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 処理年月に紐づく雇用
*/
public interface EmpTiedProYearRepository
{

    List<EmpTiedProYear> getAllEmpTiedProYear();

    Optional<EmpTiedProYear> getEmpTiedProYearById(String cid, int processCateNo);

    void add(EmpTiedProYear domain);

    void update(EmpTiedProYear domain);

    void remove(String cid, int processCateNo);

}

package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

/**
 * 処理年月に紐づく雇用
 */
public interface EmpTiedProYearRepository {

    List<EmpTiedProYear> getEmpTiedProYearById(String cid, int processCateNo);

    void add(EmpTiedProYear domain);

    void update(EmpTiedProYear domain);

}

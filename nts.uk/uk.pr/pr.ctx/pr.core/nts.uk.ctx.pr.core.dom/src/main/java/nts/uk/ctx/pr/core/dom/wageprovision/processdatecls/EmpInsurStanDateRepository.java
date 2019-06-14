package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

/**
* 雇用保険基準日
*/
public interface EmpInsurStanDateRepository
{

    List<EmpInsurStanDate> getAllEmpInsurStanDate();

    Optional<EmpInsurStanDate> getEmpInsurStanDateById(String cid, int processCateNo);

    void add(EmpInsurStanDate domain);

    void update(EmpInsurStanDate domain);

    void remove(String cid, int processCateNo);

}

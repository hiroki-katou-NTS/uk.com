package nts.uk.ctx.sys.assist.dom.salary;

import java.util.Optional;
import java.util.List;

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

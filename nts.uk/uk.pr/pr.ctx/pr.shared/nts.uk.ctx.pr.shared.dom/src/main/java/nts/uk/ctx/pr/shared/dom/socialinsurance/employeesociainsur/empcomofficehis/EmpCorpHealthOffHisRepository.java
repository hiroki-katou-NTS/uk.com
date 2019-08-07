package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import java.util.List;
import java.util.Optional;

/**
* 社員社保事業所所属履歴
*/
public interface EmpCorpHealthOffHisRepository
{

    List<EmpCorpHealthOffHis> getAllEmpCorpHealthOffHis();

    Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId, String hisId);

    void add(EmpCorpHealthOffHis domain);

    void update(EmpCorpHealthOffHis domain);

    void remove(String employeeId, String hisId);

}

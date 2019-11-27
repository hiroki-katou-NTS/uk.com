package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;
import java.util.Optional;

/**
 * 雇用保険喪失時情報
 */
public interface EmpInsLossInfoRepository {
    Optional<EmpInsLossInfo> getEmpInsLossInfoById(String sid);
    
    Optional<EmpInsLossInfo> getOneEmpInsLossInfo(String cId, String sId);
    
    List<EmpInsLossInfo> getListEmpInsLossInfo(String companyId, List<String> employeeIds);

    void add(EmpInsLossInfo domain);

    void update(EmpInsLossInfo domain);

}

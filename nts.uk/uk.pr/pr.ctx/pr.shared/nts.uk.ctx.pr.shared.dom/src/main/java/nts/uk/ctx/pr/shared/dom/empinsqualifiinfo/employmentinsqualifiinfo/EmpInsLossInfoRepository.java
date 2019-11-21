package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;
import java.util.Optional;

import java.util.Optional;

/**
 * 雇用保険喪失時情報
 */
public interface EmpInsLossInfoRepository {
    List<EmpInsHist> getAllEmpInsLossInfo();
    Optional<EmpInsLossInfo> getEmpInsLossInfoById(String sid);
    Optional<EmpInsLossInfo> getOneEmpInsLossInfo(String cId, String sId);

    void add(EmpInsLossInfo domain);

    void update(EmpInsLossInfo domain);

}

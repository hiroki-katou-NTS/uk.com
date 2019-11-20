package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.Optional;

/**
 * 雇用保険喪失時情報
 */
public interface EmpInsLossInfoRepository {
    Optional<EmpInsLossInfo> getEmpInsLossInfoById(String cId, String sId);

    void add(EmpInsLossInfo domain);

    void update(EmpInsLossInfo domain);

}

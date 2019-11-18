package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;
import java.util.Optional;

/**
 * 雇用保険取得時情報
 */
public interface EmpInsGetInfoRepository {

    List<EmpInsGetInfo> getAllEmpInsGetInfo();

    Optional<EmpInsGetInfo> getEmpInsGetInfoById(String sId);

    void add(EmpInsGetInfo domain);

    void update(EmpInsGetInfo domain);

    List<EmpInsGetInfo> getByEmpIds(List<String> empIds);

}

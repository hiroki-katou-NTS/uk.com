package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;
import java.util.Optional;

/**
 * 雇用保険取得時情報
 */
public interface EmpInsGetInfoRepository {

    Optional<EmpInsGetInfo> getEmpInsGetInfoById(String cId, String sId);

    void add(EmpInsGetInfo domain);

    void update(EmpInsGetInfo domain);

    List<EmpInsGetInfo> getByEmpIds(String cid, List<String> empIds);

}

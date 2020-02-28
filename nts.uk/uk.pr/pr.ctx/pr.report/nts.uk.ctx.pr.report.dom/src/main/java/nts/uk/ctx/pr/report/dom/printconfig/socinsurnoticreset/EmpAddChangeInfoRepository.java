package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import java.util.Optional;
import java.util.List;

/**
* 社員住所変更届情報
*/
public interface EmpAddChangeInfoRepository {
    Optional<EmpAddChangeInfo> getEmpAddChangeInfoById(String empId,String cid);

    void add(EmpAddChangeInfo domain);

    void update(EmpAddChangeInfo domain);
    List<EmpAddChangeInfo> getListEmpAddChange(List<String> empIds);
}

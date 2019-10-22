package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import java.util.Optional;

/**
* 社員住所変更届情報
*/
public interface EmpAddChangeInfoRepository {
    Optional<EmpAddChangeInfo> getEmpAddChangeInfoById(String empId,String cid);

    void add(EmpAddChangeInfo domain);

    void update(EmpAddChangeInfo domain);

}

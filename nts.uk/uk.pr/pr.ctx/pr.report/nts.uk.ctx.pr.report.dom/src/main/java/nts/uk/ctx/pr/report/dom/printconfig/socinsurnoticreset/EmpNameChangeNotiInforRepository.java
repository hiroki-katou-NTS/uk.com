package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import java.util.List;
import java.util.Optional;

/**
* 社員氏名変更届情報
*/
public interface EmpNameChangeNotiInforRepository {

    List<EmpNameChangeNotiInfor> getAllEmpNameChangeNotiInfor();

    Optional<EmpNameChangeNotiInfor> getEmpNameChangeNotiInforById(String employeeId, String cid);

    void remove(String employeeId, String cid);

}

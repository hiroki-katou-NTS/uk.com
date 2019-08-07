package nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.EmpNameChangeNotiInfor;

import java.util.Optional;
import java.util.List;

/**
* 社員氏名変更届情報
*/
public interface EmpNameChangeNotiInforRepository
{

    List<EmpNameChangeNotiInfor> getAllEmpNameChangeNotiInfor();

    Optional<EmpNameChangeNotiInfor> getEmpNameChangeNotiInforById(String employeeId, String cid);

    void add(EmpNameChangeNotiInfor domain);

    void update(EmpNameChangeNotiInfor domain);

    void remove(String employeeId, String cid);

}

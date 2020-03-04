package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.List;
import java.util.Optional;

/**
* 社員基礎年金番号情報
*/
public interface EmpBasicPenNumInforRepository
{

    List<EmpBasicPenNumInfor> getAllEmpBasicPenNumInfor();

    List<EmpBasicPenNumInfor> getAllEmpBasicPenNumInfor(List<String> empIds);

    Optional<EmpBasicPenNumInfor> getEmpBasicPenNumInforById(String employeeId);

    Optional<EmpBasicPenNumInfor> getEmpBasicPenNumInforById(String cid, String employeeId);

    void add(EmpBasicPenNumInfor domain);

    void update(EmpBasicPenNumInfor domain);

    void remove(String employeeId);

    /*boolean checkExistEmpBasicPenNumInfo(String empId);*/

}

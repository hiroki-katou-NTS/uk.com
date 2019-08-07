package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;

import java.util.Optional;
import java.util.List;

/**
* 社員厚生年金保険資格情報
*/
public interface EmpWelfarePenInsQualiInforRepository
{

    List<EmpWelfarePenInsQualiInfor> getAllEmpWelfarePenInsQualiInfor();

    Optional<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInforById(String employeeId, String historyId);

    void add(EmpWelfarePenInsQualiInfor domain);

    void update(EmpWelfarePenInsQualiInfor domain);

    void remove(String employeeId, String historyId);

}

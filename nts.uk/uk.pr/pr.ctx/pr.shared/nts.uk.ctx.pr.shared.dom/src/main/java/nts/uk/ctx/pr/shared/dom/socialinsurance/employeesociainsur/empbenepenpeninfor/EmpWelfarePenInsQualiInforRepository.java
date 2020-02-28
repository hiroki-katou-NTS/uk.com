package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 社員厚生年金保険資格情報
*/
public interface EmpWelfarePenInsQualiInforRepository {

    boolean checkEmpWelfarePenInsQualiInforEnd(GeneralDate start, GeneralDate end, List<String> empId);

    boolean checkEmpWelfarePenInsQualiInforStart(GeneralDate start, GeneralDate end, List<String> empId);

    List<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInfor(String cid, GeneralDate date, List<String> sIds);

    Optional<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInforByEmpId(String cid, String employeeId);

}

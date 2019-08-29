package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 社員厚生年金保険資格情報
*/
public interface EmpWelfarePenInsQualiInforRepository {

    EmpWelfarePenInsQualiInfor getEmplHealInsurQualifiInfor(GeneralDate start, List<String> empId);

    boolean checkEmpWelfarePenInsQualiInfor(String userIds);

    Optional<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInforById(String employeeId, String historyId);

    void add(EmpWelfarePenInsQualiInfor domain);

    void update(EmpWelfarePenInsQualiInfor domain);

        Optional<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInforByEmpId(String employeeId);

}

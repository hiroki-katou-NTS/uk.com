package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.time.GeneralDate;

import java.util.Optional;

public interface EmpSocialInsGradeRepository {
    void add(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info);

    void update(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info);

    void delete(String cId, String sId, String histId);

    Optional<EmpSocialInsGrade> getByEmpIdAndBaseDate(String companyId, String employeeId, GeneralDate standardDate);

    Optional<EmpSocialInsGrade> getByKey(String companyId, String employeeId, String historyId);

    Optional<EmpSocialInsGrade> getByEmpId(String companyId, String employeeId);
}

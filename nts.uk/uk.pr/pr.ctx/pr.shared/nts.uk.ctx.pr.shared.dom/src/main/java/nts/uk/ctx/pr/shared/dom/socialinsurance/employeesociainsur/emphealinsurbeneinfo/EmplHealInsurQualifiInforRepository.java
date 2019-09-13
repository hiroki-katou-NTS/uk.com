package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 社員健康保険資格情報
*/
public interface EmplHealInsurQualifiInforRepository {

    boolean checkEmplHealInsurQualifiInfor(GeneralDate start, List<String> empIds);

    Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforByEmpId(String empId);

    Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforById(String employeeId, String hisId);

    Optional<HealInsurNumberInfor> getHealInsurNumberInforByHisId(String hisId);

    void add(EmplHealInsurQualifiInfor domain);

    void update(EmplHealInsurQualifiInfor domain);

    void remove(String employeeId, String hisId);

}

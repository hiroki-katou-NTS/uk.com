package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 社員健康保険資格情報
*/
public interface EmplHealInsurQualifiInforRepository {

    boolean checkEmplHealInsurQualifiInforEndDate(GeneralDate start, GeneralDate end, List<String> empIds);

    boolean checkEmplHealInsurQualifiInforStartDate(GeneralDate start, GeneralDate end, List<String> empIds);

    Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate date );

    List<EmplHealInsurQualifiInfor> getListEmplHealInsurQualifiInfor(String cid, GeneralDate date);

    void add(EmplHealInsurQualifiInfor domain);

    void update(EmplHealInsurQualifiInfor domain);

    void remove(String employeeId, String hisId);

}

package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.List;
import java.util.Optional;

/**
* 社員健康保険資格情報
*/
public interface EmplHealInsurQualifiInforRepository
{

    List<EmplHealInsurQualifiInfor> getAllEmplHealInsurQualifiInfor();

    Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforById(String employeeId, String hisId);

    void add(EmplHealInsurQualifiInfor domain);

    void update(EmplHealInsurQualifiInfor domain);

    void remove(String employeeId, String hisId);

}

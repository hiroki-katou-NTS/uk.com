package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 健保組合加入期間情報
*/
public interface HealInsurPortPerIntellRepository
{

    List<HealInsurPortPerIntell> getAllHealInsurPortPerIntell();

    Optional<HealInsurPortPerIntell> getHealInsurPortPerIntellById(String employeeId, String hisId);

    Optional<HealInsurPortPerIntell> getHealInsurPortPerIntellById(String employeeId);

    Optional<HealthCarePortInfor> getHealInsurPortPerIntellById(String cid, String employeeId, GeneralDate baseDate);

    void add(HealInsurPortPerIntell domain);

    void update(HealInsurPortPerIntell domain);

    void remove(String employeeId, String hisId, String cid);

}

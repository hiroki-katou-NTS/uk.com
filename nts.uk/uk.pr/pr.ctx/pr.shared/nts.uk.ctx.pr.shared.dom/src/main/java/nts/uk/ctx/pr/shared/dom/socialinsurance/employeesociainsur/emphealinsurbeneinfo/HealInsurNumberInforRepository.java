package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.List;
import java.util.Optional;

/**
* 健保番号情報
*/
public interface HealInsurNumberInforRepository
{

    List<HealInsurNumberInfor> getAllHealInsurNumberInfor();

    Optional<HealInsurNumberInfor> getHealInsurNumberInforById(String empId, String hisId);

    List<HealInsurNumberInfor> findByHistoryId(List<String> hisId);

    Optional<HealInsurNumberInfor> getHealInsNumberInfoById(String cid, String empId);

    void add(HealInsurNumberInfor numberInfor);

    void addAll(List<HealInsurNumberInfor> domains);

    void update(HealInsurNumberInfor domain);

    void updateAll (List<HealInsurNumberInfor> numberInfors);

    void remove(String historyId);
}

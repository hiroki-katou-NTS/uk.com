package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.List;
import java.util.Optional;

/**
* 健保番号情報
*/
public interface HealInsurNumberInforRepository
{

    List<HealInsurNumberInfor> getAllHealInsurNumberInfor();

    Optional<HealInsurNumberInfor> getHealInsurNumberInforById(String historyId);

    void add(HealInsurNumberInfor domain);

    void update(HealInsurNumberInfor domain);

    void remove(String historyId);

}

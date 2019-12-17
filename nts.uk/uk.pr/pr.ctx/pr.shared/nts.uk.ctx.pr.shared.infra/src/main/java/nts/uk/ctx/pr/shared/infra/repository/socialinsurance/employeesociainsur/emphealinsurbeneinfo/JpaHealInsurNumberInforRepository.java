package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInforRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaHealInsurNumberInforRepository extends JpaRepository implements HealInsurNumberInforRepository {
    @Override
    public List<HealInsurNumberInfor> getAllHealInsurNumberInfor() {
        return null;
    }

    @Override
    public Optional<HealInsurNumberInfor> getHealInsurNumberInforById(String historyId) {
        return Optional.empty();
    }

    @Override
    public void add(HealInsurNumberInfor domain) {

    }

    @Override
    public void update(HealInsurNumberInfor domain) {

    }

    @Override
    public void remove(String historyId) {

    }
}

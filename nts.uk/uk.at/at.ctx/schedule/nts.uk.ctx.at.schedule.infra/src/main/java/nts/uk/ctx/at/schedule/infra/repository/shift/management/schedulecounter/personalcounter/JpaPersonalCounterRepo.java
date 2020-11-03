package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.personalcounter;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounterRepo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPersonalCounterRepo extends JpaRepository implements PersonalCounterRepo {
    @Override
    public void insert(String companyId, PersonalCounter domain) {

    }

    @Override
    public void update(String companyId, PersonalCounter domain) {

    }

    @Override
    public Optional<PersonalCounter> get(String companyId) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        return false;
    }
}

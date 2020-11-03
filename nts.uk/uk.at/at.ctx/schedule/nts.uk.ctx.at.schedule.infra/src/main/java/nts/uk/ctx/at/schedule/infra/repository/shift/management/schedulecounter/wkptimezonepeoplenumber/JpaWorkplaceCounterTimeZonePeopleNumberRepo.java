package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.wkptimezonepeoplenumber;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumber;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumberRepo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkplaceCounterTimeZonePeopleNumberRepo extends JpaRepository implements WorkplaceCounterTimeZonePeopleNumberRepo {
    @Override
    public void insert(String companyId, WorkplaceCounterTimeZonePeopleNumber domain) {

    }

    @Override
    public void update(String companyId, WorkplaceCounterTimeZonePeopleNumber domain) {

    }

    @Override
    public Optional<WorkplaceCounterTimeZonePeopleNumber> get(String companyId) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        return false;
    }
}

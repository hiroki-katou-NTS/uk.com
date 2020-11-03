package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.wkpcounterlaborcostandtime;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkplaceCounterLaborCostAndTimeRepo extends JpaRepository implements WorkplaceCounterLaborCostAndTimeRepo {
    @Override
    public void insert(String companyId, WorkplaceCounterLaborCostAndTime domain) {

    }

    @Override
    public void update(String companyId, WorkplaceCounterLaborCostAndTime domain) {

    }

    @Override
    public Optional<WorkplaceCounterLaborCostAndTime> get(String companyId) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        return false;
    }
}

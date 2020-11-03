package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.timenumber;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimesNumberCounterSelectionRepo extends JpaRepository implements TimesNumberCounterSelectionRepo {
    @Override
    public void insert(String companyId, TimesNumberCounterSelection domain) {

    }

    @Override
    public void update(String companyId, TimesNumberCounterSelection domain) {

    }

    @Override
    public Optional<TimesNumberCounterSelection> get(String companyId, TimesNumberCounterType type) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String companyId, TimesNumberCounterType type) {
        return false;
    }
}

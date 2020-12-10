package nts.uk.ctx.at.function.infra.repository.alarmworkplace.extractprocessstatus;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplaceRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAlarmListExtractProcessStatusWorkplaceRepository extends JpaRepository implements AlarmListExtractProcessStatusWorkplaceRepository {
    @Override
    public Optional<AlarmListExtractProcessStatusWorkplace> getBy(String companyId, String id) {
        return Optional.empty();
    }

    @Override
    public void add(AlarmListExtractProcessStatusWorkplace processStatus) {

    }

    @Override
    public void update(AlarmListExtractProcessStatusWorkplace processStatus) {

    }
}

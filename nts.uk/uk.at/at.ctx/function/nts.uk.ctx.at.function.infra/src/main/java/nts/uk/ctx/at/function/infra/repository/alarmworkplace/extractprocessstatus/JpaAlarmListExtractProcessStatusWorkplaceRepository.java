package nts.uk.ctx.at.function.infra.repository.alarmworkplace.extractprocessstatus;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplaceRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaAlarmListExtractProcessStatusWorkplaceRepository extends JpaRepository implements AlarmListExtractProcessStatusWorkplaceRepository {
    @Override
    public void add(AlarmListExtractProcessStatusWorkplace alarmListExtraProcessStatus) {

    }
}

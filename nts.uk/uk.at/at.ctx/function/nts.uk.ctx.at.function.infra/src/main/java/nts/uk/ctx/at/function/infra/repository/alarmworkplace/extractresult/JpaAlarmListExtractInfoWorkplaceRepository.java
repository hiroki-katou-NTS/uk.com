package nts.uk.ctx.at.function.infra.repository.alarmworkplace.extractresult;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplaceRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaAlarmListExtractInfoWorkplaceRepository extends JpaRepository implements AlarmListExtractInfoWorkplaceRepository {
}

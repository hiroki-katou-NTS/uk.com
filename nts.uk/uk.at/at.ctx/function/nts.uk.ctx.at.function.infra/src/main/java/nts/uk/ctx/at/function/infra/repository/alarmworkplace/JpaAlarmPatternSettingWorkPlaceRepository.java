package nts.uk.ctx.at.function.infra.repository.alarmworkplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaAlarmPatternSettingWorkPlaceRepository extends JpaRepository implements AlarmPatternSettingWorkPlaceRepository {
    @Override
    public List<AlarmPatternSettingWorkPlace> findByCompanyId(String cid) {
        return new ArrayList<>();
    }

    @Override
    public List<CheckCondition> getCheckCondition(String cid, AlarmPatternCode alarmPatternCode) {
        return new ArrayList<>();
    }
}

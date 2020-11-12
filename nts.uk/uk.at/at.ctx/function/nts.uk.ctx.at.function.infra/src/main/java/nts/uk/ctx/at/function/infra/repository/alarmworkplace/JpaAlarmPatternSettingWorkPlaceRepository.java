package nts.uk.ctx.at.function.infra.repository.alarmworkplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaAlarmPatternSettingWorkPlaceRepository extends JpaRepository implements AlarmPatternSettingWorkPlaceRepository {
    @Override
    public List<AlarmPatternSettingWorkPlace> findByCompanyId(String cid) {
        return new ArrayList<>();
    }
}

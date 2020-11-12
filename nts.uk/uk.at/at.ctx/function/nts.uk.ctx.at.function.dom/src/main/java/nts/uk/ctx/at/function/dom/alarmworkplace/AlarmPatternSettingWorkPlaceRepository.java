package nts.uk.ctx.at.function.dom.alarmworkplace;

import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;

import java.util.List;

public interface AlarmPatternSettingWorkPlaceRepository {

    List<AlarmPatternSettingWorkPlace> findByCompanyId(String cid);
    List<CheckCondition> getCheckCondition(String cid, AlarmPatternCode alarmPatternCode);
}

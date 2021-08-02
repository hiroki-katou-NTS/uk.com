package nts.uk.ctx.at.function.dom.alarm.mailsettings;

import java.util.List;
import java.util.Optional;

public interface AlarmListExecutionMailSettingRepository {
    List<AlarmListExecutionMailSetting> findAll(String cid, int personalManagerClassify);

    Optional<AlarmListExecutionMailSetting> find(String cid);

    void insert(AlarmListExecutionMailSetting alarmExecMailSetting);

    void update(AlarmListExecutionMailSetting alarmExecMailSetting);
}

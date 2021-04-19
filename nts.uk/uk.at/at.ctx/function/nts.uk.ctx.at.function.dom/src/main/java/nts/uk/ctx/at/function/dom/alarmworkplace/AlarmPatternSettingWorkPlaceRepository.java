package nts.uk.ctx.at.function.dom.alarmworkplace;

import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;

import java.util.List;
import java.util.Optional;

public interface AlarmPatternSettingWorkPlaceRepository {

    void create(AlarmPatternSettingWorkPlace domain);

    void update(AlarmPatternSettingWorkPlace domain);

    void delete(String companyId, String alarmPatternCode);


    List<AlarmPatternSettingWorkPlace> findByCompanyId(String cid);

    Optional<AlarmPatternSettingWorkPlace> getBy(String cid, AlarmPatternCode alarmPatternCode);

}

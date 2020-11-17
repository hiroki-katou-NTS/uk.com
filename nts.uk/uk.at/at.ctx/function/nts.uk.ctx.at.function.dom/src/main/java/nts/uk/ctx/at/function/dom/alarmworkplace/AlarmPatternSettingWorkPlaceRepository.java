package nts.uk.ctx.at.function.dom.alarmworkplace;

import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;

import java.util.List;
import java.util.Optional;

public interface AlarmPatternSettingWorkPlaceRepository {

    public void create(AlarmPatternSettingWorkPlace domain);

    public void update(AlarmPatternSettingWorkPlace domain);

    public void delete(String companyId, String alarmPatternCode);


    List<AlarmPatternSettingWorkPlace> findByCompanyId(String cid);

    Optional<AlarmPatternSettingWorkPlace> getBy(String cid, AlarmPatternCode alarmPatternCode);

}

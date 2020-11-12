package nts.uk.ctx.at.function.dom.alarmworkplace;

import java.util.List;

public interface AlarmPatternSettingWorkPlaceRepository {

    List<AlarmPatternSettingWorkPlace> findByCompanyId(String cid);
}

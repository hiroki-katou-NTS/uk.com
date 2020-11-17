package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;

import java.util.List;
import java.util.Optional;

public interface AlarmCheckCdtWkpCtgRepository {

    Optional<AlarmCheckCdtWorkplaceCategory> getByID(String id, int category, String categoryItemCD);

    List<AlarmCheckCdtWorkplaceCategory> getByCategory(WorkplaceCategory category);

    List<AlarmCheckCdtWorkplaceCategory> getBy(WorkplaceCategory category, List<AlarmCheckConditionCode> codes);

    void register(AlarmCheckCdtWorkplaceCategory domain);

    void update(AlarmCheckCdtWorkplaceCategory domain);

    void delete(String id);

}

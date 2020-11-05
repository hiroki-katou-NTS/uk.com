package nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition;

import java.util.Optional;

public interface AlarmCheckCdtWkpCtgRepository {

    Optional<AlarmCheckCdtWorkplaceCategory> getByID(String id);

    Optional<AlarmCheckCdtWorkplaceCategory> getByCategoryID(int categoryID);

    void register(AlarmCheckCdtWorkplaceCategory domain);

    void update(AlarmCheckCdtWorkplaceCategory domain);

    void delete(String id);

}

package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition;

import java.util.List;
import java.util.Optional;

public interface AlarmCheckCdtWkpCtgRepository {

    Optional<AlarmCheckCdtWorkplaceCategory> getByID(String id);

    List<AlarmCheckCdtWorkplaceCategory> getByCategoryID(int categoryID);

    void register(AlarmCheckCdtWorkplaceCategory domain);

    void update(AlarmCheckCdtWorkplaceCategory domain);

    void delete(String id);

}

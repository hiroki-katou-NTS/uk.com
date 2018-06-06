package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;
import java.util.List;

/**
* 復旧対象
*/
public interface RestorationTargetRepository
{

    List<RestorationTarget> getAllRestorationTarget();

    Optional<RestorationTarget> getRestorationTargetById(String dataRecoveryProcessId, String recoveryCategory);

    void add(RestorationTarget domain);

    void update(RestorationTarget domain);

    void remove(String dataRecoveryProcessId, String recoveryCategory);

}

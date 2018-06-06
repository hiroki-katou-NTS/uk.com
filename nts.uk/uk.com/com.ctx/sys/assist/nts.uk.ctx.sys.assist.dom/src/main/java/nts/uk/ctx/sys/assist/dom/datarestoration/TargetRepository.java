package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;
import java.util.List;

/**
* 対象者
*/
public interface TargetRepository
{

    List<Target> getAllTarget();

    Optional<Target> getTargetById(String dataRecoveryProcessId, String sid);

    void add(Target domain);

    void update(Target domain);

    void remove(String dataRecoveryProcessId, String sid);

}

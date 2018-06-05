package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;
import java.util.List;

/**
* サーバー準備動作管理
*/
public interface ServerPrepareMngRepository
{

    List<ServerPrepareMng> getAllServerPrepareMng();

    Optional<ServerPrepareMng> getServerPrepareMngById(String dataRecoveryProcessId);

    void add(ServerPrepareMng domain);

    void update(ServerPrepareMng domain);

    void remove(String dataRecoveryProcessId);

}

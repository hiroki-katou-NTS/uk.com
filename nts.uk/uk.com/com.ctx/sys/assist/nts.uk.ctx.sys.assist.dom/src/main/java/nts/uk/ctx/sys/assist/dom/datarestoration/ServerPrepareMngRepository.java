package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;

/**
 * サーバー準備動作管理
 */
public interface ServerPrepareMngRepository {

	Optional<ServerPrepareMng> getServerPrepareMngById(String dataRecoveryProcessId);

	void add(ServerPrepareMng domain);

	void update(ServerPrepareMng domain);

	void remove(String dataRecoveryProcessId);
}

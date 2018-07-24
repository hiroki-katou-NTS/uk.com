package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;

@Stateless
public class ServerPrepareManagementFinder {
	
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;

	public String getServerPrepareManagementById(String dataRecoveryProcessId) {
		Optional<ServerPrepareMng> serverPrepare = serverPrepareMngRepository.getServerPrepareMngById(dataRecoveryProcessId);
		return serverPrepare.isPresent() ? serverPrepare.get().getOperatingCondition().name() : ServerPrepareOperatingCondition.UPLOAD_FAILED.name();
	}
}

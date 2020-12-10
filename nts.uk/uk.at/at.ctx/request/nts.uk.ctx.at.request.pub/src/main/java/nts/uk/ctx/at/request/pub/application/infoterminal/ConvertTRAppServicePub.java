package nts.uk.ctx.at.request.pub.application.infoterminal;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;

public interface ConvertTRAppServicePub {

	public <T extends ApplicationReceptionDataExport> Optional<AtomTask> converData(String empInfoTerCode,
			String contractCode, T recept);

}

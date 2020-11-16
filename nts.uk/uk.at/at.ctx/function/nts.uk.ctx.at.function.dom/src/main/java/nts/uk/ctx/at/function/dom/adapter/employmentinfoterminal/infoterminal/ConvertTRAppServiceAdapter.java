package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.ApplicationReceptionDataImport;

public interface ConvertTRAppServiceAdapter {

	public  Optional<AtomTask> converData(Integer empInfoTerCode,
			String contractCode, ApplicationReceptionDataImport recept);
}

package nts.uk.file.at.app.export.regisagreetime;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface RegistTimeRepository {
	
	List<MasterData> getDataExport();
}

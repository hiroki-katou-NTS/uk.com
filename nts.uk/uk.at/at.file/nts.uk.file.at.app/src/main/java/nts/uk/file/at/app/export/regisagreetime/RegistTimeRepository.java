package nts.uk.file.at.app.export.regisagreetime;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface RegistTimeRepository {
	
	List<MasterData> getDataExportSheet1();
	List<MasterData> getDataExportSheet2();
	List<MasterData> getDataExportSheet3();
}

package nts.uk.file.at.app.export.specialholiday;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface SpecialHolidayExRepository {
	
	List<MasterData> getSPHDExportData(String cid);
	
	List<MasterData> getSPHDEventExportData(String cid);
}

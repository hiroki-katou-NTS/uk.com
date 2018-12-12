package nts.uk.file.at.app.export.shift.estimate;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface ShiftEstimateRepository {
	List<MasterData> getDataExport(String date);
}

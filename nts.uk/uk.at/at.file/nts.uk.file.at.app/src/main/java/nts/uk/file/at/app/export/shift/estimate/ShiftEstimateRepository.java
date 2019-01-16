package nts.uk.file.at.app.export.shift.estimate;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface ShiftEstimateRepository {
	List<MasterData> getDataExport();
	List<MasterData> getDataSheetTwoExport();
	List<MasterData> getDataSheetThreeExport(int startDate, int endDate);
	List<MasterData> getDataSheetFourExport(int startDate, int endDate);
	List<MasterData> getDataSheetFiveExport(int startDate, int endDate);
}

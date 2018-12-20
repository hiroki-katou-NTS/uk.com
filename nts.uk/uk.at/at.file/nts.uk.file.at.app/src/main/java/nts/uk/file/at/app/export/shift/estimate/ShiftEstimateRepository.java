package nts.uk.file.at.app.export.shift.estimate;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface ShiftEstimateRepository {
	List<MasterData> getDataExport();
	List<MasterData> getDataSheetTwoExport();
	List<MasterData> getDataSheetThreeExport(String startDate, String endDate);
	List<MasterData> getDataSheetFourExport(String startDate, String endDate);
	List<MasterData> getDataSheetFiveExport(String startDate, String endDate);
}

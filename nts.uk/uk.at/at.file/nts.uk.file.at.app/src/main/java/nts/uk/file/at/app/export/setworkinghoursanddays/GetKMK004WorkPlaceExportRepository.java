package nts.uk.file.at.app.export.setworkinghoursanddays;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

/**
 * 
 * @author sonnlb
 *
 */
public interface GetKMK004WorkPlaceExportRepository {

	List<MasterData> getWorkPlaceExportData(int startDate, int endDate);
}

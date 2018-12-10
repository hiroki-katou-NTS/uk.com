package nts.uk.file.com.app.Indivigrant;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface IndivigrantRepository {
	List<MasterData> getDataExport();
}

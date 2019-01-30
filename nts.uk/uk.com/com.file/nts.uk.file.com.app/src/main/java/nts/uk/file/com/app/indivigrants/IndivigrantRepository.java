package nts.uk.file.com.app.indivigrants;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface IndivigrantRepository {
	List<MasterData> getDataExport(String baseDate);
}

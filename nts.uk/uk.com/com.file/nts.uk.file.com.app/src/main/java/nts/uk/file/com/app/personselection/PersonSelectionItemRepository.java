package nts.uk.file.com.app.personselection;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface PersonSelectionItemRepository {

	List<MasterData> getDataExport(String contractCd, String date);

}

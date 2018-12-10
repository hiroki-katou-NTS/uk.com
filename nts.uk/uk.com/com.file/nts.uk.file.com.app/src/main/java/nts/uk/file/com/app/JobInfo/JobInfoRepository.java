package nts.uk.file.com.app.JobInfo;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface JobInfoRepository {
	List<MasterData> getDataExport(String date);
}

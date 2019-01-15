package nts.uk.file.com.app.jobInfo;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface JobInfoRepository {
	List<MasterData> getDataRoleSetPosExport(String date);
	
	List<MasterData> getDataRoleSetEmpExport(String date);
}

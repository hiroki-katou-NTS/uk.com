package nts.uk.file.com.app.jobInfos;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface JobInfoRepository {
	List<MasterData> getDataRoleSetPosExport();
	
	List<MasterData> getDataRoleSetEmpExport(String date);
}

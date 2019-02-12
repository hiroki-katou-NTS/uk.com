package nts.uk.file.com.app.personrole;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface PersonRoleRepository {
	List<MasterData> getDataExport(int salaryUseAtr, int personnelUseAtr, int employmentUseAtr);
	
}

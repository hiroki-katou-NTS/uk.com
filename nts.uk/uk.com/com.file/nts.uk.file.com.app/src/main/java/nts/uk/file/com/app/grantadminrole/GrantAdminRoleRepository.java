package nts.uk.file.com.app.grantadminrole;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface GrantAdminRoleRepository {
    List<MasterData> getDataExport(String companyId, int roleType);
    List<MasterData> getDataExportCompanyManagerMode(int roleType);

}

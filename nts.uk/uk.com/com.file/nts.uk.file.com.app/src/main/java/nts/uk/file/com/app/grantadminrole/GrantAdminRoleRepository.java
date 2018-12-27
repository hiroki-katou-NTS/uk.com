package nts.uk.file.com.app.grantadminrole;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;
import java.sql.Date;

public interface GrantAdminRoleRepository {
    List<MasterData> getDataExport(String companyId, int roleType, Date date);
    List<MasterData> getDataExportCompanyManagerMode(int roleType, Date date);

}

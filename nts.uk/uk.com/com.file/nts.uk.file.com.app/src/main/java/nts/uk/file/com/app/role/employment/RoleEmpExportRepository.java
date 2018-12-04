package nts.uk.file.com.app.role.employment;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface RoleEmpExportRepository {
    List<MasterData> findAllRoleEmployment(int roleType, String cId);
}

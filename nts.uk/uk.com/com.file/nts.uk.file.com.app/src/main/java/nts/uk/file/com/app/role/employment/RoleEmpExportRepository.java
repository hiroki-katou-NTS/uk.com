package nts.uk.file.com.app.role.employment;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;
import java.util.Map;

public interface RoleEmpExportRepository {
     List<MasterData> findAllRoleEmployment(int roleType, String cId);
     Map<Integer, String> findAllFunctionNo();
}

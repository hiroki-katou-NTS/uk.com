package nts.uk.file.com.app.role.personalinfo;

import nts.uk.ctx.sys.auth.dom.export.wkpmanager.WorkPlaceSelectionExportData;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceFunction;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface RolePersonalInforRepository {
    List<MasterData>  findAllRolePersonalInfor(int roleType, String cId);
}

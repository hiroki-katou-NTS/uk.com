package nts.uk.file.com.app.rolesetmenu;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface RoleSetMenuRepository {
	
	List<MasterData> exportDataExcel();
	
}

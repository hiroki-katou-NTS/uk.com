package nts.uk.file.com.app.person.setting;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface PerInfoInitValueSetCtgRepository {
	List<MasterData> getDataExport(int payroll,int personnel,int atttendance);
}

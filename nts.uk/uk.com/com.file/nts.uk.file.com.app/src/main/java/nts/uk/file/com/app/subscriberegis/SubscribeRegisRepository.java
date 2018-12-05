package nts.uk.file.com.app.subscriberegis;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface SubscribeRegisRepository {
    List<MasterData> getDataExport(String companyId, List<String>  employeeIds);
}

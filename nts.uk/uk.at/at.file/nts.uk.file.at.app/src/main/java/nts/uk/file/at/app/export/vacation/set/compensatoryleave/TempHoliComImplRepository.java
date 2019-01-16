package nts.uk.file.at.app.export.vacation.set.compensatoryleave;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface TempHoliComImplRepository {
    List<MasterData> getAllTemHoliCompany(String cid);
}

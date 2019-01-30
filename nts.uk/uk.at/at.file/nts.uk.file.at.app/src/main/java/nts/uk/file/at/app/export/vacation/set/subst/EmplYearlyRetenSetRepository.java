package nts.uk.file.at.app.export.vacation.set.subst;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface EmplYearlyRetenSetRepository {
    List<MasterData> getAllEmplYearlyRetenSet(String cid);
}

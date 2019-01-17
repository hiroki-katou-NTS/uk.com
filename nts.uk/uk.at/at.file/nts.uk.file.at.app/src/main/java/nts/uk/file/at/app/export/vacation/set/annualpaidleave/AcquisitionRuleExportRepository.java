package nts.uk.file.at.app.export.vacation.set.annualpaidleave;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface AcquisitionRuleExportRepository {
    List<MasterData> getAllAcquisitionRule(String cid);
}

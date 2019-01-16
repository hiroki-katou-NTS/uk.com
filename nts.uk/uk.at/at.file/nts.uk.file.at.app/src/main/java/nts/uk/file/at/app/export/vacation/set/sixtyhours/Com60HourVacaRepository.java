package nts.uk.file.at.app.export.vacation.set.sixtyhours;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface Com60HourVacaRepository {
    List<MasterData> getAllCom60HourVacation(String cid);
}

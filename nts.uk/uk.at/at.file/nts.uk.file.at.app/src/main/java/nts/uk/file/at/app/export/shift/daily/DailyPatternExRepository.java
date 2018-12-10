package nts.uk.file.at.app.export.shift.daily;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface DailyPatternExRepository {
    List<MasterData> findAllDailyPattern();
}

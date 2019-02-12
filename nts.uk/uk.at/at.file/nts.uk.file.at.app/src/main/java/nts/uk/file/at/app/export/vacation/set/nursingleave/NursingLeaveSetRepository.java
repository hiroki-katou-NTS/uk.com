package nts.uk.file.at.app.export.vacation.set.nursingleave;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface NursingLeaveSetRepository {
    List<MasterData> getAllNursingLeaveSetting(String cid);
}

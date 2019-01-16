package nts.uk.file.at.app.export.vacation.set.annualpaidleave;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface AnnPaidLeaveRepository {
	
	List<MasterData> getAnPaidLea(String cid);

}

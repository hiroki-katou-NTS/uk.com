package nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface GetMasterAttendanceItemAdapter {

	//RequestList707
	public List<AllMasterAttItemImport> getAllMaster(String companyId, List<Integer> lstAtt, GeneralDate baseDate);
}

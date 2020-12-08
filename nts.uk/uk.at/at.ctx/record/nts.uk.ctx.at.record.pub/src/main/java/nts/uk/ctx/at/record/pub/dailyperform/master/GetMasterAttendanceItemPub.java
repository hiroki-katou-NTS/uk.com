package nts.uk.ctx.at.record.pub.dailyperform.master;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface GetMasterAttendanceItemPub {

	//RequestList707
	public List<AllMasterAttItemExport> getAllMaster(String companyId, List<Integer> lstAtt, GeneralDate baseDate);
}

package nts.uk.ctx.at.schedule.dom.schedule.schedulemaster;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author TanLV
 *
 */
public interface ScheMasterInfoRepository {
	Optional<ScheMasterInfo> getScheMasterInfo(String sId, GeneralDate generalDate);
	
	void addScheMasterInfo(ScheMasterInfo scheMasterInfo);
	
	void updateScheMasterInfo(ScheMasterInfo scheMasterInfo);
	
	void deleteScheMasterInfo(String sId, GeneralDate generalDate);
}

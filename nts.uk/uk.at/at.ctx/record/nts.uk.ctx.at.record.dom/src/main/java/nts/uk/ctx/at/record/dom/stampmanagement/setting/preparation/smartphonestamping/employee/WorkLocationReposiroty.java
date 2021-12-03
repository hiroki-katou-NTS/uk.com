package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;

public interface WorkLocationReposiroty {
	List<WorkLocation> findAll(String contractCode);
	
	List<WorkLocation> findByWorkPlace(String contractCode, String cid, String workPlaceId);
	
	void insertStampingArea(String emplId, StampingAreaRestriction areaRestriction);
	
	Optional<StampingAreaRestriction> findByEmployeeId (String employId);
	
	void updateStampingArea(String emplId,StampingAreaRestriction areaRestriction);
	
	List<String> getStatusStampingEmpl(List<String>listEmplId);
	
	void deleteStampSetting(String employeeId);
}

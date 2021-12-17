package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.List;
import java.util.Optional;

public interface StampingAreaReposiroty {
	void insertStampingArea(String emplId, StampingAreaRestriction areaRestriction);
	
	Optional<StampingAreaRestriction> findByEmployeeId (String employId);
	
	Boolean updateStampingArea(String emplId,StampingAreaRestriction areaRestriction);
	
	List<String> getStatusStampingEmpl(List<String>listEmplId);
	
	void deleteStampSetting(String employeeId);
}

package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.List;
import java.util.Optional;

public interface StampingAreaRepository {
	void insertStampingArea(EmployeeStampingAreaRestrictionSetting restrictionSetting);

	Optional<EmployeeStampingAreaRestrictionSetting> findByEmployeeId(String employId);

	Boolean updateStampingArea(EmployeeStampingAreaRestrictionSetting restrictionSetting);

	List<String> getStatusStampingEmpl(List<String> listEmplId);

	void deleteStampSetting(String employeeId);
	
	void saveStampingArea(EmployeeStampingAreaRestrictionSetting restrictionSetting);
}

package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SpecialLeaveGrantRepository {

	List<SpecialLeaveGrantRemainingData> getAll(String employeeId, int specialCode);

	List<SpecialLeaveGrantRemainingData> getAllByExpStatus(String employeeId, int specialCode, boolean expirationStatus);
	
	void add(SpecialLeaveGrantRemainingData data);

	void update(SpecialLeaveGrantRemainingData data);

	void delete(String specialid);

	Optional<SpecialLeaveGrantRemainingData> getBySpecialId(String specialId);

}

package nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation;

import java.util.Optional;

public interface TimeSpecialLeaveMngSetRepository {
	
    Optional<TimeSpecialLeaveManagementSetting> findByCompany(String companyId);

	void update(TimeSpecialLeaveManagementSetting setting);

	void add(TimeSpecialLeaveManagementSetting setting);
}

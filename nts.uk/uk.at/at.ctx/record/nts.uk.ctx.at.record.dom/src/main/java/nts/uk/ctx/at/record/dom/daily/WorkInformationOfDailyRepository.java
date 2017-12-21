package nts.uk.ctx.at.record.dom.daily;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface WorkInformationOfDailyRepository {

	Optional<WorkInformationOfDaily> find(String companyId,String employeeId, GeneralDate date);
}

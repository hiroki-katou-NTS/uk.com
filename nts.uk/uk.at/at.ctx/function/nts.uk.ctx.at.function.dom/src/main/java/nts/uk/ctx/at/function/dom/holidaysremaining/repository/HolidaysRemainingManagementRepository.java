package nts.uk.ctx.at.function.dom.holidaysremaining.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;

public interface HolidaysRemainingManagementRepository {

	List<HolidaysRemainingManagement> getProcessExecutionLogByCompanyId(String companyId);

	Optional<HolidaysRemainingManagement> getProcessExecutionByCidAndExecCd(String companyID, String code);

	void insert(HolidaysRemainingManagement domain);

	void update(HolidaysRemainingManagement domain);

	void remove(String companyId, String code);
}

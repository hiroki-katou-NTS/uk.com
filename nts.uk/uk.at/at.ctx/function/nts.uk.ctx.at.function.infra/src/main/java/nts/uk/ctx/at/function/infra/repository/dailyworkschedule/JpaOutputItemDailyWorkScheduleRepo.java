package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingRepository;

public class JpaOutputItemDailyWorkScheduleRepo implements OutputStandardSettingRepository, FreeSettingOfOutputItemRepository {
	
	public static final String GET_FREE_SETTING_BY_EMPLOYEE_AND_COMPANY = "SELECT outItem FROM KfnmtRptWkDaiOutItem outItem WHERE ";
	public static final String GET_STANDARD_SETTING_BY_COMPANY = "";

	@Override
	public Optional<FreeSettingOfOutputItemForDailyWorkSchedule> getFreeSettingByCompanyAndEmployee(String companyId,
			String employeeId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<OutputStandardSettingOfDailyWorkSchedule> getStandardSettingByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}

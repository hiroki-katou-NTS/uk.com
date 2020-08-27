package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.Optional;

public interface FreeSettingOfOutputItemRepository {

	/**
	 * ドメインモデル「日別勤務表の出力項目自由設定」を取得
	 * 
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return ドメインモデル「日別勤務表の出力項目自由設定」
	 */
	public Optional<FreeSettingOfOutputItemForDailyWorkSchedule> getFreeSettingByCompanyAndEmployee(String companyId, String employeeId);
}

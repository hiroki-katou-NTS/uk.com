package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.Optional;

public interface OutputStandardSettingRepository {

	/**
	 * ドメインモデル「日別勤務表の出力項目定型設定」を取得
	 * @param companyId: 会社ID
	 * @return ドメインモデル Domain Model「日別勤務表の出力項目定型設定」
	 */
	public Optional<OutputStandardSettingOfDailyWorkSchedule> getStandardSettingByCompanyId(String companyId);
}

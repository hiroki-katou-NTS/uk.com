package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.shorttimework;

import java.util.Optional;

/**
 * リポジトリ：短時間勤務の計算
 * @author shuichi_ishida
 */
public interface CalcOfShortTimeWorkRepository {

	/**
	 * 取得
	 * @param companyId 会社ID
	 * @return 短時間勤務の計算
	 */
	Optional<CalcOfShortTimeWork> find(String companyId);
	
	/**
	 * 追加または更新
	 * @param calcOfShortTimeWork 短時間勤務の計算
	 */
	void addOrUpdate(CalcOfShortTimeWork calcOfShortTimeWork);
}

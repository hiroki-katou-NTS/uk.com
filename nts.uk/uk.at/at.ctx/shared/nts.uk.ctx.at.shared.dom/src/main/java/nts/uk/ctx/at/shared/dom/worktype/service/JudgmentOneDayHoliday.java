package nts.uk.ctx.at.shared.dom.worktype.service;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

public interface JudgmentOneDayHoliday {
	/**
	 * 1日休日の判定
	 * @param companyID
	 * @param workTypeCD
	 * @return
	 */
	public boolean judgmentOneDayHoliday(String companyID, String workTypeCD);
	
	/**
	 * refactor 4
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.勤務種類.アルゴリズム.法定区分のチェック.法定区分のチェック
	 * @param companyID 会社ID
	 * @param actualWorkTypeCD 実績の勤務種類コード
	 * @param appWorkTypeCD 申請する勤務種類コード
	 * @return
	 */
	public HolidayAtrOutput checkHolidayAtr(String companyID, String actualWorkTypeCD, String appWorkTypeCD);
	
	/**
	 * refactor 4
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.勤務種類.アルゴリズム.勤務種類の法定区分を取得.勤務種類の法定区分を取得
	 * @param companyID 会社ID
	 * @param workTypeCD 勤務種類
	 * @return
	 */
	public Optional<HolidayAtr> getHolidayAtr(String companyID, String workTypeCD);
	
}

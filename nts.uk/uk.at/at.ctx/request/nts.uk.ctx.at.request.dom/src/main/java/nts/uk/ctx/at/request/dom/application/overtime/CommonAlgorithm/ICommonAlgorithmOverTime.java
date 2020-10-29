package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
@Stateless
public interface ICommonAlgorithmOverTime {
	/**
	 * Refactor5
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業申請設定.アルゴリズム.指定社員の申請残業枠を取得する
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @param overTimeAtr
	 * @return 利用する残業枠
	 */
	public QuotaOuput getOvertimeQuotaSetUse(String companyId, String employeeId, GeneralDate date, OverTimeAtr overTimeAtr);
	/**
	 * Refactor5
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.AB画面の共通アルゴリズム.07_勤務種類取得
	 * @param appEmploymentSetting
	 * @return 勤務種類(List)
	 */
	public List<WorkType> getWorkType(Optional<AppEmploymentSetting> appEmploymentSetting);
	/**
	 * Refactor5
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.AB画面の共通アルゴリズム.基準日に関する情報を取得する
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @param overTimeAtr
	 * @param workTime
	 * @param appEmploymentSetting
	 * @return 基準日に関する情報
	 */
	public InfoBaseDateOutput getInfoBaseDate(String companyId, String employeeId, GeneralDate date, OverTimeAtr overTimeAtr, List<WorkTimeSetting> workTime, Optional<AppEmploymentSetting> appEmploymentSetting);
}

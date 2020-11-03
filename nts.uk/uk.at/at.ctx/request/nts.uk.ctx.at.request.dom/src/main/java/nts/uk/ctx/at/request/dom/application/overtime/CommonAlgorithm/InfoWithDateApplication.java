package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkHours;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;

/**
 * Refactor5
 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.AB画面の共通アルゴリズム.申請日に関する情報を取得する
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 申請日に関係する情報 pending
public class InfoWithDateApplication {
	// 休憩時間帯設定
	private Optional<BreakTimeZoneSetting> breakTime = Optional.empty();
	// 勤務時間
	private Optional<WorkHours> workHours = Optional.empty();
	// 残業指示
	
	// 申請時間
	private Optional<ApplicationTime> applicationTime = Optional.empty();
	// 初期の勤務種類コード
	private Optional<String> workTypeCD = Optional.empty();
	// 初期の就業時間帯コード
	private Optional<String> workTimeCD = Optional.empty();
}

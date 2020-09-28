package nts.uk.ctx.at.request.dom.setting.company.request.stamp;
/**
 * Refactor4
 * hoangnd
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.打刻申請
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@AllArgsConstructor
@Getter
//打刻申請の反映
public class AppStampReflect {
	// 介護時間帯を反映する
	private NotUseAtr nurseTime;

	// 休憩時間帯を反映する
	private NotUseAtr breakTime;

	// 会社ID
	String companyId;

	// 出退勤を反映する
	private NotUseAtr attendence;

	// 外出時間帯を反映する
	private NotUseAtr outingHourse;

	// 応援開始、終了を反映する
	private NotUseAtr startAndEndSupport;

	// 育児時間帯を反映する
	private NotUseAtr parentHours;

	// 臨時出退勤を反映する
	private NotUseAtr temporaryAttendence;

}

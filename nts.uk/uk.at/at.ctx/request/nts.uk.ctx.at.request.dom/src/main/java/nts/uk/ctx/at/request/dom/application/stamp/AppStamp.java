package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementEarly;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

/**
 * Refactor4
 * @author hoangnd
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.打刻申請
 * Update by LienPTK - 2021/11/22 - EA4158
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//打刻申請
public class AppStamp extends Application {
//	時刻
	private List<TimeStampApp> listTimeStampApp;
	
//	時刻の取消
	private List<DestinationTimeApp> listDestinationTimeApp;
//	時間帯
	private List<TimeStampAppOther> listTimeStampAppOther;
//	時間帯の取消
	private List<DestinationTimeZoneApp> listDestinationTimeZoneApp;
//	申請内容
	public String getAppContent() {
		return null;
	}

	public AppStamp(Application application) {
		super(application);
	}
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.打刻申請.アルゴリズム.応援が1回め勤務か2回目勤務のどちらの時間帯なのか判断してセットする
	 * 応援が1回め勤務か2回目勤務のどちらの時間帯なのか判断してセットする
	 *
	 * @param isMultipleWork 複数回勤務の管理
	 * @param achievementEarly 遅刻早退実績
	 */
	public void determineTheSupportTimeAndSet(boolean isMultipleWork, AchievementEarly achievementEarly) {
		
		// ＠時刻をLoopする
		for (TimeStampApp timeStampApp : this.listTimeStampApp) {
			// 「＠打刻分類」をチェックする
			if (timeStampApp.destinationTimeApp.getTimeStampAppEnum().equals(TimeStampAppEnum.CHEERING)) {
				// Inported.「複数回勤務の管理」をチェックする
				if (isMultipleWork) {
					// ①IF：（予定出勤時刻1　and　予定退勤時刻1　and予定出勤時刻2　and　予定退勤時刻2　がEmpty）
					if (!achievementEarly.getScheAttendanceTime1().isPresent()
					 && !achievementEarly.getScheDepartureTime1().isPresent()
					 && !achievementEarly.getScheAttendanceTime2().isPresent()
					 && !achievementEarly.getScheDepartureTime2().isPresent()) {
						// ＠応援勤務NO　＝　１
						timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(1)));
					}

					// IF：予定出勤時刻2　and　予定退勤時刻2　がEmpty
					if (!achievementEarly.getScheAttendanceTime2().isPresent()
					 && !achievementEarly.getScheDepartureTime2().isPresent()) {
						// ＠応援勤務NO　＝　１
						timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(2)));
					}

					// 開始終了区分　＝　開始の場合
					if (timeStampApp.destinationTimeApp.getStartEndClassification().equals(StartEndClassification.START)) {
						// 予定退勤時刻1がPresent
						if (achievementEarly.getScheDepartureTime1().isPresent()) {
							// 時刻　＜＝　予定退勤時刻1　
							if (timeStampApp.getTimeOfDay().lessThanOrEqualTo(achievementEarly.getScheDepartureTime1().get())) {
								// 勤務NO　＝　１
								timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(1)));
							// 時刻　＞　予定退勤時刻1　
							} else {
								// 勤務NO＝2
								timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(2)));
							}
						} else {
							// 勤務NO　＝　１
							timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(1)));
						}

						// IF：予定出勤時刻２がPresent
						if (achievementEarly.getScheAttendanceTime2().isPresent()) {
							// 時刻　＜　予定出勤時刻２
							if (timeStampApp.getTimeOfDay().lessThan(achievementEarly.getScheAttendanceTime2().get())) {
								// 勤務NO＝１
								timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(1)));
							// 時刻　＞＝　予定出勤時刻
							} else {
								// 勤務NO＝2
								timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(2)));
							}
						} else {
							// 勤務NO　＝　１
							timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(1)));
						}
					}
				} else {
					// ＠応援勤務NO　＝　１
					timeStampApp.destinationTimeApp.setSupportWorkNo(Optional.of(new WorkNo(1)));
				}
			}
		}
	}

}

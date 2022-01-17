package nts.uk.screen.at.app.kdw013.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.日ごとの作業合計時間を求める
 * 
 * @author tutt
 *
 */
@Stateless
public class CalculateTotalWorktimePerDay {

	@Inject
	private CalculateWorktime calWorktime;

	public List<TotalWorktimeDto> calculateTotalWorktimePerDay(CalculateTotalWorktimePerDayCommand command) {
		List<TotalWorktimeDto> totalWorktimes = new ArrayList<>();

		// Loop List<日別勤怠(Work)>
		for (IntegrationOfDaily daily : command.getIntegrationOfDailyLst()) {
			
			Optional<TimeLeavingWork> timeLeavingWorkNo1 = daily.getAttendanceLeave().map(d -> d.getTimeLeavingWorks())
					.orElse(Collections.emptyList()).stream()
					.filter(timeLeavingWork -> timeLeavingWork.getWorkNo().v() == 1).findFirst();
			
			if (timeLeavingWorkNo1.isPresent()) {
				// 1. 出退勤．出勤．打刻．isPresent AND 出退勤．退勤．打刻．isPresent
				if (timeLeavingWorkNo1.get().getAttendanceStamp().isPresent()
						&& timeLeavingWorkNo1.get().getLeaveStamp().isPresent()) {

					// 「作業合計時間」を作成する
					TotalWorktimeDto total = new TotalWorktimeDto();

					// 作業合計時間．年月日 = 処理中の「日別勤怠(Work)．年月日」
					total.setDate(daily.getYmd());

					// 作業合計時間．作業時間 = 処理中の「日別勤怠(Work)．応援時間．勤務時間．総労働時間」の合計
					total.setTaskTime(daily.getOuenTime().stream().map(m -> m.getWorkTime().getTotalTime().v())
							.collect(Collectors.summingInt(Integer::intValue)));

					totalWorktimes.add(total);

				}

			} else {
				// 2. 出退勤．出勤．打刻．isEmpty OR 出退勤．退勤．打刻．isEmpty
				// call 作業時間を計算する

				// 基準時間帯．開始時刻 = 処理中の「日別勤怠(Work)．応援時間帯．時間帯．開始．時刻」の一番小さい時刻を利用する
				Integer start = daily.getOuenTimeSheet().isEmpty() ? 0
						: daily.getOuenTimeSheet().stream()
								.mapToInt(m -> m.getTimeSheet().getStart()
										.map(s -> s.getTimeWithDay().map(td -> td.v()).orElse(0)).orElse(0))
								.min().getAsInt();

				// 基準時間帯．終了時刻 = 処理中の「日別勤怠(Work)．応援時間帯．時間帯．終了．時刻」の一番小さい時刻を利用する
				//lấy lớn nhất, không phải nhỏ nhất, tài liệu mô tả sai
				Integer end = daily.getOuenTimeSheet().isEmpty() ? 0
						: daily.getOuenTimeSheet().stream()
								.mapToInt(m -> m.getTimeSheet().getEnd()
										.map(s -> s.getTimeWithDay().map(td -> td.v()).orElse(0)).orElse(0))
								.max().getAsInt();

				// 基準時間帯
				TimeSpanForCalc refTimezone = new TimeSpanForCalc(new TimeWithDayAttr(start), new TimeWithDayAttr(end));

				// $休憩リスト = 処理中の「日別勤怠(Work)」．休憩時間帯．時間帯：map
				// 計算用時間帯#計算用時間帯($．開始、$．終了)
				List<TimeSpanForCalc> goOutBreakTimeLst = daily.getBreakTime().getBreakTimeSheets().stream()
						.map(m -> new TimeSpanForCalc(m.getStartTime(), m.getEndTime())).collect(Collectors.toList());

				/*
				 * if 処理中の「日別勤怠(Work)」．外出時間帯．isPresent $外出リスト =
				 * 処理中の「日別勤怠(Work)」．外出時間帯．時間帯： filter $．外出．isPresent AND
				 * $．戻り．isPresent map 計算用時間帯#計算用時間帯($．外出．時刻．時刻、$．戻り．時刻．時刻)
				 */
				if (daily.getOutingTime().isPresent()) {

					List<TimeSpanForCalc> outingTimeLst = daily.getOutingTime().get().getOutingTimeSheets().stream()
							.filter(f -> f.getGoOut().isPresent() && f.getComeBack().isPresent())
							.map(m -> new TimeSpanForCalc(m.getGoOut().get().getTimeDay().getTimeWithDay().get(),
									m.getComeBack().get().getTimeDay().getTimeWithDay().get()))
							.collect(Collectors.toList());

					goOutBreakTimeLst.addAll(outingTimeLst);
				}

				// 「作業合計時間」を作成する
				TotalWorktimeDto total = new TotalWorktimeDto();

				// 作業合計時間．年月日 = 処理中の「日別勤怠(Work)．年月日」
				total.setDate(daily.getYmd());

				// 作業合計時間．作業時間 = 求めた「作業時間」
				total.setTaskTime(calWorktime.calculateWorktime(refTimezone, goOutBreakTimeLst));

				totalWorktimes.add(total);

			}
			
		
		}

		return totalWorktimes;
	}
}

package nts.uk.ctx.at.record.dom.shorttimework;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;


/**
 * 
 * @author nampt 日別実績の短時間勤務時間帯 - root
 *
 */
@Getter
@NoArgsConstructor
public class ShortTimeOfDailyPerformance extends AggregateRoot {

	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日*/
	private GeneralDate ymd;
	
	/** 時間帯*/
	private ShortTimeOfDailyAttd timeZone;
	
	public ShortTimeOfDailyPerformance(String employeeId, List<ShortWorkingTimeSheet> shortWorkingTimeSheets,
			GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = new ShortTimeOfDailyAttd(shortWorkingTimeSheets);
	}

	public ShortTimeOfDailyPerformance(String employeeId, GeneralDate ymd, ShortTimeOfDailyAttd timeZone) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = timeZone;
	}
	
	// 短時間勤務を変更
	public void change(ShortWorkTimeHistoryItem shortWTHistItem, List<EditStateOfDailyPerformance> editStates) {

		// 時間帯を取得
		List<SChildCareFrame> sChildCFs = shortWTHistItem.getLstTimeSlot();

		if (sChildCFs.isEmpty())
			return;

		List<ShortWorkingTimeSheet> sWTimeSheets = this.timeZone.getShortWorkingTimeSheets();
		for (SChildCareFrame childF : sChildCFs) {
			// 編集状態を確認
			Pair<Integer, Integer> pairItem = itemIdFromNo(childF.timeSlot);
			if (!editStates.stream().filter(x -> x.getEditState().getAttendanceItemId() == pairItem.getLeft()
					|| x.getEditState().getAttendanceItemId() == pairItem.getRight()).findAny().isPresent()) {
				// 短時間勤務時間帯を取り
				Optional<ShortWorkingTimeSheet> sWTimeSheetOpt = sWTimeSheets.stream()
						.filter(s -> s.getShortWorkTimeFrameNo().v() == childF.timeSlot).findFirst();

				if (!sWTimeSheetOpt.isPresent())
					continue;

				sWTimeSheets.remove(sWTimeSheetOpt.get());
				// 時間帯を作成
				ShortWorkingTimeSheet createNew = new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(childF.timeSlot),
						sWTimeSheetOpt.get().getChildCareAttr(), childF.getStartTime(), childF.getEndTime(),
						new AttendanceTime(0), new AttendanceTime(0));
				sWTimeSheets.add(createNew);

			}
		}

	}

	// 短時間勤務をクリア
	public void clear(List<EditStateOfDailyPerformance> editStates) {

		// 時間帯を取得
		List<ShortWorkingTimeSheet> sWTimeSheets = this.getTimeZone().getShortWorkingTimeSheets();
		if (sWTimeSheets.isEmpty())
			return;

		List<Integer> attendances = editStates.stream().map(x -> x.getEditState().getAttendanceItemId()).distinct()
				.collect(Collectors.toList());

		// 時間帯を削除
		this.getTimeZone().getShortWorkingTimeSheets()
				.removeIf(x -> !attendances.contains(itemIdFromNo(x.getShortWorkTimeFrameNo().v()).getLeft())
						&& !attendances.contains(itemIdFromNo(x.getShortWorkTimeFrameNo().v()).getRight()));
	}

	// Pair<育児開始時刻, 育児終了時刻>
	private Pair<Integer, Integer> itemIdFromNo(int timSlot) {
		if (timSlot == 1) {
			return Pair.of(759, 760);
		}
		return Pair.of(761, 762);
	}
}

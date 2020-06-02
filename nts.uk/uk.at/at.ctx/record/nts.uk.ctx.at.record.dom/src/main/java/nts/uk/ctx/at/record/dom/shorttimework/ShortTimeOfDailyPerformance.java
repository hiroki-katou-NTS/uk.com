package nts.uk.ctx.at.record.dom.shorttimework;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.primitivevalue.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;

/**
 * 
 * @author nampt 日別実績の短時間勤務時間帯 - root
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShortTimeOfDailyPerformance extends AggregateRoot {

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 時間帯: 短時間勤務時間帯 */
	private List<ShortWorkingTimeSheet> shortWorkingTimeSheets;

	/** 年月日: 年月日 */
	private GeneralDate ymd;

	// 短時間勤務を変更
	public void change(ShortWorkTimeHistoryItem shortWTHistItem, List<EditStateOfDailyPerformance> editStates) {

		// 時間帯を取得
		List<SChildCareFrame> sChildCFs = shortWTHistItem.getLstTimeSlot();

		if (sChildCFs.isEmpty())
			return;

		List<ShortWorkingTimeSheet> sWTimeSheets = this.getShortWorkingTimeSheets();
		for (SChildCareFrame childF : sChildCFs) {
			if (!editStates.stream().filter(x -> x.getAttendanceItemId() == childF.timeSlot).findAny().isPresent()) {
				// 時間帯を作成
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
		List<ShortWorkingTimeSheet> sWTimeSheets = this.getShortWorkingTimeSheets();
		if (sWTimeSheets.isEmpty())
			return;

		List<Integer> attendances = editStates.stream().map(x -> x.getAttendanceItemId()).distinct()
				.collect(Collectors.toList());

		// 時間帯を削除
		this.getShortWorkingTimeSheets().removeIf(x -> !attendances.contains(x.getShortWorkTimeFrameNo().v()));
	}

}

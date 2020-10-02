package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;

/**
 * 日別勤怠の短時間勤務時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.短時間勤務.日別勤怠の短時間勤務時間帯
 * @author tutk
 *
 */
@Getter
public class ShortTimeOfDailyAttd implements DomainObject{
	/** 時間帯 */
	private List<ShortWorkingTimeSheet> shortWorkingTimeSheets;

	public ShortTimeOfDailyAttd(List<ShortWorkingTimeSheet> shortWorkingTimeSheets) {
		super();
		this.shortWorkingTimeSheets = shortWorkingTimeSheets;
	}

	public void setShortWorkingTimeSheets(List<ShortWorkingTimeSheet> shortWorkingTimeSheets) {
		this.shortWorkingTimeSheets = shortWorkingTimeSheets;
	}
	
	// 短時間勤務を変更
	public static List<ShortWorkingTimeSheet> change(ShortWorkTimeHistoryItem shortWTHistItem, List<EditStateOfDailyAttd> editStates) {

		// 時間帯を取得
		List<SChildCareFrame> sChildCFs = shortWTHistItem.getLstTimeSlot();

		//List<ShortWorkingTimeSheet> sWTimeSheets = this.shortWorkingTimeSheets;
		List<ShortWorkingTimeSheet> sWTimeSheets = new ArrayList<>();
		for (SChildCareFrame childF : sChildCFs) {
			// 編集状態を確認
			Pair<Integer, Integer> pairItem = itemIdFromNo(childF.timeSlot);
			if (!editStates.stream().filter(x -> x.getAttendanceItemId() == pairItem.getLeft()
					|| x.getAttendanceItemId() == pairItem.getRight()).findAny().isPresent()) {
				// 短時間勤務時間帯を取り
//				Optional<ShortWorkingTimeSheet> sWTimeSheetOpt = sWTimeSheets.stream()
//						.filter(s -> s.getShortWorkTimeFrameNo().v() == childF.timeSlot).findFirst();
//
//				if (!sWTimeSheetOpt.isPresent())
//					continue;

				//sWTimeSheets.remove(sWTimeSheetOpt.get());
				// 時間帯を作成
				ShortWorkingTimeSheet createNew = new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(childF.timeSlot),
						ChildCareAttribute.decisionValue(shortWTHistItem.getChildCareAtr().value), childF.getStartTime(), childF.getEndTime(),
						new AttendanceTime(0), new AttendanceTime(0));
				sWTimeSheets.add(createNew);

			}
		}

		return sWTimeSheets;
	}

	// 短時間勤務をクリア
	public void clear(List<EditStateOfDailyAttd> editStates) {

		// 時間帯を取得
		List<ShortWorkingTimeSheet> sWTimeSheets = this.getShortWorkingTimeSheets();
		if (sWTimeSheets.isEmpty())
			return;

		List<Integer> attendances = editStates.stream().map(x -> x.getAttendanceItemId()).distinct()
				.collect(Collectors.toList());

		// 時間帯を削除
		this.getShortWorkingTimeSheets()
				.removeIf(x -> !attendances.contains(itemIdFromNo(x.getShortWorkTimeFrameNo().v()).getLeft())
						&& !attendances.contains(itemIdFromNo(x.getShortWorkTimeFrameNo().v()).getRight()));
	}

	// Pair<育児開始時刻, 育児終了時刻>
	private static Pair<Integer, Integer> itemIdFromNo(int timSlot) {
		if (timSlot == 1) {
			return Pair.of(759, 760);
		}
		return Pair.of(761, 762);
	}
}

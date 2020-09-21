package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday.preparetimeframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.TimeFrame;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.AttendanceAtr;

/**
 * 時間帯の枠を用意する
 * 
 * @author tutk
 *
 */
@Stateless
public class PrepareTimeFrame {

	/**
	 * 
	 * @param maxNumberOfUses
	 *            最大枠件数
	 * @param listTimeFrame
	 *            時間帯一覧
	 * @param attendanceAtr
	 *            打刻種類
	 */
	public void prepareTimeFrame(int maxNumberOfUses, List<TimeFrame> listTimeFrame, AttendanceAtr attendanceAtr) {
		// パラメータ。打刻種類を確認する
		if (attendanceAtr != AttendanceAtr.GO_OUT) {
			// 開始時刻順に時間帯をソートする (Sắp xếp 時間帯)
			// sort list start
			List<TimeFrame> listTimeFrameStart = listTimeFrame.stream()
					.filter(x -> x.getStart().isPresent() && x.getStart().get().getTimeVacation().isPresent())
					.collect(Collectors.toList());
			listTimeFrameStart = listTimeFrameStart.stream()
					.sorted((x, y) -> x.getStart().get().getTimeVacation().get().getStart().v()
							- y.getStart().get().getTimeVacation().get().getStart().v())
					.collect(Collectors.toList());
			// sort list end

			List<TimeFrame> listTimeFrameEnd = listTimeFrame.stream()
					.filter(x -> !x.getStart().isPresent() && !x.getStart().get().getTimeVacation().isPresent()
							&& x.getEnd().isPresent() && x.getEnd().get().getTimeVacation().isPresent())
					.collect(Collectors.toList());
			listTimeFrameEnd = listTimeFrameEnd.stream()
					.sorted((x, y) -> x.getStart().get().getTimeVacation().get().getStart().v()
							- y.getStart().get().getTimeVacation().get().getStart().v())
					.collect(Collectors.toList());

			List<TimeFrame> listNew = new ArrayList<>();
			listNew.addAll(listTimeFrameStart);
			listNew.addAll(listTimeFrameEnd);
			listTimeFrame = listNew;
		}

		// 最大使用回数を確認する
		if (listTimeFrame.size() > maxNumberOfUses) {
			for (int i = listTimeFrame.size(); i > maxNumberOfUses; i--) {
				listTimeFrame.remove(i);
			}
		} else if (listTimeFrame.size() < maxNumberOfUses) {
			for (int i =1; i <= maxNumberOfUses; i++) {
				boolean checkExist = false;
				for (int workNo : listTimeFrame.stream().map(c->c.getFrameNo()).collect(Collectors.toList())) {
					if(workNo == i) {
						checkExist = true;
						break;
					}
				}
				if(!checkExist) {
					listTimeFrame.add(new TimeFrame(0, i, Optional.empty(), Optional.empty(), null));
				}
			}
		}

	}

}

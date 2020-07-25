package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.TimeFrame;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday.preparetimeframe.PrepareTimeFrame;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.AttendanceAtr;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;

/**
 * 時間帯反映する (new_2020)
 * 
 * @author tutk
 *
 */
@Stateless
public class ReflectTimeOfDay {
	@Inject
	private PrepareTimeFrame prepareTimeFrame;
	public void reflectTimeOfDay(String employeeId, GeneralDate ymd, Stamp stamp, int maxNumberOfUses,
			List<TimeFrame> listTimeFrame, AttendanceAtr attendanceAtr) {
		//時間帯の枠を用意する
		prepareTimeFrame.prepareTimeFrame(maxNumberOfUses, listTimeFrame, attendanceAtr);
		
		for(TimeFrame timeFrame : listTimeFrame) {
			if(stamp.getType().getChangeClockArt() == ChangeClockArt.RETURN || 
			   stamp.getType().getChangeClockArt() == ChangeClockArt.END_OF_SUPPORT ) {
				//TODO:(TKT)
			}else if(stamp.getType().getChangeClockArt() == ChangeClockArt.GO_OUT || 
					   stamp.getType().getChangeClockArt() == ChangeClockArt.START_OF_SUPPORT ) {
				//TODO:(TKT)
			}
		}
		//空っぽの時間帯を削除する
		listTimeFrame = listTimeFrame.stream().filter(x -> x.getStart().isPresent() || x.getEnd().isPresent())
				.collect(Collectors.toList());
		//枠NOに番号付けする
		for(int i =1;i<=listTimeFrame.size();i++) {
			listTimeFrame.get(i-1).setFrameNo(i);
		}
	}
}

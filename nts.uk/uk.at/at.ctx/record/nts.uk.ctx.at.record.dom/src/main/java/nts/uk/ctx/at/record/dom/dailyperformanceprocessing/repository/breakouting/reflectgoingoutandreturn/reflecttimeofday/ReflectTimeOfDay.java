package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.TimeFrame;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday.imprint.ImprintReflectTimeOfDay;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.breakouting.reflectgoingoutandreturn.reflecttimeofday.preparetimeframe.PrepareTimeFrame;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.AttendanceAtr;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

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
	
	@Inject
	private ImprintReflectTimeOfDay imprintReflectTimeOfDay;
	public void reflectTimeOfDay(String employeeId, GeneralDate ymd, Stamp stamp, int maxNumberOfUses,
			List<TimeFrame> listTimeFrame, AttendanceAtr attendanceAtr,WorkTimeCode workTimeCode) {
		//時間帯の枠を用意する
		prepareTimeFrame.prepareTimeFrame(maxNumberOfUses, listTimeFrame, attendanceAtr);
		
		for(int i = 0;i<listTimeFrame.size();i++) {
			TimeFrame timeFrame = listTimeFrame.get(i);
			Optional<TimeFrame> timeFrameNext = Optional.empty();
			if((i+1) < listTimeFrame.size()) {
				timeFrameNext = Optional.of(listTimeFrame.get(i+1));
			}
			//戻りOR終了の場合
			if(stamp.getType().getChangeClockArt() == ChangeClockArt.RETURN || 
			   stamp.getType().getChangeClockArt() == ChangeClockArt.END_OF_SUPPORT ) {
				//打刻反映する
				imprintReflectTimeOfDay.imprint(false, timeFrame, timeFrameNext, stamp, timeFrame.getEnd(),workTimeCode,ymd);
			//外出OR開始の場合
			}else if(stamp.getType().getChangeClockArt() == ChangeClockArt.GO_OUT || 
					   stamp.getType().getChangeClockArt() == ChangeClockArt.START_OF_SUPPORT ) {
				//打刻反映する
				imprintReflectTimeOfDay.imprint(true, timeFrame, timeFrameNext, stamp, timeFrame.getEnd(),workTimeCode,ymd);
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

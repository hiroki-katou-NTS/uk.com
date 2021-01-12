package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.screen.at.app.ksu001.processcommon.CorrectWorkTimeHalfDay;
import nts.uk.screen.at.app.ksu001.processcommon.CorrectWorkTimeHalfDayParam;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class CorrectWorkTimeHalfDayKSu002 {

	@Inject
	private CorrectWorkTimeHalfDay correctWorkTimeHalfDay;

	public CorrectWorkTimeHalfDayOutput get(CorrectWorkTimeHalfDayParam param) {
		
		WorkInfoAndTimeZone zone = this.correctWorkTimeHalfDay.handle(param);
		
		CorrectWorkTimeHalfDayOutput result = new CorrectWorkTimeHalfDayOutput();
		
		Optional<TimeZone> time =  zone.getTimeZones().stream().findFirst();
		
		if (time.isPresent()) {
			result.setStartTime(time.get().getStart().v());
			result.setEndTime(time.get().getEnd().v());
		}
		
		return result;
	}
	
}

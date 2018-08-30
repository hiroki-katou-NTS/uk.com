package nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave.nexttime;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;

@Stateless
public class NextTimeEventFinder {
	
	public NextTimeEventDto getNextTimeData(NextTimeEventParam param) {
		return new NextTimeEventDto(GeneralDate.max().toString(), "0.0日", "0:00");
	}

}

package nts.uk.ctx.at.record.app.find.dailyperform.resttime;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.RestTimeZoneOfDailyDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
/** 日別実績の休憩時間帯 Finder */
public class RestTimeZoneOfDailyFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public RestTimeZoneOfDailyDto find() {
		// TODO Auto-generated method stub
		return new RestTimeZoneOfDailyDto();
	}

}

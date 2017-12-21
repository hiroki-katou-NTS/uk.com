package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemOfDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績の任意項目 Finder*/
@Stateless
public class OptionalItemOfDailyPerformFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public OptionalItemOfDailyPerformDto find() {
		// TODO Auto-generated method stub
		return new OptionalItemOfDailyPerformDto();
	}

}

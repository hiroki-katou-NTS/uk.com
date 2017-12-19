package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto.SpecificDateAttrOfDailyPerforDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績の特定日区分 Finder*/
@Stateless
public class SpecificDateAttrOfDailyPerforFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public SpecificDateAttrOfDailyPerforDto find() {
		// TODO Auto-generated method stub
		return new SpecificDateAttrOfDailyPerforDto();
	}

}

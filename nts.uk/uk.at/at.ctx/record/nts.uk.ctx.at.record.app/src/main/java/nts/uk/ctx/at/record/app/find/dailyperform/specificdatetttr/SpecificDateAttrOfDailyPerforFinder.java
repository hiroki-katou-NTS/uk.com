package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto.SpecificDateAttrOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績の特定日区分 Finder */
@Stateless
public class SpecificDateAttrOfDailyPerforFinder extends FinderFacade {

	@Inject
	private SpecificDateAttrOfDailyPerforRepo repo;

	@SuppressWarnings("unchecked")
	@Override
	public SpecificDateAttrOfDailyPerforDto find(String employeeId, GeneralDate baseDate) {
		return SpecificDateAttrOfDailyPerforDto.getDto(this.repo.find(employeeId, baseDate).orElse(null));
	}

}

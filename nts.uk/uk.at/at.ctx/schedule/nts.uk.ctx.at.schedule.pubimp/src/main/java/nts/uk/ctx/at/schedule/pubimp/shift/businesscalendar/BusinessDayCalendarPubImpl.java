package nts.uk.ctx.at.schedule.pubimp.shift.businesscalendar;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.BusinessDayCalendarService;
import nts.uk.ctx.at.schedule.dom.shift.management.TargetDaysHDCls;
import nts.uk.ctx.at.schedule.pub.shift.management.BusinessDayCalendarExport;
import nts.uk.ctx.at.schedule.pub.shift.management.BusinessDayCalendarPub;
import nts.uk.ctx.at.schedule.pub.shift.management.HolidayClsExport;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class BusinessDayCalendarPubImpl implements BusinessDayCalendarPub {
	@Inject
	private BusinessDayCalendarService businessDayCalendarService;

	@Override
	public Optional<BusinessDayCalendarExport> acquiredHolidayClsOfTargetDate(String companyId, String workPlaceId, GeneralDate date) {
		Optional<TargetDaysHDCls> output = businessDayCalendarService.acquiredHolidayClsOfTargetDate(companyId, workPlaceId, date);
		if(output.isPresent()) {
			BusinessDayCalendarExport businessDayCal = new BusinessDayCalendarExport(EnumAdaptor.valueOf(output.get().holidayCls.value, HolidayClsExport.class), output.get().targetDate);
			return Optional.of(businessDayCal);
		}
		
		return Optional.empty();
	}
}

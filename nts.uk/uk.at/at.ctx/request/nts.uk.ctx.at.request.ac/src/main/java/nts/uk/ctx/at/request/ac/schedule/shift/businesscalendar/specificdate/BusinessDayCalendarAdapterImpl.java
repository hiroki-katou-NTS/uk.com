package nts.uk.ctx.at.request.ac.schedule.shift.businesscalendar.specificdate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.BusinessDayCalendarAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.BusinessDayCalendarImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.HolidayClsImport;
import nts.uk.ctx.at.schedule.pub.shift.management.BusinessDayCalendarExport;
import nts.uk.ctx.at.schedule.pub.shift.management.BusinessDayCalendarPub;

/**
 * @author loivt
 *
 */
@Stateless
public class BusinessDayCalendarAdapterImpl implements BusinessDayCalendarAdapter {
	@Inject
	private BusinessDayCalendarPub businessDayCalendarPub;
	@Override
	public Optional<BusinessDayCalendarImport> acquiredHolidayClsOfTargetDate(String companyId, String workPlaceId,
			GeneralDate date) {
		
		Optional<BusinessDayCalendarExport> buOptional = this.businessDayCalendarPub.acquiredHolidayClsOfTargetDate(companyId, workPlaceId, date);
		if(!buOptional.isPresent()){
			return Optional.empty();
		}
		BusinessDayCalendarImport businessDayCalendarImport = new BusinessDayCalendarImport(EnumAdaptor.valueOf(buOptional.get().holidayCls.value, HolidayClsImport.class), buOptional.get().getTargetDate());
		Optional<BusinessDayCalendarImport> optBusinessDayCalendarImport = Optional.of(businessDayCalendarImport);
		return optBusinessDayCalendarImport;
	}

}

package nts.uk.ctx.at.function.ac.nursingcareleavemanagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.adapter.nursingcareleavemanagement.CareHolidayMngAdapter;
import nts.uk.ctx.at.function.dom.adapter.nursingcareleavemanagement.CareHolidayMngImported;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service.CareHolidayMngService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CareHolidayMngFinder implements CareHolidayMngAdapter {

	@Inject
	private CareHolidayMngService careHolidayMngService;

	@Override
	public List<CareHolidayMngImported> calCareRemainOfInPerior(String cid, String sid, DatePeriod dateData,
			boolean mode) {
		// 343 && 344
		careHolidayMngService.calCareRemainOfInPerior(cid, sid, dateData, mode);
		// TODO Auto-generated method stub
		return null;
	}

}

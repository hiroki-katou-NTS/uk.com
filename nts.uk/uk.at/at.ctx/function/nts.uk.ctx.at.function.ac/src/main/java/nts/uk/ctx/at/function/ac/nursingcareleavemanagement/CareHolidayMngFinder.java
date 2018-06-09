package nts.uk.ctx.at.function.ac.nursingcareleavemanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.adapter.nursingcareleavemanagement.CareHolidayMngAdapter;
import nts.uk.ctx.at.function.dom.adapter.nursingcareleavemanagement.NursingCareLeaveImported;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNursingRemainOutputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service.CareHolidayMngService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CareHolidayMngFinder implements CareHolidayMngAdapter {

	@Inject
	private CareHolidayMngService careHolidayMngService;

	@Override
	public NursingCareLeaveImported calCareRemainOfInPerior(String cid, String sid, DatePeriod dateData,
			boolean mode) {
		// 343 && 344
		ChildCareNursingRemainOutputPara childCareNursingRemainOutputPara = careHolidayMngService.calCareRemainOfInPerior(cid, sid, dateData, mode);
		if(childCareNursingRemainOutputPara == null) return null;
		return new NursingCareLeaveImported(childCareNursingRemainOutputPara.getBeforeUseDays(), childCareNursingRemainOutputPara.getBeforeCareLeaveDays());
	}

}

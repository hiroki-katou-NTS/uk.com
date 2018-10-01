package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface PlanAnnualUserDetailExport {
	/**
	 * 
	 * @param sId
	 * @param workTypeCd
	 * @param dateData
	 * @return
	 */
	List<GeneralDate> lstPlanDetail(String sId, String workTypeCd, DatePeriod dateData);

}

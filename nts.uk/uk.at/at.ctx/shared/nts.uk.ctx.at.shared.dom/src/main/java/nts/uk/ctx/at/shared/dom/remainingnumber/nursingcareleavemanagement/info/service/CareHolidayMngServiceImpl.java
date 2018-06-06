package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNursingRemainOutputPara;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class CareHolidayMngServiceImpl implements CareHolidayMngService{

	@Override
	public List<ChildCareNursingRemainOutputPara> calCareRemainOfInPerior(String cid, String sid, DatePeriod dateData,
			boolean mode) {
		List<ChildCareNursingRemainOutputPara> lstOutData = new ArrayList<>();
		// TODO Auto-generated method stub
		return lstOutData;
	}

}

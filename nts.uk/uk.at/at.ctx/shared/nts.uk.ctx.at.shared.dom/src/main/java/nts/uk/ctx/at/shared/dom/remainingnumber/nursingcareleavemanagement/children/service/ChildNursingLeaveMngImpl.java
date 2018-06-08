package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class ChildNursingLeaveMngImpl implements ChildNursingLeaveMng{

	@Override
	public List<ChildCareNursingRemainOutputPara> calChildNursOfInPeriod(String cid, String sid, DatePeriod dateData,
			boolean mode) {
		List<ChildCareNursingRemainOutputPara> lstOutData = new ArrayList<>();
		// TODO Auto-generated method stub
		return lstOutData;
	}

}

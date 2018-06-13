package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class ChildNursingLeaveMngImpl implements ChildNursingLeaveMng{

	@Override
	public ChildCareNursingRemainOutputPara calChildNursOfInPeriod(String cid, String sid, DatePeriod dateData,
			boolean mode) {
		ChildCareNursingRemainOutputPara outData = new ChildCareNursingRemainOutputPara(false,false,0d,0d,Optional.empty(),Optional.empty());
		// TODO Auto-generated method stub
		return outData;
	}

}

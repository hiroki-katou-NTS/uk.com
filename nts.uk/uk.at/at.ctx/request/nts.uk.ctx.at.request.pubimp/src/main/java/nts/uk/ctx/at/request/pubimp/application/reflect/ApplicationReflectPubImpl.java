package nts.uk.ctx.at.request.pubimp.application.reflect;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.pub.application.reflect.ApplicationReflectPub;
import nts.uk.ctx.at.request.pub.application.reflect.WorkReflectAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ApplicationReflectPubImpl implements ApplicationReflectPub{

	@Override
	public boolean applicationRellect(String workId, WorkReflectAtr workAtr, DatePeriod workDate) {
		// TODO Auto-generated method stub
		return false;
	}

}

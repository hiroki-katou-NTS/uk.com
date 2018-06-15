/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.vacation.setting.nursingleave;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.ChildNursingRemainExport;
import nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.ShNursingLeaveSettingPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ShNursingLeaveSettingPubImpl.
 */
@Stateless
public class ShNursingLeaveSettingPubImpl implements ShNursingLeaveSettingPub {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.
	 * ShNursingLeaveSettingPub#aggrChildNursingRemainPeriod(java.lang.String,
	 * java.lang.String, nts.uk.shr.com.time.calendar.period.DatePeriod,
	 * java.lang.Integer)
	 */
	@Override
	public ChildNursingRemainExport aggrChildNursingRemainPeriod(String companyId,
			String employeeId, DatePeriod period, Integer mode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.
	 * ShNursingLeaveSettingPub#aggrNursingRemainPeriod(java.lang.String,
	 * java.lang.String, nts.uk.shr.com.time.calendar.period.DatePeriod,
	 * java.lang.Integer)
	 */
	@Override
	public ChildNursingRemainExport aggrNursingRemainPeriod(String companyId, String employeeId,
			DatePeriod period, Integer mode) {
		// TODO Auto-generated method stub
		return null;
	}

}

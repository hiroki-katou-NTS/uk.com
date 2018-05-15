/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.person;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class EmployeeInfoQueryModel.
 */
public class EmployeeInfoDto {
	
	/** The peroid. */
	public DatePeriod peroid;
	
	/** The employee ids. */
	public List<String> employeeIds;
	
	/** The is find classsification info. */
	public boolean isFindClasssificationInfo;
	
	/** The is find job tilte info. */
	public boolean isFindJobTilteInfo;
	
	/** The is find work place info. */
	public boolean isFindWorkPlaceInfo;
	
	/** The is find employment info. */
	public boolean isFindEmploymentInfo;
	
	/** The is find bussiness type info. */
	public boolean isFindBussinessTypeInfo;
}

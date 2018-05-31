/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.person;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class EmployeeInfoQueryModel.
 */
@Data
@Builder
public class EmployeeInfoQueryModel {
	
	/** The peroid. */
	private DatePeriod peroid;
	
	/** The employee ids. */
	private List<String> employeeIds;
	
	/** The is find classsification info. */
	private boolean isFindClasssificationInfo;
	
	/** The is find job tilte info. */
	private boolean isFindJobTilteInfo;
	
	/** The is find work place info. */
	private boolean isFindWorkPlaceInfo;
	
	/** The is find employment info. */
	private boolean isFindEmploymentInfo;
	
	/** The is find bussiness type info. */
	private boolean isFindBussinessTypeInfo;
}

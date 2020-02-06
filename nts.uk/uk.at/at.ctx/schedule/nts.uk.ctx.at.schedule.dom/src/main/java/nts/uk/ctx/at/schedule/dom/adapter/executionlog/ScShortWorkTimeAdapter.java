/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.adapter.executionlog;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface ScShortWorkTimeAdapter.
 */
public interface ScShortWorkTimeAdapter {

	List<ShortWorkTimeDto> findShortWorkTimes(List<String> empIds, DatePeriod period);
}

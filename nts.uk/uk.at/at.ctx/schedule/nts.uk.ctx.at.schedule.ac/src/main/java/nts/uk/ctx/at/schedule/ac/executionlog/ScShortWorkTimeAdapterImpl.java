/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ac.executionlog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScShortWorkTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortChildCareFrameDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareAtr;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortChildCareFrameExport;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortWorkTimeExport;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortWorkTimePub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ScShortWorkTimeAdapterImpl.
 */
@Stateless
public class ScShortWorkTimeAdapterImpl implements ScShortWorkTimeAdapter {

	/** The short work time pub. */
	@Inject
	private ShShortWorkTimePub shortWorkTimePub;

	
	@Override
	public List<ShortWorkTimeDto> findShortWorkTimes(List<String> empIds, DatePeriod period) {
		return this.shortWorkTimePub.findShortWorkTimes(empIds, period).stream()
				.map(export -> this.convertExport(export)).collect(Collectors.toList());
	}

	/**
	 * Convert export.
	 *
	 * @param export
	 *            the export
	 * @return the short work time dto
	 */
	private ShortWorkTimeDto convertExport(ShShortWorkTimeExport export) {
		ShortWorkTimeDto dto = new ShortWorkTimeDto();
		dto.setChildCareAtr(ChildCareAtr.valueOf(export.getChildCareAtr().value));
		dto.setEmployeeId(export.getEmployeeId());
		dto.setPeriod(export.getPeriod());
		dto.setLstTimeSlot(export.getLstTimeSlot().stream()
				.map(exportChildCare -> this.convertChildCareExport(exportChildCare)).collect(Collectors.toList()));
		return dto;
	}

	/**
	 * Convert child care export.
	 *
	 * @param export
	 *            the export
	 * @return the short child care frame dto
	 */
	private ShortChildCareFrameDto convertChildCareExport(ShShortChildCareFrameExport export) {
		ShortChildCareFrameDto dto = new ShortChildCareFrameDto();
		dto.setEndTime(export.getEndTime());
		dto.setStartTime(export.getStartTime());
		dto.setTimeSlot(export.getTimeSlot());
		return dto;
	}
}

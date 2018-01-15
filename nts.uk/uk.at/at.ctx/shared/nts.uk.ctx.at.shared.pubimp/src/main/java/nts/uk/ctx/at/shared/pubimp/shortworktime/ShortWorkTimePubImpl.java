/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.shortworktime;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.pub.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortChildCareFrameExport;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortWorkTimeExport;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortWorkTimePub;

/**
 * The Class ShortWorkTimePubImpl.
 */
@Stateless
public class ShortWorkTimePubImpl implements ShShortWorkTimePub {

	/** The work time hist repo. */
	@Inject
	private SWorkTimeHistoryRepository workTimeHistRepo;
	
	/** The work time hist item repo. */
	@Inject
	private SWorkTimeHistItemRepository workTimeHistItemRepo;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.pub.shortworktime.ShShortWorkTimePub#
	 * findShortWorkTime(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<ShShortWorkTimeExport> findShortWorkTime(String empId, GeneralDate baseDate) {
		// find short work time history
		Optional<ShortWorkTimeHistory> opWorkTimeHist = this.workTimeHistRepo.findByBaseDate(empId, baseDate);
		if (!opWorkTimeHist.isPresent()) {
			return Optional.empty();
		}
		ShortWorkTimeHistory workTimeHist = opWorkTimeHist.get();
		
		// find short work time history item
		Optional<ShortWorkTimeHistoryItem> opWorkTimeHistItem = this.workTimeHistItemRepo.findByKey(empId,
				workTimeHist.getHistoryItems().get(0).identifier());
		
		if (!opWorkTimeHistItem.isPresent()) {
			throw new RuntimeException(String.format("Short work history item %s not found.",
					workTimeHist.getHistoryItems().get(0).identifier()));
		}
		ShortWorkTimeHistoryItem workTimeHistItem = opWorkTimeHistItem.get();
		
		// return value
		return Optional.of(ShShortWorkTimeExport.builder()
				.employeeId(workTimeHist.getEmployeeId())
				.period(workTimeHist.getHistoryItems().get(0).span())
				.childCareAtr(ChildCareAtr.valueOf(workTimeHistItem.getChildCareAtr().value))
				.lstTimeSlot(workTimeHistItem.getLstTimeSlot().stream()
						.map(item -> {
							return ShShortChildCareFrameExport.builder()
									.timeSlot(item.getTimeSlot())
									.startTime(item.getStartTime())
									.endTime(item.getEndTime())
									.build();
						})
						.collect(Collectors.toList()))
				.build());
	}

}

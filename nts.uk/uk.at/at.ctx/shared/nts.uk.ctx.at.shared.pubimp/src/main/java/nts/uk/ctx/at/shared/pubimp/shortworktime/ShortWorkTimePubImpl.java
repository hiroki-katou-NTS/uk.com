/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.shortworktime;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.pub.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortChildCareFrameExport;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortWorkTimeExport;
import nts.uk.ctx.at.shared.pub.shortworktime.ShShortWorkTimePub;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.pub.shortworktime.ShShortWorkTimePub#
	 * findShortWorkTimes(java.util.List,
	 * nts.arc.time.calendar.period.DatePeriod)
	 */
	@Override
	public List<ShShortWorkTimeExport> findShortWorkTimes(List<String> empIds, DatePeriod period) {
		if(CollectionUtil.isEmpty(empIds)) {
			return Collections.emptyList();
		}
		
		// find short work time history
		//ドメインモデル「短時間勤務履歴」を取得する
		List<ShortWorkTimeHistory> opWorkTimeHist = this.workTimeHistRepo
				.findLstByEmpAndPeriod(empIds, period);

		List<DateHistoryItem> dateHistoryItems = opWorkTimeHist.stream()
				.flatMap(shortWorkTimeHistory -> shortWorkTimeHistory.getHistoryItems().stream())
				.collect(Collectors.toList());

		List<String> historyIds = dateHistoryItems.stream().map(DateHistoryItem::identifier)
				.collect(Collectors.toList());
		//ドメインモデル「短時間勤務履歴項目」を取得する
		List<ShortWorkTimeHistoryItem> shortWorkTimeHistoryItems = this.workTimeHistItemRepo
				.findByHistIds(historyIds);

		Map<String, ShortWorkTimeHistoryItem> mapShortWorkTimeHistoryItem = shortWorkTimeHistoryItems
				.stream().collect(Collectors.toMap(ShortWorkTimeHistoryItem::getHistoryId,
						Function.identity()));
		
		if(CollectionUtil.isEmpty(opWorkTimeHist) || CollectionUtil.isEmpty(shortWorkTimeHistoryItems)) {
			return Collections.emptyList();
		}

		// return value
		//取得した「短時間勤務履歴」(List)、「短時間勤務履歴項目」(List)を返す
		return opWorkTimeHist.parallelStream().map(workTimeHist -> {
			ShortWorkTimeHistoryItem workTimeHistItem = mapShortWorkTimeHistoryItem
					.get(workTimeHist.getHistoryItems().get(0).identifier());
			return ShShortWorkTimeExport.builder().employeeId(workTimeHist.getEmployeeId())
					.period(workTimeHist.getHistoryItems().get(0).span())
					.childCareAtr(ChildCareAtr.valueOf(workTimeHistItem.getChildCareAtr().value))
					.lstTimeSlot(workTimeHistItem.getLstTimeSlot().stream().map(item -> {
						return ShShortChildCareFrameExport.builder().timeSlot(item.getTimeSlot())
								.startTime(item.getStartTime()).endTime(item.getEndTime()).build();
					}).collect(Collectors.toList())).build();
		}).collect(Collectors.toList());

	}

}

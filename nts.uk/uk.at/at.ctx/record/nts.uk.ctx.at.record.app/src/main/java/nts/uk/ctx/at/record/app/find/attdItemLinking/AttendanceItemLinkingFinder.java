/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.attdItemLinking;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttdItemLinkRequest;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapterDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceItemLinkingFinder.
 * 
 * @author anhnm
 */
@Stateless
public class AttendanceItemLinkingFinder {

	/** The frame adapter. */
	@Inject
	private FrameNoAdapter frameAdapter;

	/** The opt item repo. */
	@Inject
	private OptionalItemRepository optItemRepo;

	/** The Constant MONTHLY_FRAME_TYPE. */
	private static final int MONTHLY_FRAME_TYPE = 2;

	/** The Constant DAILY_FRAME_TYPE. */
	private static final int DAILY_FRAME_TYPE = 1;

	/** The Constant FRAME_CATEGORY_OPTIONAL_ITEM. */
	private static final int FRAME_CATEGORY_OPTIONAL_ITEM = 8;

	/**
	 * Find by any item.
	 *
	 * @param request the request
	 * @return the list
	 */
	public List<FrameNoAdapterDto> findByAnyItem(AttdItemLinkRequest request) {
		// find list optional item by attribute
		List<Integer> filteredByAtr = this.optItemRepo
				.findByAtr(AppContexts.user().companyId(), request.getFormulaAtr()).stream()
				.filter(ii -> ii.isUsed()).map(OptionalItem::getOptionalItemNo)
				.map(OptionalItemNo::v).collect(Collectors.toList());

		// filter list optional item by selectable list parameters.
		List<Integer> filteredBySelectableList = request.getAnyItemNos().stream()
				.filter(itemNo -> filteredByAtr.contains(itemNo))
				.collect(Collectors.toList());

		// return list AttendanceItemLinking after filtered by list optional item.
		return this.frameAdapter.getByAnyItem(convertToFrameType(request.getPerformanceAtr())).stream()
				.filter(item -> filteredBySelectableList.contains(item.getFrameNo())).collect(Collectors.toList());
	}

	/**
	 * Find attendance by optional item.
	 *
	 * @param optionalItemNos the optional item nos
	 * @param performanceAtr the performance atr
	 * @return the list
	 */
	public List<FrameNoAdapterDto> findAttendanceByOptionalItem(List<Integer> optionalItemNos, int performanceAtr) {
		return this.frameAdapter.findByFrameNos(optionalItemNos, performanceAtr, FRAME_CATEGORY_OPTIONAL_ITEM);
	}

	/**
	 * Convert to frame type.
	 *
	 * @param performanceAtr the performance atr
	 * @return the int
	 */
	private int convertToFrameType(int performanceAtr) {
		return performanceAtr == PerformanceAtr.DAILY_PERFORMANCE.value ? DAILY_FRAME_TYPE : MONTHLY_FRAME_TYPE;
	}

}

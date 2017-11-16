/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.pub.attendanceItemAndFrameLinking;

import java.util.List;

public interface AttendanceItemLinkingPub {
	List<AttendanceItemLinkingDto> getFrameNo(List<Integer> attendanceItemIds);

	/**
	 * Gets the by any item category.
	 *
	 * @param typeOfItem the type of item
	 * @return the by any item category
	 * 
	 * @author anhnm
	 */
	List<AttendanceItemLinkingDto> getByAnyItemCategory(int typeOfItem);
}

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

	/**
	 * Find by frame nos.
	 *
	 * @param frameNos the frame nos
	 * @param typeOfItem the type of item
	 * @param frameCategory the frame category
	 * @return the list
	 */
	List<AttendanceItemLinkingDto> findByFrameNos(List<Integer> frameNos, int typeOfItem, int frameCategory);
}

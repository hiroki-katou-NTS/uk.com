/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.attendanceitemframelinking.repository;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.function.dom.attendanceitemframelinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.FrameCategory;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;

public interface AttendanceItemLinkingRepository {
	
	List<AttendanceItemLinking> getByAttendanceId(List<Integer> attendanceItemIds);
	
	/**
	 * @author tutk
	 * Get Full Data AttendanceItemLinking 
	 * @param attendanceItemIds
	 * @return
	 */
	List<AttendanceItemLinking> getFullDataByListAttdaId(List<Integer> attendanceItemIds);
	/**
	 * Gets the by any item category.
	 *
	 * @param type the type
	 * @return the by any item category
	 * 
	 * @author anhnm
	 */
	List<AttendanceItemLinking> getByAnyItemCategory(TypeOfItem type);

	/**
	 * Find by frame nos.
	 *
	 * @param frameNos the frame nos
	 * @param typeOfItem the type of item
	 * @param frameCategory the frame category
	 * @return the list
	 */
	List<AttendanceItemLinking> findByFrameNos(List<Integer> frameNos, int typeOfItem, int frameCategory);
	
	List<AttendanceItemLinking> getByAttendanceIdAndType(List<Integer> attendanceItemIds, TypeOfItem type);
	/**
	 * @author tutk
	 * Get Full Data AttendanceItemLinking  by type and list attendanceItemIds
	 * @param attendanceItemIds
	 * @return
	 */
	List<AttendanceItemLinking> getFullDataByAttdIdAndType(List<Integer> attendanceItemIds, TypeOfItem type);
	

	/**
	 * @author tutk
	 * Get Full Data AttendanceItemLinking  by type and list attendanceItemIds and category
	 * @param attendanceItemIds
	 * @return
	 */
	List<AttendanceItemLinking> getFullDataByAttdIdAndTypeAndCategory(List<Integer> attendanceItemIds, TypeOfItem type,int frameCategory);
	
	
	/**
	 * List<????????????????????????????????????ID?????????????????? Nh???n d??nh s??ch < Attendance items li??n quan ?????n Overtime ID  kh??ng th??? s??? d???ng ???????c>
	 *
	 * @param frameNos List<???NO>
	 * @param typeOfItem ?????????????????????
	 * @param frameCategory List<???????????????>
	 * @return List<????????????ID>
	 */
	List<AttendanceItemLinking> findByFrameNoTypeAndFramCategory(List<BigDecimal> frameNos, int typeOfItem, List<Integer> frameCategory);
	
	
	/**
	 * Find by frame no type and fram category and frame no.
	 *
	 * @param frameNos the frame nos
	 * @param typeOfItem the type of item
	 * @param frameCategory the frame category
	 * @param lstFrameNo the lst frame no
	 * @return the list
	 */
	List<AttendanceItemLinking> findByFrameNoTypeAndFramCategoryAndBreakdownItemNo(List<BigDecimal> frameNos
			, int typeOfItem
			, List<Integer> frameCategory
			, List<Integer> breakdownItemNo);

}
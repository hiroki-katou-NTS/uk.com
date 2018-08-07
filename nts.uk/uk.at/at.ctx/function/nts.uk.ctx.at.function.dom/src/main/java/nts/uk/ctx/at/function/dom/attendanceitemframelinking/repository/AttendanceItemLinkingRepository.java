/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.attendanceitemframelinking.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendanceitemframelinking.AttendanceItemLinking;
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
	
	List<AttendanceItemLinking> getByAttendanceIdAndType(List<Integer> attendanceItemIds, TypeOfItem type);
	/**
	 * @author tutk
	 * Get Full Data AttendanceItemLinking  by type and list attendanceItemIds
	 * @param attendanceItemIds
	 * @return
	 */
	List<AttendanceItemLinking> getFullDataByAttdIdAndType(List<Integer> attendanceItemIds, TypeOfItem type);
}

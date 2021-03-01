package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.service;

import java.util.List;
/**
 * get name attendance item
 * @author dudt
 *
 */
public interface AttendanceNamePriniumAdapter {
	List<AttendanceNamePriniumDto> getDailyAttendanceItemName(List<Integer> dailyAttendanceItemIds);
}

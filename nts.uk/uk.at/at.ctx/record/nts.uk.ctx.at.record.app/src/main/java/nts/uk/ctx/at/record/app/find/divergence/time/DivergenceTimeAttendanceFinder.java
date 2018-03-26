package nts.uk.ctx.at.record.app.find.divergence.time;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.attendance.AttendanceItemRepository;

/**
 * The Class DivergenceTimeAttendanceFinder.
 */
@Stateless
public class DivergenceTimeAttendanceFinder {

	/** The attendance item repository. */
	@Inject
	AttendanceItemRepository attendanceItemRepository;

}

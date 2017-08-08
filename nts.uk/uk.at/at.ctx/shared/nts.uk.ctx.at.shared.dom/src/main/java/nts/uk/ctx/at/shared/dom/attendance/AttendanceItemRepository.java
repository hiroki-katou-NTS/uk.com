package nts.uk.ctx.at.shared.dom.attendance;

import java.util.List;

public interface AttendanceItemRepository {
	/**
	 * get possible attendance item
	 * 
	 * @param companyId
	 * @return
	 */
	List<AttendanceItem> getPossibleAttendanceItems(String companyId, List<Integer> lstPossible);
<<<<<<< HEAD
	List<AttendanceItem> getAttendanceItems(String companyId);
=======
	
	List<AttendanceItem> getAttendanceItems(String companyId, int useAtr);
>>>>>>> c791dcba11a8384d6d77b59cca50123261a866bc

}

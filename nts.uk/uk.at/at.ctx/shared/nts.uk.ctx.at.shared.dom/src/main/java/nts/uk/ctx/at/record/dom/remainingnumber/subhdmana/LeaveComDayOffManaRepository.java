package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import java.util.List;

public interface LeaveComDayOffManaRepository {
	
	void add(LeaveComDayOffManagement domain);
	
	void update(LeaveComDayOffManagement domain);
	
	void delete(String leaveID, String comDayOffID);
	
	List<LeaveComDayOffManagement> getByLeaveID(String leaveID);
	
	List<LeaveComDayOffManagement> getBycomDayOffID(String comDayOffID);
	
	void insertAll(List<LeaveComDayOffManagement> entitiesLeave);
	
	void deleteByLeaveId(String leaveId);
	
	void deleteByComDayOffID(String comDayOffID);

	List<LeaveComDayOffManagement> getByListComLeaveID(List<String> listLeaveID);
	/**
	 * Delete 休出代休紐付け管理 by comDayOffId
	 * @param comDayOffId
	 */
	void deleteByComDayOffId(String comDayOffId);
}

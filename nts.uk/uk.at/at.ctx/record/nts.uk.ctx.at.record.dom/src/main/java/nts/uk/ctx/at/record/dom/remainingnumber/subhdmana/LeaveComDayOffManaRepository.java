package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

public interface LeaveComDayOffManaRepository {
	
	void add(LeaveComDayOffManagement domain);
	
	void update(LeaveComDayOffManagement domain);
	
	void delete(String leaveID, String comDayOffID);
}

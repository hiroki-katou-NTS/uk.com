package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface LeaveComDayOffManaRepository {
	
	void add(LeaveComDayOffManagement domain);
	
	void update(LeaveComDayOffManagement domain);
	
	void delete(String sid, GeneralDate occDate, GeneralDate digestDate);
	
	List<LeaveComDayOffManagement> getByLeaveID(String sid, GeneralDate occDate);
	
	List<LeaveComDayOffManagement> getByListDate(String sid, List<GeneralDate> lstDate);
	
	List<LeaveComDayOffManagement> getBycomDayOffID(String sid,  GeneralDate digestDate);
	
	void insertAll(List<LeaveComDayOffManagement> entitiesLeave);
	
	void deleteByLeaveId(String sid, GeneralDate occDate);
	
	void deleteByComDayOffID(String sid,  GeneralDate digestDate);

	List<LeaveComDayOffManagement> getByListComLeaveID(String sid, DatePeriod period);
	List<LeaveComDayOffManagement> getByListComId(String sid,  DatePeriod period);
	/**
	 * Delete 休出代休紐付け管理 by comDayOffId
	 * @param comDayOffId
	 */
	void deleteByComDayOffId(String sid,  GeneralDate digestDate);
	
	/**
	 * ドメイン「休出代休紐付け管理」を取得する
	 * @param sid 社員ID
	 * @param lstOccDate 休出日
	 * @param lstDigestDate 代休日
	 * @return List leave company dayOff management 休出代休紐付け管理
	 */
	List<LeaveComDayOffManagement> getByListOccDigestDate(String sid, List<GeneralDate> lstOccDate, List<GeneralDate> lstDigestDate);
	
	/**
	 * @param sid
	 * @param occDate
	 * @param digestDate
	 */
	void delete(String sid1, String sid2, List<GeneralDate> occDate, List<GeneralDate> digestDate);
}

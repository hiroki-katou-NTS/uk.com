package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;

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
	
	void deleteByDigestTarget(String sid, GeneralDate digestDate, TargetSelectionAtr target);

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
	void delete(String sid1, String sid2, List<GeneralDate> occDate, List<GeneralDate> digestDate);/**
	 * ＜条件＞
	 * ・社員ID＝逐次発生の休暇明細.社員ID
	 * ・使用日＝逐次発生の休暇明細．年月日．年月日
	 * ・発生日 >= INPUT．基準日
	*/
	//LeaveComDayOffManaRepository.getLeaveComWithDateUse
	List<LeaveComDayOffManagement> getLeaveComWithDateUse(String sid, GeneralDate dateOfUse, GeneralDate baseDate);
	
	/**
	 * ＜条件＞
	 * 逐次発生の休暇明細．年月日．日付不明 = false
	 * ・社員ID＝逐次発生の休暇明細.社員ID
	 * ・発生日＝逐次発生の休暇明細．年月日．年月日
	 * ・使用日 >= INPUT．基準日
	*/
	//LeaveComDayOffManaRepository.getLeaveComWithOutbreakDay
	List<LeaveComDayOffManagement> getLeaveComWithOutbreakDay(String sid, GeneralDate outbreakDay, GeneralDate baseDate);
	
	//	[3] 取得する
	List<LeaveComDayOffManagement> getDigestOccByListComId(String sid,  DatePeriod period);
	
	//	[4] 削除する
	 void deleteWithPeriod(String sid,  DatePeriod period);
	 
	 //[5] Insert(List<休出代休紐付け管理>)
	 void insertList(List<LeaveComDayOffManagement> lstDomain);
}

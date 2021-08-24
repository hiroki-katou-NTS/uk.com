package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;

public interface PayoutSubofHDManaRepository {
	
	void add(PayoutSubofHDManagement domain);
	
	void update(PayoutSubofHDManagement domain);
	
	void delete(String sid, GeneralDate occDate, GeneralDate digestDate);
	
	void delete(String sid1, String sid2, List<GeneralDate> occDate, List<GeneralDate> digestDate);
	
	List<PayoutSubofHDManagement> getByPayoutId(String sid, GeneralDate occDate);
	
	List<PayoutSubofHDManagement> getBySubId(String sid, GeneralDate digestDate);
	
	List<PayoutSubofHDManagement> getByListPayoutID(String sid, DatePeriod date);
	
	List<PayoutSubofHDManagement> getByListDate(String sid, List<GeneralDate> lstDate);
	
	List<PayoutSubofHDManagement> getByListOccDate(String sid, List<GeneralDate> lstDate);
	
	List<PayoutSubofHDManagement> getByListSubID(String sid, DatePeriod date);
	
	void delete(String sid, GeneralDate occDate);
	
	void deleteBySubID(String sid, GeneralDate digestDate);
	
	void deleteByDigestTarget(String sid, GeneralDate digestDate, TargetSelectionAtr target);
	
	/**
	 * ＜条件＞
	 * ・社員ID＝逐次発生の休暇明細.社員ID
	 * ・使用日＝逐次発生の休暇明細．年月日．年月日
	 * ・発生日 >= INPUT．基準日
	*/
	//PayoutSubofHDManaRepository.getByPayoutId
	List<PayoutSubofHDManagement> getWithDateUse(String sid, GeneralDate dateOfUse, GeneralDate baseDate);
	
	/**
	 * ＜条件＞
	 * 逐次発生の休暇明細．年月日．日付不明 = false
	 * ・社員ID＝逐次発生の休暇明細.社員ID
	 * ・発生日＝逐次発生の休暇明細．年月日．年月日
	 * ・使用日 >= INPUT．基準日
	*/
	//PayoutSubofHDManaRepository.getBySubId
	List<PayoutSubofHDManagement> getWithOutbreakDay(String sid, GeneralDate outbreakDay, GeneralDate baseDate);
	
    //	[3] 取得する
	List<PayoutSubofHDManagement> getOccDigetByListSid(String sid, DatePeriod date);
	
	//	[4] 削除する
	 void deletePayoutWithPeriod(String sid,  DatePeriod period);
	 
	 //[5] Insert(List<振出振休紐付け管理>)
	 void insertPayoutList(List<PayoutSubofHDManagement> lstDomain);
	 
     //[7] 登録及び更新
	void updateOrInsert(PayoutSubofHDManagement domain);
}

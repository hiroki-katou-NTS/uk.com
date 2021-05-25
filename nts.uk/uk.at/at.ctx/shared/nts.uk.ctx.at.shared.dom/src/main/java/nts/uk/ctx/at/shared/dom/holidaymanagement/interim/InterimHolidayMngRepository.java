package nts.uk.ctx.at.shared.dom.holidaymanagement.interim;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnlb
 *
 */
public interface InterimHolidayMngRepository {

	/**
	 * 検索 By ID
	 * 
	 * @param mngId
	 * @return
	 */
	public List<InterimHolidayMng> getById(String mngId);

	/**
	 * 削除
	 * 
	 * @param mngId
	 */
	public void deleteById(String mngId);
	
	
	/**
	 * 削除
	 * 
	 * @param mngId
	 */
	public void add(InterimHolidayMng domain);

	public void deleteBySidAndYmd(String sid, GeneralDate ymd);

	public void persistAndUpdate(InterimHolidayMng holidayMng);

}

package nts.uk.ctx.at.shared.dom.remainmana.specialholidaymng.interim;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface InterimSpecialHolidayMngExport {
	/**
	 * 特別休暇暫定データを取得する
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @param mode true: INPUT．モードが月次モード, false: INPUT．モードがその他モード 
	 * @return
	 */
	List<InterimSpecialHolidayMng> getDataByMode(String cid, String sid, DatePeriod dateData, boolean mode);

}

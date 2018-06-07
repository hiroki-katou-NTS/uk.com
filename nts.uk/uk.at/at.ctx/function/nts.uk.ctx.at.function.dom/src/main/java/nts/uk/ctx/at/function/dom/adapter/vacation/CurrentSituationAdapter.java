package nts.uk.ctx.at.function.dom.adapter.vacation;


import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface CurrentSituationAdapter {
	
	
	/**
	 * RequetList341 期間内の子看護残を集計する		
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @param mode True: 月次モード, false: その他モード
	 * @return
	 */
	public CurrentSituationImported   calChildNursOfInPeriod(String cid, String sid, DatePeriod dateData,boolean mode);
	
}

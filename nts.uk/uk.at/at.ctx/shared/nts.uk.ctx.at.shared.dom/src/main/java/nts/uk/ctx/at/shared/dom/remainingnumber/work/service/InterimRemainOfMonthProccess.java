package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface InterimRemainOfMonthProccess {
	/**
	 * 月次処理用の暫定残数管理データを作成する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param dateData 期間
	 * @return
	 */
	Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainDataMng(String cid, String sid, DatePeriod dateData);
	
}

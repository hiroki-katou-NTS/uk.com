package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.AppRemainCreateInfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RemainCreateInforByApplicationData {
	/**
	 * 残数作成元の申請を取得する
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @return
	 */
	List<AppRemainCreateInfor> lstRemainDataFromApp(String cid, String sid, DatePeriod dateData);

}

package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 暫定データの登録(変更された日のみ)
 * @author do_dt
 *
 */
public interface InterimRemainDataMngRegisterDateChange {
	/**
	 * 暫定データの登録
	 * @param cid
	 * @param sid
	 * @param lstDate
	 */
	void registerDateChange(String cid, String sid, List<GeneralDate> lstDate);
}

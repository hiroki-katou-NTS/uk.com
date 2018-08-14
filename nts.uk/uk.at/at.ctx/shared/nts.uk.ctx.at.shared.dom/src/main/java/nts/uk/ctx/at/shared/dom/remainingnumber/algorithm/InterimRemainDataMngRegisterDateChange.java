package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 変更された日のみの暫定データの登録
 * @author do_dt
 *
 */
public interface InterimRemainDataMngRegisterDateChange {
	/**
	 * 変更された日のみの暫定データの登録
	 * @param cid
	 * @param sid
	 * @param lstDate
	 */
	void registerDateChange(String cid, String sid, List<GeneralDate> lstDate);
}

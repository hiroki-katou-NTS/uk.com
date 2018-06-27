package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Map;

import nts.arc.time.GeneralDate;

/**
 * 指定期間の暫定残数管理データを作成する
 * @author do_dt
 *
 */
public interface InterimRemainOffPeriodCreateData {
	/**
	 * 指定期間の暫定残数管理データを作成する
	 * @param inputParam
	 * @return
	 */
	public Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainDataMng(InterimRemainCreateDataInputPara inputParam);
	/**
	 * 対象日のデータを抽出する
	 * @param baseDate
	 * @param inputInfor
	 * @return
	 */
	public InterimRemainCreateInfor extractDataOfDate(GeneralDate baseDate, InterimRemainCreateDataInputPara inputInfor);
	
	
}

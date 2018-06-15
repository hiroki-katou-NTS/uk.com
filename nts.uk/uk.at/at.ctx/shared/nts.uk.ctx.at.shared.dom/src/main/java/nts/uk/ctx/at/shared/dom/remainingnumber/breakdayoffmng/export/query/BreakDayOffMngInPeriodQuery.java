package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

public interface BreakDayOffMngInPeriodQuery {
	/**
	 * RequestList203: 期間内の休出代休残数を取得する
	 * @param inputParam
	 * @return
	 */
	BreakDayOffRemainMngOfInPeriod getBreakDayOffMngInPeriod(BreakDayOffRemainMngParam inputParam);
	/**
	 * 1.未相殺の代休(確定)を取得する
	 * @param sid
	 * @return
	 */
	List<BreakDayOffDetail> getConfirmDayOffDetail(String sid);
	/**
	 * 1-1.確定代休から未相殺の代休を取得する
	 * @param sid
	 * @return
	 */
	List<CompensatoryDayOffManaData> lstConfirmDayOffData(String sid);
	/**
	 * 1-3.暫定休出と紐付けをしない確定代休を取得する
	 * @param dayoffConfirmData
	 * @return
	 */
	BreakDayOffDetail getConfirmDayOffData(CompensatoryDayOffManaData dayoffConfirmData, String sid);
	/**
	 * 2.未使用の休出(確定)を取得する
	 * @param sid
	 * @return
	 */
	List<BreakDayOffDetail> getConfirmBreakDetail(String sid);
	/**
	 * 2-1.確定休出から未使用の休出を取得する
	 * @param sid
	 * @return
	 */
	List<LeaveManagementData> lstConfirmBreakData(String sid);
	/**
	 * 2-3.暫定代休と紐付けをしない確定休出を取得する
	 * @param breakConfirm
	 * @param sid
	 * @return
	 */
	BreakDayOffDetail getConfirmBreakData(LeaveManagementData breakConfirm, String sid);
	/**
	 * 繰越数を計算する
	 * @param baseDate
	 * @param lstDetailData
	 * @return
	 */
	double calcCarryForwardDays(GeneralDate baseDate, List<BreakDayOffDetail> lstDetailData);
	/**
	 * 6.残数と未消化数を集計する
	 * @param baseDate
	 * @param lstDetailData
	 * @return
	 */
	RemainUnDigestedDayTimes getRemainUnDigestedDayTimes(GeneralDate baseDate, List<BreakDayOffDetail> lstDetailData);
	/**
	 * 3.未相殺の代休(暫定)を取得する
	 * @param inputParam
	 * @return
	 */
	List<BreakDayOffDetail> lstInterimDayOffDetail(BreakDayOffRemainMngParam inputParam);
	/**
	 * 3-1.休出と紐付けをしない代休を取得する
	 * @param detailData
	 * @param remainData
	 * @return
	 */
	BreakDayOffDetail getNotTypeBreak(InterimDayOffMng detailData, InterimRemain remainData);
}

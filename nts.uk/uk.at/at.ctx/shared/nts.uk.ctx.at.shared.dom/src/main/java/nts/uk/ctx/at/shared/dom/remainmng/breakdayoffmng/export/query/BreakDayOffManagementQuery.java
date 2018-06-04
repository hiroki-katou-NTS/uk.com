package nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.export.query;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.export.query.AsbRemainTotalInfor;
import nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.休出代休管理.アルゴリズム.Query
 * @author du_dt
 *
 */
public interface BreakDayOffManagementQuery {
	/**
	 * RequestList269
	 * 月度別代休残数集計を取得
	 * @param employeeId
	 * @param baseDate
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	public List<InterimRemainAggregateOutputData> getInterimRemainAggregate(String employeeId, GeneralDate baseDate, YearMonth startMonth, YearMonth endMonth);
	/**
	 * 期間内の代休発生数合計を取得
	 * @param employeeId
	 * @param dateData
	 * @return
	 */
	public Double getTotalOccurrenceDays(String employeeId, DatePeriod dateData);
	/**
	 * 期間内の代休使用数合計を取得
	 * @param employeeId
	 * @param dateData
	 * @return
	 */
	public Double getTotalUseDays(String employeeId, DatePeriod dateData);
	/**
	 * 当月の代休残数を集計する
	 * @param employeeId
	 * @return
	 */
	public InterimRemainAggregateOutputData aggregatedDayoffCurrentMonth(String employeeId);
	/**
	 * 休出代休発生消化履歴の取得
	 */
	public BreakDayOffOutputHisData getBreakDayOffData(String cid, String sid, GeneralDate baseDate);
	/**
	 * 暫定管理データを取得する
	 * @param sid
	 * @param baseDate
	 * @return
	 */
	public BreakDayOffInterimMngData getMngData(String sid, GeneralDate baseDate);
	/**
	 * 指定期間内に発生した暫定休出と紐付いた確定代休・暫定代休を取得する
	 * @param sid
	 * @param dateData
	 * @return
	 */
	public BreakDayOffInterimMngData getMngDataToInterimData(String sid, DatePeriod dateData);
	/**
	 * 未消化の確定休出に紐付いた暫定代休を取得する
	 * @param sid
	 * @param dateData
	 * @param mngData
	 * @return
	 */
	public BreakDayOffInterimMngData getNotInterimDayOffMng(String sid, DatePeriod dateData, BreakDayOffInterimMngData mngData);
	/**
	 * 休出履歴を作成する
	 * @param lstInterimBreakMng: 暫定休出管理データ
	 * @return
	 */
	public List<BreakHistoryData> breakHisData(List<InterimBreakMng> lstInterimBreakMng);
	/**
	 * 代休履歴を作成する
	 * @param lstInterimDayOffMng: 暫定代休管理
	 * @return
	 */
	public List<DayOffHistoryData> dayOffHisData(List<InterimDayOffMng> lstInterimDayOffMng);
	/**
	 * 休出代休履歴対照情報を作成する
	 * @param lstInterimBreakDayOff 暫定休出代休紐付け管理
	 * @return
	 */
	public List<BreakDayOffHistory> lstBreakDayOffHis(List<InterimBreakDayOffMng> lstInterimBreakDayOff, List<BreakHistoryData> lstBreakHis, List<DayOffHistoryData> lstDayOffHis);
	/**
	 * 残数集計情報を作成する
	 * @param lstBreakHis
	 * @param lstDayOffHis
	 * @return
	 */
	public AsbRemainTotalInfor totalInfor(List<BreakHistoryData> lstBreakHis, List<DayOffHistoryData> lstDayOffHis);
}

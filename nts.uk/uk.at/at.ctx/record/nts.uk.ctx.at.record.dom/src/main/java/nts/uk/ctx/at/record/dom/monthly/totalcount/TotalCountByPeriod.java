package nts.uk.ctx.at.record.dom.monthly.totalcount;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 期間別の回数集計
 * @author shuichu_ishida
 */
@Getter
public class TotalCountByPeriod implements Cloneable {

	/** 回数集計 */
	private List<TotalCount> totalCountList;
	
	/**
	 * コンストラクタ
	 */
	public TotalCountByPeriod(){
		
		this.totalCountList = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param totalCountList 回数集計
	 * @return 期間別の回数集計
	 */
	public static TotalCountByPeriod of(
			List<TotalCount> totalCountList){
		
		TotalCountByPeriod domain = new TotalCountByPeriod();
		domain.totalCountList = totalCountList;
		return domain;
	}
	
	@Override
	public TotalCountByPeriod clone() {
		TotalCountByPeriod cloned = new TotalCountByPeriod();
		try {
			for (val totalCount : this.totalCountList) cloned.totalCountList.add(totalCount.clone());
		}
		catch (Exception e){
			throw new RuntimeException("TotalCountbyPeriod clone error.");
		}
		return cloned;
	}
	
	/**
	 * 回数集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void totalize(
			String companyId,
			String employeeId,
			DatePeriod period,
			RepositoriesRequiredByMonthlyAggr repositories){

		// 日別実績から回数集計結果を取得する準備をする
		val timeAndCount = repositories.getTimeAndCountFromDailyRecord().setData(companyId, employeeId, period);
		
		// 回数集計マスタを取得
		val totalTimesList = repositories.getTotalTimes().getAllTotalTimes(companyId);
		for (val totalTimes : totalTimesList){
			val totalCountNo = totalTimes.getTotalCountNo();
			
			// 集計処理
			val result = timeAndCount.getResult(totalTimes);
			
			// 回数集計を返す　（集計結果を追加する）
			this.totalCountList.add(TotalCount.of(
					totalCountNo,
					new AttendanceDaysMonth(result.getCount().v()),
					new AttendanceTimeMonth(result.getTime().v())));
		}
	}
}

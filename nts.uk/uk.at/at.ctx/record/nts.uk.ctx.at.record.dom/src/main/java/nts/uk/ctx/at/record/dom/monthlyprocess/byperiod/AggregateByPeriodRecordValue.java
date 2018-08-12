package nts.uk.ctx.at.record.dom.monthlyprocess.byperiod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;

/**
 * 戻り値：任意期間集計Mgr．アルゴリズム
 * @author shuichu_ishida
 */
@Getter
public class AggregateByPeriodRecordValue {

	/** 任意期間別実績の勤怠時間 */
	@Setter
	private Optional<AttendanceTimeOfAnyPeriod> attendanceTime;
	
	/** エラー情報 */
	private Map<String, MonthlyAggregationErrorInfo> errorInfos;
	/** 社員の月別実績エラー一覧 */
	private List<EmployeeMonthlyPerError> perErrors;
	/** 中断フラグ */
	@Setter
	private boolean interruption;
	
	/**
	 * コンストラクタ
	 */
	public AggregateByPeriodRecordValue(){
		this.attendanceTime = Optional.empty();
		
		this.errorInfos = new HashMap<>();
		this.perErrors = new ArrayList<>();
		this.interruption = false;
	}
	
	/**
	 * エラー情報を追加する
	 * @param resourceId リソースID
	 * @param message エラーメッセージ
	 */
	public void addErrorInfos(String resourceId, ErrMessageContent message){
		this.errorInfos.putIfAbsent(resourceId, new MonthlyAggregationErrorInfo(resourceId, message));
	}
	
	/**
	 * エラー情報に指定のリソースIDがあるかどうか
	 * @param resourceId リソースID
	 * @return true：ある、false：ない
	 */
	public boolean existErrorResource(String resourceId){
		return this.errorInfos.containsKey(resourceId);
	}
}

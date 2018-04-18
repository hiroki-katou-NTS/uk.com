package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveManagement;

/**
 * 年休情報
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveInfo {

	/** 年月日 */
	private GeneralDate ymd;
	/** 残数 */
	private AnnualLeaveRemainingNumber remainingNumber;
	/** 付与残数データ */
	private List<AnnualLeaveGrantRemainingData> grantRemainingNumberList;
	/** 上限データ */
	private AnnualLeaveMaxData maxData;
	/** 付与情報 */
	private Optional<AnnualLeaveGrant> grantInfo;
	/** 使用日数 */
	private AnnualLeaveUsedDayNumber usedDays;
	/** 使用時間 */
	private UsedMinutes usedTime;
	/** 付与後フラグ */
	private boolean afterGrantAtr;

	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public AnnualLeaveInfo(){
		
		this.ymd = GeneralDate.min();
		this.remainingNumber = new AnnualLeaveRemainingNumber();
		this.grantRemainingNumberList = new ArrayList<>();
		this.maxData = new AnnualLeaveMaxData();
		this.grantInfo = Optional.empty();
		this.usedDays = new AnnualLeaveUsedDayNumber(0.0);
		this.usedTime = new UsedMinutes(0);
		this.afterGrantAtr = false;
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param remainingNumber 残数
	 * @param grantRemainingNumberList 付与残数データ
	 * @param maxData 上限データ
	 * @param grantInfo 付与情報
	 * @param usedDays 使用日数
	 * @param usedTime 使用時間
	 * @param afterGrantAtr 付与後フラグ
	 * @return 年休情報
	 */
	public static AnnualLeaveInfo of(
			GeneralDate ymd,
			AnnualLeaveRemainingNumber remainingNumber,
			List<AnnualLeaveGrantRemainingData> grantRemainingNumberList,
			AnnualLeaveMaxData maxData,
			Optional<AnnualLeaveGrant> grantInfo,
			AnnualLeaveUsedDayNumber usedDays,
			UsedMinutes usedTime,
			boolean afterGrantAtr){
	
		AnnualLeaveInfo domain = new AnnualLeaveInfo();
		domain.ymd = ymd;
		domain.remainingNumber = remainingNumber;
		domain.grantRemainingNumberList = grantRemainingNumberList;
		domain.maxData = maxData;
		domain.grantInfo = grantInfo;
		domain.usedDays = usedDays;
		domain.usedTime = usedTime;
		domain.afterGrantAtr = afterGrantAtr;
		return domain;
	}
	
	/**
	 * 年休付与残数を更新
	 */
	public void updateRemainingNumber(){
		this.remainingNumber.updateRemainingNumber(this.grantRemainingNumberList, this.afterGrantAtr);
	}
	
	/**
	 * 年休の消滅・付与・消化
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggregatePeriodWork 処理中の年休集計期間WORK
	 * @param tempAnnualLeaveMngs 暫定年休管理データリスト
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param aggrResult 年休の集計結果
	 * @return 年休の集計結果
	 */
	public AggrResultOfAnnualLeave lapsedGrantDigest(
			String companyId,
			String employeeId,
			AggregatePeriodWork aggregatePeriodWork,
			List<TempAnnualLeaveManagement> tempAnnualLeaveMngs,
			boolean isGetNextMonthData,
			boolean isCalcAttendanceRate,
			AggrResultOfAnnualLeave aggrResult){
		
		// 期間終了日時点の年休情報を付与消滅前に退避するかチェック
		if (aggregatePeriodWork.isNextDayAfterPeriodEnd() && !isGetNextMonthData){
			
			// 「年休の集計結果．年休情報（期間終了日時点）」　←　処理中の「年休情報」
			aggrResult.setAsOfPeriodEnd(this);
		}
		
		// 付与前退避処理
		this.saveStateBeforeGrant(aggregatePeriodWork);
		
		// 年月日を更新
		this.ymd = aggregatePeriodWork.getPeriod().end();

		// 消滅処理
		aggrResult = this.lapsedProcess(aggregatePeriodWork, aggrResult);
		
		// 付与処理
		aggrResult = this.grantProcess(aggregatePeriodWork, isCalcAttendanceRate, aggrResult);
		
		// 期間終了日翌日時点の期間かチェック
		if (!aggregatePeriodWork.isNextDayAfterPeriodEnd()){
			
			// 消化処理
			aggrResult = this.digestProcess(aggregatePeriodWork, tempAnnualLeaveMngs, aggrResult);
			
			// 「年休の集計結果」を返す
			return aggrResult;
		}

		// 期間終了日時点の年休情報を消化後に退避するかチェック
		if (isGetNextMonthData){
			
			// 「年休の集計結果．年休情報（期間終了日時点）」　←　処理中の「年休情報」
			aggrResult.setAsOfPeriodEnd(this);
		}
		
		// 「年休の集計結果．年休情報（期間終了日の翌日開始時点）」　←　処理中の「年休情報」
		aggrResult.setAsOfStartNextDayOfPeriodEnd(this);
		
		// 「年休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 付与前退避処理
	 * @param aggregatePeriodWork 処理中の年休集計期間WORK
	 */
	private void saveStateBeforeGrant(AggregatePeriodWork aggregatePeriodWork){
		
		// 「年休集計期間WORK.付与フラグ」をチェック
		if (!aggregatePeriodWork.isGrantAtr()) return;
		
		// 「年休情報．付与後フラグ」をチェック
		if (this.isAfterGrantAtr()) return;
		
		// 現在の年休（マイナスあり）の残数を付与前として退避する
		val withMinus = this.remainingNumber.getAnnualLeaveWithMinus();
		withMinus.setRemainingNumberBeforeGrant(withMinus.getRemainingNumber());

		// 現在の年休（マイナスなし）の残数を付与前として退避する
		val noMinus = this.remainingNumber.getAnnualLeaveNoMinus();
		noMinus.setRemainingNumberBeforeGrant(noMinus.getRemainingNumber());
	}
	
	/**
	 * 消滅処理
	 * @param aggregatePeriodWork 処理中の年休集計期間WORK
	 * @param aggrResult 年休の集計結果
	 * @return 年休の集計結果
	 */
	private AggrResultOfAnnualLeave lapsedProcess(
			AggregatePeriodWork aggregatePeriodWork,
			AggrResultOfAnnualLeave aggrResult){
		
		// 「年休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 付与処理
	 * @param aggregatePeriodWork 処理中の年休集計期間WORK
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param aggrResult 年休の集計結果
	 * @return 年休の集計結果
	 */
	private AggrResultOfAnnualLeave grantProcess(
			AggregatePeriodWork aggregatePeriodWork,
			boolean isCalcAttendanceRate,
			AggrResultOfAnnualLeave aggrResult){
		
		// 「年休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 消化処理
	 * @param aggregatePeriodWork 処理中の年休集計期間WORK
	 * @param tempAnnualLeaveMngs 暫定年休管理データリスト
	 * @param aggrResult 年休の集計結果
	 * @return 年休の集計結果
	 */
	private AggrResultOfAnnualLeave digestProcess(
			AggregatePeriodWork aggregatePeriodWork,
			List<TempAnnualLeaveManagement> tempAnnualLeaveMngs,
			AggrResultOfAnnualLeave aggrResult){
		
		// 「年休の集計結果」を返す
		return aggrResult;
	}
}

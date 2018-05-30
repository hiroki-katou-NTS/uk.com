package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.UndigestedTimeAnnualLeaveTime;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;

/**
 * 年休情報
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveInfo implements Cloneable {

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
	
	/** 年休設定 */
	private AnnualPaidLeaveSetting annualPaidLeaveSet;

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
		
		this.annualPaidLeaveSet = null;
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
	
	@Override
	public AnnualLeaveInfo clone() {
		AnnualLeaveInfo cloned = new AnnualLeaveInfo();
		try {
			cloned.ymd = this.ymd;
			cloned.remainingNumber = this.remainingNumber.clone();
			for (val grantRemainingNumber : this.grantRemainingNumberList){
				val detail = grantRemainingNumber.getDetails();
				Integer grantMinutes = null;
				if (detail.getGrantNumber().getMinutes().isPresent()){
					grantMinutes = detail.getGrantNumber().getMinutes().get().v();
				}
				Integer usedMinutes = null;
				if (detail.getUsedNumber().getMinutes().isPresent()){
					usedMinutes = detail.getUsedNumber().getMinutes().get().v();
				}
				Double stowageDays = null;
				if (detail.getUsedNumber().getStowageDays().isPresent()){
					stowageDays = detail.getUsedNumber().getStowageDays().get().v();
				}
				Integer remainMinutes = null;
				if (detail.getRemainingNumber().getMinutes().isPresent()){
					remainMinutes = detail.getRemainingNumber().getMinutes().get().v();
				}
				Double prescribedDays = null;
				Double deductedDays = null;
				Double workingDays = null;
				if (grantRemainingNumber.getAnnualLeaveConditionInfo().isPresent()){
					val annualLeaveCond = grantRemainingNumber.getAnnualLeaveConditionInfo().get();
					prescribedDays = annualLeaveCond.getPrescribedDays().v();
					deductedDays = annualLeaveCond.getDeductedDays().v();
					workingDays = annualLeaveCond.getWorkingDays().v();
				}
				cloned.grantRemainingNumberList.add(AnnualLeaveGrantRemainingData.createFromJavaType(
						grantRemainingNumber.getAnnLeavID(),
						grantRemainingNumber.getCid(),
						grantRemainingNumber.getEmployeeId(),
						grantRemainingNumber.getGrantDate(),
						grantRemainingNumber.getDeadline(),
						grantRemainingNumber.getExpirationStatus().value,
						grantRemainingNumber.getRegisterType().value,
						detail.getGrantNumber().getDays().v(),
						grantMinutes,
						detail.getUsedNumber().getDays().v(),
						usedMinutes,
						stowageDays,
						detail.getRemainingNumber().getDays().v(),
						remainMinutes,
						0.0,
						prescribedDays,
						deductedDays,
						workingDays));
			}
			if (this.grantInfo.isPresent()){
				cloned.grantInfo = Optional.of(this.grantInfo.get().clone());
			}
			cloned.usedDays = new AnnualLeaveUsedDayNumber(this.usedDays.v());
			cloned.usedTime = new UsedMinutes(this.usedTime.v());
			cloned.afterGrantAtr = this.afterGrantAtr;
			
			// 以下は、cloneしなくてよい。
			cloned.maxData = this.maxData;
			cloned.annualPaidLeaveSet = this.annualPaidLeaveSet;
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveInfo clone error.");
		}
		return cloned;
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
	 * @param annualPaidLeaveSet 年休設定
	 * @param grantRemainingDatas 年休付与残数データリスト
	 * @return 年休の集計結果
	 */
	public AggrResultOfAnnualLeave lapsedGrantDigest(
			String companyId,
			String employeeId,
			AggregatePeriodWork aggregatePeriodWork,
			List<TempAnnualLeaveManagement> tempAnnualLeaveMngs,
			boolean isGetNextMonthData,
			boolean isCalcAttendanceRate,
			AggrResultOfAnnualLeave aggrResult,
			AnnualPaidLeaveSetting annualPaidLeaveSet){
		
		this.annualPaidLeaveSet = annualPaidLeaveSet;
		
		// 期間終了日時点の年休情報を付与消滅前に退避するかチェック
		if (aggregatePeriodWork.isNextDayAfterPeriodEnd() && !isGetNextMonthData){
			
			// 「年休の集計結果．年休情報（期間終了日時点）」　←　処理中の「年休情報」
			aggrResult.setAsOfPeriodEnd(this.clone());
		}
		
		// 付与前退避処理
		this.saveStateBeforeGrant(aggregatePeriodWork);
		
		// 年月日を更新
		this.ymd = aggregatePeriodWork.getPeriod().end();

		// 消滅処理
		aggrResult = this.lapsedProcess(aggregatePeriodWork, aggrResult);
		
		// 付与処理
		aggrResult = this.grantProcess(companyId, employeeId,
				aggregatePeriodWork, isCalcAttendanceRate, aggrResult);
		
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
			aggrResult.setAsOfPeriodEnd(this.clone());
		}
		
		// 「年休の集計結果．年休情報（期間終了日の翌日開始時点）」　←　処理中の「年休情報」
		aggrResult.setAsOfStartNextDayOfPeriodEnd(this.clone());
		
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
		withMinus.setRemainingNumberBeforeGrant(withMinus.getRemainingNumber().clone());

		// 現在の年休（マイナスなし）の残数を付与前として退避する
		val noMinus = this.remainingNumber.getAnnualLeaveNoMinus();
		noMinus.setRemainingNumberBeforeGrant(noMinus.getRemainingNumber().clone());
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
		
		// 消滅フラグを取得
		if (!aggregatePeriodWork.isLapsedAtr()) return aggrResult;
		
		// 「付与残数データ」を取得
		val itrGrantRemainingNumber = this.grantRemainingNumberList.listIterator();
		while (itrGrantRemainingNumber.hasNext()){
			val grantRemainingNumber = itrGrantRemainingNumber.next();
			
			// 処理中の付与残数データを期限切れにする
			grantRemainingNumber.setExpirationStatus(LeaveExpirationStatus.EXPIRED);
			
			// 未消化数を更新
			val targetUndigestNumber = this.remainingNumber.getAnnualLeaveNoMinus().getUndigestedNumber();
			val remainingNumber = grantRemainingNumber.getDetails().getRemainingNumber();
			targetUndigestNumber.getUndigestedDays().addDays(remainingNumber.getDays().v());
			if (remainingNumber.getMinutes().isPresent()){
				if (!targetUndigestNumber.getUndigestedTime().isPresent()){
					targetUndigestNumber.setUndigestedTime(Optional.of(new UndigestedTimeAnnualLeaveTime()));
				}
				targetUndigestNumber.getUndigestedTime().get().addMinutes(remainingNumber.getMinutes().get().v());
			}
		}
		
		// 年休情報残数を更新
		this.updateRemainingNumber();
		
		// 年休情報を「年休の集計結果．年休情報（消滅）」に追加
		if (!aggrResult.getLapsed().isPresent()) aggrResult.setLapsed(Optional.of(new ArrayList<>()));
		aggrResult.getLapsed().get().add(this.clone());
		
		// 「年休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 付与処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggregatePeriodWork 処理中の年休集計期間WORK
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param aggrResult 年休の集計結果
	 * @return 年休の集計結果
	 */
	private AggrResultOfAnnualLeave grantProcess(
			String companyId,
			String employeeId,
			AggregatePeriodWork aggregatePeriodWork,
			boolean isCalcAttendanceRate,
			AggrResultOfAnnualLeave aggrResult){
		
		// 「付与フラグ」をチェック
		if (!aggregatePeriodWork.isGrantAtr()) return aggrResult;
		
		// 付与日から期限日を計算
		if (!aggregatePeriodWork.getAnnualLeaveGrant().isPresent()) return aggrResult;
		val annualLeaveGrant = aggregatePeriodWork.getAnnualLeaveGrant().get();
		val grantDate = annualLeaveGrant.getGrantDate();
		val retentionYears = this.annualPaidLeaveSet.getManageAnnualSetting().getRemainingNumberSetting().retentionYear.v();
		val deadline = grantDate.addYears(retentionYears).addDays(-1);
		
		// 付与日数を計算する
		double grantDays = 0.0;
		
		// 「年休付与残数データ」を作成する
		val newRemainData = AnnualLeaveGrantRemainingData.createFromJavaType(
				"",
				companyId, employeeId, grantDate, deadline,
				LeaveExpirationStatus.AVAILABLE.value, GrantRemainRegisterType.MONTH_CLOSE.value,
				grantDays, null,
				0.0, null, null,
				0.0, null,
				0.0,
				null, null, null);
		
		// 作成した「年休付与残数データ」を付与残数データリストに追加
		this.grantRemainingNumberList.add(newRemainData);

		// 上限データを作成
		
		// 付与後フラグ　←　true
		this.afterGrantAtr = true;
		
		// 年休情報残数を更新
		this.updateRemainingNumber();
		
		// 「年休情報（付与時点）」に「年休情報」を追加
		if (!aggrResult.getAsOfGrant().isPresent()) aggrResult.setAsOfGrant(Optional.of(new ArrayList<>()));
		aggrResult.getAsOfGrant().get().add(this.clone());
		
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
		
		// 「暫定年休管理データリスト」を取得する
		for (val tempAnnualLeaveMng : tempAnnualLeaveMngs){
			// 年休を消化する
			
			// 年休使用数WORK
			ManagementDays useDaysWork = tempAnnualLeaveMng.getAnnualLeaveUse();
			// 年休使用残数WORK
			ManagementDays remainDaysWork = tempAnnualLeaveMng.getAnnualLeaveUse();
			
			// 年休付与残数を取得
			List<AnnualLeaveGrantRemainingData> targetRemainingDatas = new ArrayList<>();
			for (val remainingData : this.grantRemainingNumberList){
				if (tempAnnualLeaveMng.getYmd().before(remainingData.getGrantDate())) continue;
				if (tempAnnualLeaveMng.getYmd().after(remainingData.getDeadline())) continue;
				targetRemainingDatas.add(remainingData);
			}
			
			// 取得設定をチェック
			if (this.annualPaidLeaveSet.getAcquisitionSetting().annualPriority == AnnualPriority.FIFO){

				// 当年付与分から消化する　（付与日　降順(DESC)）
				targetRemainingDatas.sort((a, b) -> -a.getGrantDate().compareTo(b.getGrantDate()));
			}
			else {
				
				// 繰越分から消化する　（付与日　昇順(ASC)）
				targetRemainingDatas.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
			}
			
			for (val targetRemainingData : targetRemainingDatas){
				
				// 年休を指定日数消化する
				remainDaysWork = new ManagementDays(targetRemainingData.digest(remainDaysWork.v(), false));
			}
			
			// 消化しきれなかった分を強制的に消化
			if (targetRemainingDatas.size() > 0){
				val targetRemainingData = targetRemainingDatas.get(targetRemainingDatas.size() - 1);
				remainDaysWork = new ManagementDays(targetRemainingData.digest(remainDaysWork.v(), true));
			}
			
			// 実年休（年休（マイナスあり））に使用数を加算する
			this.remainingNumber.getAnnualLeaveWithMinus().addUsedNumber(
					useDaysWork.v(), aggregatePeriodWork.isAfterGrant());
			
			// 年休情報残数を更新
			this.updateRemainingNumber();
		}
		
		// 「年休の集計結果」を返す
		return aggrResult;
	}
}

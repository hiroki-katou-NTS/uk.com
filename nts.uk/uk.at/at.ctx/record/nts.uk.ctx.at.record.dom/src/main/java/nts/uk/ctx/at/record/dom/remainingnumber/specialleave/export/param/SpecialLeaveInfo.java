package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodProc;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RemNumShiftListWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;

/**
 * 特別休暇情報
 * @author shuichi_ishida
 */
@Getter
@Setter
public class SpecialLeaveInfo implements Cloneable {

	/** 年月日 */
	private GeneralDate ymd;
	/** 残数 */
	private SpecialLeaveRemainingNumber remainingNumber;
	/** 付与残数データ */
	private List<SpecialLeaveGrantRemaining> grantRemainingList;
	
	/**
	 * コンストラクタ
	 */
	public SpecialLeaveInfo(){
		
		this.ymd = GeneralDate.min();
		this.remainingNumber = new SpecialLeaveRemainingNumber();
		this.grantRemainingList = new ArrayList<>();
		
		//this.annualPaidLeaveSet = null;
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param remainingNumber 残数
	 * @param grantRemainingNumberList 付与残数データ
	 * @return 特休情報
	 */
	public static SpecialLeaveInfo of(
			GeneralDate ymd,
			SpecialLeaveRemainingNumber remainingNumber,
			List<SpecialLeaveGrantRemaining> grantRemainingNumberList
			){
	
		SpecialLeaveInfo domain = new SpecialLeaveInfo();
		domain.ymd = ymd;
		domain.remainingNumber = remainingNumber;
		domain.grantRemainingList = grantRemainingNumberList;
		return domain;
	}
	
	@Override
	public SpecialLeaveInfo clone() {
		SpecialLeaveInfo cloned = new SpecialLeaveInfo();
		try {
			cloned.ymd = this.ymd;
			cloned.remainingNumber = this.remainingNumber.clone();
			for (val grantRemainingNumber : this.grantRemainingList){
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
				SpecialLeaveGrantRemaining newRemainData = new SpecialLeaveGrantRemaining(
						AnnualLeaveGrantRemainingData.createFromJavaType(
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
				newRemainData.setDummyAtr(grantRemainingNumber.isDummyAtr());
				cloned.grantRemainingList.add(newRemainData);
			}
			if (this.grantInfo.isPresent()){
				cloned.grantInfo = Optional.of(this.grantInfo.get().clone());
			}
			cloned.usedDays = new AnnualLeaveUsedDayNumber(this.usedDays.v());
			cloned.usedTime = new UsedMinutes(this.usedTime.v());
//			cloned.afterGrantAtr = this.afterGrantAtr;
			
			// 以下は、cloneしなくてよい。
			cloned.maxData = this.maxData;
			cloned.annualPaidLeaveSet = this.annualPaidLeaveSet;
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveInfo clone error.");
		}
		return cloned;
	}

	public List<AnnualLeaveGrantRemainingData> getGrantRemainingNumberList(){
		return this.grantRemainingList.stream().map(c -> (AnnualLeaveGrantRemainingData)c)
				.collect(Collectors.toList());
	}
	
	/**
	 * 特休付与残数を更新
	 */
	public void updateRemainingNumber(boolean afterGrant){
		this.remainingNumber.updateRemainingNumber(this.grantRemainingList, afterGrant);
	}
	
	/**
	 * 特休の消滅・付与・消化
	 * @param require
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggregatePeriodWork 処理中の特休集計期間WORK
	 * @param tempAnnualLeaveMngs 暫定特休管理データリスト
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param aggrResult 特休の集計結果
	 * @param annualPaidLeaveSet 特休設定
	 * @return 特休の集計結果
	 */
	public AggrResultOfAnnualLeave lapsedGrantDigest(
			LeaveRemainingNumber.RequireM3 require,
			String companyId,
			String employeeId,
			AggregatePeriodWork aggregatePeriodWork,
			List<TmpAnnualLeaveMngWork> tempAnnualLeaveMngs,
			boolean isGetNextMonthData,
			boolean isCalcAttendanceRate,
			AggrResultOfAnnualLeave aggrResult,
			AnnualPaidLeaveSetting annualPaidLeaveSet){
		
		this.annualPaidLeaveSet = annualPaidLeaveSet;
		
		// 期間終了日時点の特休情報を付与消滅前に退避するかチェック
		// if (aggregatePeriodWork.isNextDayAfterPeriodEnd() && !isGetNextMonthData){
		
		// 初回付与かチェックする
		if ( aggregatePeriodWork.getGrantNumber() == 1 ){ 
			
			// 「特休の集計結果．特休情報（期間終了日時点）」　←　処理中の「特休情報」
			aggrResult.setAsOfPeriodEnd(this.clone());
			
			// 付与前退避処理
			this.saveStateBeforeGrant(aggregatePeriodWork);
		}
		
		// 年月日を更新　←　開始日
		this.ymd = aggregatePeriodWork.getPeriod().start();

		// 消滅処理
		aggrResult = this.lapsedProcess(aggregatePeriodWork, aggrResult);
		
		// 付与処理
		aggrResult = this.grantProcess(companyId, employeeId,
				aggregatePeriodWork, isCalcAttendanceRate, aggrResult);
		
		// 期間終了日翌日時点の期間かチェック
		if (!aggregatePeriodWork.isNextDayAfterPeriodEnd()){
			
			// 消化処理
			aggrResult = this.digestProcess(
					require, companyId, employeeId,
					aggregatePeriodWork, tempAnnualLeaveMngs, aggrResult);
			
			// 年月日を更新　←　終了日
			this.ymd = aggregatePeriodWork.getPeriod().end();
			
			// 「特休の集計結果」を返す
			return aggrResult;
		}

		// 期間終了日時点の特休情報を消化後に退避するかチェック
		if (isGetNextMonthData){
			
			// 「特休の集計結果．特休情報（期間終了日時点）」　←　処理中の「特休情報」
			aggrResult.setAsOfPeriodEnd(this.clone());
		}
		
		// 「特休の集計結果．特休情報（期間終了日の翌日開始時点）」　←　処理中の「特休情報」
		aggrResult.setAsOfStartNextDayOfPeriodEnd(this.clone());
		
		// 「特休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 付与前退避処理
	 * @param aggregatePeriodWork 処理中の特休集計期間WORK
	 */
	private void saveStateBeforeGrant(AggregatePeriodWork aggregatePeriodWork){
		
		// 「特休集計期間WORK.付与フラグ」をチェック
		if (!aggregatePeriodWork.isGrantAtr()) return;
		
//		// 「特休情報．付与後フラグ」をチェック
//		if (this.isAfterGrantAtr()) return;
		
		// 初回付与かチェックする
		if ( aggregatePeriodWork.getGrantNumber() != 1 ){
			return;
		}
	
		// 現在の特休（マイナスあり）の残数を付与前として退避する
		val withMinus = this.remainingNumber.getAnnualLeaveWithMinus();
		withMinus.saveStateBeforeGrant();

		// 現在の特休（マイナスなし）の残数を付与前として退避する
		val noMinus = this.remainingNumber.getAnnualLeaveNoMinus();
		noMinus.saveStateAfterGrant();
	}
	
	/**
	 * 消滅処理
	 * @param aggregatePeriodWork 処理中の特休集計期間WORK
	 * @param aggrResult 特休の集計結果
	 * @return 特休の集計結果
	 */
	private AggrResultOfAnnualLeave lapsedProcess(
			AggregatePeriodWork aggregatePeriodWork,
			AggrResultOfAnnualLeave aggrResult){
		
		// 消滅フラグを取得
		if (!aggregatePeriodWork.isLapsedAtr()) return aggrResult;
		
		// 「付与残数データ」を取得
		val itrGrantRemainingNumber = this.grantRemainingList.listIterator();
		while (itrGrantRemainingNumber.hasNext()){
			val grantRemainingNumber = itrGrantRemainingNumber.next();
			
			// 期限日が特休集計期間WORK.期間.開始日の前日でなければ、消滅処理しない
			if (!grantRemainingNumber.getDeadline().equals(aggregatePeriodWork.getPeriod().start().addDays(-1))){
				continue;
			}
			
			// 特休不足ダミーフラグがtrueなら、消滅処理しない
			if (grantRemainingNumber.isDummyAtr() == true) continue;
			
			// 処理中の付与残数データを期限切れにする
			grantRemainingNumber.setExpirationStatus(LeaveExpirationStatus.EXPIRED);
			
//			// 未消化数を更新
//			val targetUndigestNumber = this.remainingNumber.getAnnualLeaveNoMinus().getUndigestedNumber();
//			val remainingNumber = grantRemainingNumber.getDetails().getRemainingNumber();
//			targetUndigestNumber.getUndigestedDays().addDays(remainingNumber.getDays().v());
//			if (remainingNumber.getMinutes().isPresent()){
//				if (!targetUndigestNumber.getUndigestedTime().isPresent()){
//					targetUndigestNumber.setUndigestedTime(Optional.of(new UndigestedTimeAnnualLeaveTime()));
//				}
//				targetUndigestNumber.getUndigestedTime().get().addMinutes(remainingNumber.getMinutes().get().v());
//			}
		}
		
		// 特休情報残数を更新
		this.updateRemainingNumber(aggregatePeriodWork.isAfterGrant());
		
		// 特休情報を「特休の集計結果．特休情報（消滅）」に追加
		if (!aggrResult.getLapsed().isPresent()) aggrResult.setLapsed(Optional.of(new ArrayList<>()));
		aggrResult.getLapsed().get().add(this.clone());
		
		// 「特休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 付与処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggregatePeriodWork 処理中の特休集計期間WORK
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param aggrResult 特休の集計結果
	 * @return 特休の集計結果
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
		//val deadline = this.annualPaidLeaveSet.calcDeadline(grantDate);
		val deadline = annualLeaveGrant.getDeadLine();
		
		// 付与日数を確認する
		double grantDays = 0.0;
		if (aggregatePeriodWork.getAnnualLeaveGrant().isPresent()){
			grantDays = aggregatePeriodWork.getAnnualLeaveGrant().get().getGrantDays().v().doubleValue();
		}
		
		// 次回特休付与を確認する
		Double prescribedDays = null;
		Double deductedDays = null;
		Double workingDays = null;
		Double attendanceRate = 0.0;
		if (aggregatePeriodWork.getAnnualLeaveGrant().isPresent()){
			val nextAnnLeaGrant = aggregatePeriodWork.getAnnualLeaveGrant().get();
			if (nextAnnLeaGrant.getPrescribedDays().isPresent()){
				prescribedDays = nextAnnLeaGrant.getPrescribedDays().get().v();
			}
			if (nextAnnLeaGrant.getDeductedDays().isPresent()){
				deductedDays = nextAnnLeaGrant.getDeductedDays().get().v();
			}
			if (nextAnnLeaGrant.getWorkingDays().isPresent()){
				workingDays = nextAnnLeaGrant.getWorkingDays().get().v();
			}
			if (nextAnnLeaGrant.getAttendanceRate().isPresent()){
				attendanceRate = nextAnnLeaGrant.getAttendanceRate().get().v().doubleValue();
			}
		}
		
		// 「特休付与残数データ」を作成する
		val newRemainData = new SpecialLeaveGrantRemaining(AnnualLeaveGrantRemainingData.createFromJavaType(
				"",
				companyId, employeeId, grantDate, deadline,
				LeaveExpirationStatus.AVAILABLE.value, GrantRemainRegisterType.MONTH_CLOSE.value,
				grantDays, null,
				0.0, null, null,
				grantDays, null,
				0.0,
				prescribedDays,
				deductedDays,
				workingDays));
		newRemainData.setDummyAtr(false);
		
		// 作成した「特休付与残数データ」を付与残数データリストに追加
		this.grantRemainingList.add(newRemainData);

//		// 付与後フラグ　←　true
//		this.afterGrantAtr = true;
		
		// 特休情報．付与情報に付与時の情報をセット
		double oldGrantDays = 0.0;
		if (this.grantInfo.isPresent()){
			oldGrantDays = this.grantInfo.get().getGrantDays().v().doubleValue();
		}
		this.grantInfo = Optional.of(AnnualLeaveGrant.of(
				new AnnualLeaveGrantDayNumber(oldGrantDays + grantDays),
				new YearlyDays(workingDays == null ? 0.0 : workingDays),
				new YearlyDays(prescribedDays == null ? 0.0 : prescribedDays),
				new YearlyDays(deductedDays == null ? 0.0 : deductedDays),
				new MonthlyDays(0.0),
				new MonthlyDays(0.0),
				new AttendanceRate(attendanceRate)));
		
		// 特休情報残数を更新
		this.updateRemainingNumber(aggregatePeriodWork.isAfterGrant());
		
		// 「特休情報（付与時点）」に「特休情報」を追加
		if (!aggrResult.getAsOfGrant().isPresent()) aggrResult.setAsOfGrant(Optional.of(new ArrayList<>()));
		aggrResult.getAsOfGrant().get().add(this.clone());
		
		// 「特休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 消化処理
	 * @param repositoriesRequiredByRemNum 残数処理 キャッシュクラス
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggregatePeriodWork 処理中の特休集計期間WORK
	 * @param tempAnnualLeaveMngs 暫定特休管理データリスト
	 * @param aggrResult 特休の集計結果
	 * @return 特休の集計結果
	 */
	private AggrResultOfAnnualLeave digestProcess(
			LeaveRemainingNumber.RequireM3 require,
			String companyId,
			String employeeId,
			AggregatePeriodWork aggregatePeriodWork,
			List<TmpAnnualLeaveMngWork> tempAnnualLeaveMngs,
			AggrResultOfAnnualLeave aggrResult){
		
		// 「暫定特休管理データリスト」を取得する
		tempAnnualLeaveMngs.sort((a, b) -> a.getYmd().compareTo(b.getYmd()));
		for (val tempAnnualLeaveMng : tempAnnualLeaveMngs){
			if (!aggregatePeriodWork.getPeriod().contains(tempAnnualLeaveMng.getYmd())) continue;
			
			// 特休を消化する
			{
				// 特休使用数WORK
				ManagementDays useDaysWork = new ManagementDays(tempAnnualLeaveMng.getUseDays().v());
				// 特休使用残数WORK
				ManagementDays remainDaysWork = new ManagementDays(tempAnnualLeaveMng.getUseDays().v());
				
				// 特休付与残数を取得
				List<LeaveGrantRemainingData> targetRemainingDatas 
					= new ArrayList<LeaveGrantRemainingData>();
				
				for (val remainingData : this.grantRemainingList){
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
				
				// 使用数変数作成
				LeaveUsedNumber leaveUsedNumber = new LeaveUsedNumber();
				leaveUsedNumber.setDays(new LeaveUsedDayNumber(tempAnnualLeaveMng.getUseDays().v()));
				// ooooo 要時間　leaveUsedNumber.setMinutes(minutes);
				
				// 「休暇残数シフトリストWORK」一時変数を作成
				RemNumShiftListWork remNumShiftListWork = new RemNumShiftListWork();
				
				boolean isForcibly = true; // ooooo
				
				// 消化する
				LeaveGrantRemainingData.digest(
						targetRemainingDatas,
						require,
						remNumShiftListWork,
						leaveUsedNumber,
						employeeId, 
						aggregatePeriodWork.getPeriod().start(),
						isForcibly);
				
				// 残数不足で一部消化できなかったとき
				if ( !remNumShiftListWork.getUnusedNumber().isZero() ){
					
					// 消化できなかった休暇使用数をもとに、付与残数ダミーデータを作成する
					// 「特休付与残数データ」を作成する
					val dummyRemainData = new SpecialLeaveGrantRemaining(
							AnnualLeaveGrantRemainingData.createFromJavaType(
							"",
							companyId, employeeId, tempAnnualLeaveMng.getYmd(), tempAnnualLeaveMng.getYmd(),
							LeaveExpirationStatus.AVAILABLE.value, GrantRemainRegisterType.MONTH_CLOSE.value,
							0.0, null,
							0.0, null, null,
							0.0, null,
							0.0,
							null, null, null));
					dummyRemainData.setDummyAtr(true);
					
//					// 特休を指定日数消化する
//					remainDaysWork = new ManagementDays(dummyRemainData.digest(remainDaysWork.v(), true));
					
					// 付与残数データに追加
					this.grantRemainingList.add(dummyRemainData);
					
				}
				
////				
////				for (val targetRemainingData : targetRemainingDatas){
////					
//////					// 特休を指定日数消化する
////					remainDaysWork = new ManagementDays(targetRemainingData.digest(remainDaysWork.v(), false));
////					
////					// 休暇残数を指定日数消化する
//					
//					
//					
//					
//					
//				}
//				
//				// 消化しきれなかった特休の消化処理
//				if (remainDaysWork.v() > 0.0)
//				{
//					// 「特休付与残数データ」を作成する
//					val dummyRemainData = new AnnualLeaveGrantRemaining(AnnualLeaveGrantRemainingData.createFromJavaType(
//							"",
//							companyId, employeeId, tempAnnualLeaveMng.getYmd(), tempAnnualLeaveMng.getYmd(),
//							LeaveExpirationStatus.AVAILABLE.value, GrantRemainRegisterType.MONTH_CLOSE.value,
//							0.0, null,
//							0.0, null, null,
//							0.0, null,
//							0.0,
//							null, null, null));
//					dummyRemainData.setDummyAtr(true);
//					
//					// 特休を指定日数消化する
//					remainDaysWork = new ManagementDays(dummyRemainData.digest(remainDaysWork.v(), true));
//					
//					// 付与残数データに追加
//					this.grantRemainingList.add(dummyRemainData);
//				}
//				
//				// 実特休（特休（マイナスあり））に使用数を加算する
//				this.remainingNumber.getAnnualLeaveWithMinus().addUsedNumber(
//						useDaysWork.v(), aggregatePeriodWork.isAfterGrant());
//				
//				// 特休情報残数を更新
//				this.updateRemainingNumber();
			}
		}
		
		// 残数不足エラーをチェックする
		{
			// 特休残数がマイナスかチェック
			val withMinus = this.remainingNumber.getAnnualLeaveWithMinus();
			if (withMinus.getRemainingNumberInfo().getRemainingNumber().isMinus()){
				if (aggregatePeriodWork.isAfterGrant()){
					// 「日単位特休不足エラー（付与後）」を追加
					aggrResult.addError(AnnualLeaveError.SHORTAGE_AL_OF_UNIT_DAY_AFT_GRANT);
				}
				else {
					// 「日単位特休不足エラー（付与前）」を追加
					aggrResult.addError(AnnualLeaveError.SHORTAGE_AL_OF_UNIT_DAY_BFR_GRANT);
				}
			}
		}
		
		// 「特休の集計結果」を返す
		return aggrResult;
	}
}



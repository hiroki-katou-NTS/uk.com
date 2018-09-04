package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export.CalcDeadlineForGrantDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export.GetUpperLimitSetting;

/**
 * 積立年休情報
 * @author shuichi_ishida
 */
@Getter
@Setter
public class ReserveLeaveInfo implements Cloneable {

	/** 年月日 */
	private GeneralDate ymd;
	/** 残数 */
	private ReserveLeaveRemainingNumber remainingNumber;
	/** 付与残数データ */
	private List<ReserveLeaveGrantRemaining> grantRemainingList;
	/** 使用数 */
	private ReserveLeaveUsedDayNumber usedDays;
	/** 付与後フラグ */
	private boolean afterGrantAtr;
	/** 付与情報 */
	private Optional<ReserveLeaveGrantInfo> grantInfo;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveInfo(){
		
		this.ymd = GeneralDate.min();
		this.remainingNumber = new ReserveLeaveRemainingNumber();
		this.grantRemainingList = new ArrayList<>();
		this.usedDays = new ReserveLeaveUsedDayNumber(0.0);
		this.afterGrantAtr = false;
		this.grantInfo = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param remainingNumber 残数
	 * @param grantRemainingList 付与残数データ
	 * @param usedDays 使用数
	 * @param afterGrantAtr 付与後フラグ
	 * @param grantInfo 付与情報
	 * @return 積立年休情報
	 */
	public static ReserveLeaveInfo of(
			GeneralDate ymd,
			ReserveLeaveRemainingNumber remainingNumber,
			List<ReserveLeaveGrantRemaining> grantRemainingList,
			ReserveLeaveUsedDayNumber usedDays,
			boolean afterGrantAtr,
			Optional<ReserveLeaveGrantInfo> grantInfo){
	
		ReserveLeaveInfo domain = new ReserveLeaveInfo();
		domain.ymd = ymd;
		domain.remainingNumber = remainingNumber;
		domain.grantRemainingList = grantRemainingList;
		domain.usedDays = usedDays;
		domain.afterGrantAtr = afterGrantAtr;
		domain.grantInfo = grantInfo;
		return domain;
	}
	
	@Override
	public ReserveLeaveInfo clone() {
		ReserveLeaveInfo cloned = new ReserveLeaveInfo();
		try {
			cloned.ymd = this.ymd;
			cloned.remainingNumber = this.remainingNumber.clone();
			for (val grantRemainingNumber : this.grantRemainingList){
				val detail = grantRemainingNumber.getDetails();
				Double overLimitDays = null;
				if (detail.getUsedNumber().getOverLimitDays().isPresent()){
					overLimitDays = detail.getUsedNumber().getOverLimitDays().get().v();
				}
				ReserveLeaveGrantRemaining newRemainData = new ReserveLeaveGrantRemaining(
						ReserveLeaveGrantRemainingData.createFromJavaType(
								grantRemainingNumber.getRsvLeaID(),
								grantRemainingNumber.getEmployeeId(),
								grantRemainingNumber.getGrantDate(),
								grantRemainingNumber.getDeadline(),
								grantRemainingNumber.getExpirationStatus().value,
								grantRemainingNumber.getRegisterType().value,
								detail.getGrantNumber().v(),
								detail.getUsedNumber().getDays().v(),
								overLimitDays,
								detail.getRemainingNumber().v()));
				newRemainData.setDummyAtr(grantRemainingNumber.isDummyAtr());
				cloned.grantRemainingList.add(newRemainData);
			}
			cloned.usedDays = new ReserveLeaveUsedDayNumber(this.usedDays.v());
			cloned.afterGrantAtr = this.afterGrantAtr;
			if (this.grantInfo.isPresent()){
				cloned.grantInfo = Optional.of(this.grantInfo.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("ReserveLeaveInfo clone error.");
		}
		return cloned;
	}
	
	public List<ReserveLeaveGrantRemainingData> getGrantRemainingNumberList(){
		return this.grantRemainingList.stream().map(c -> (ReserveLeaveGrantRemainingData)c)
				.collect(Collectors.toList());
	}
	
	/**
	 * 積立年休付与残数を更新
	 */
	public void updateRemainingNumber(){
		this.remainingNumber.updateRemainingNumber(this.grantRemainingList, this.afterGrantAtr);
	}
	
	/**
	 * 積立年休の消滅・付与・消化
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriodWork 積立年休集計期間WORK
	 * @param tmpReserveLeaveMngs 暫定積立年休管理データリスト
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param aggrResult 積立年休の集計結果
	 * @param getUpperLimitSetting 社員の保持年数を取得
	 * @param calcDeadlineForGrantDate 付与日から期限日を計算
	 * @param annualPaidLeaveSet 年休設定
	 * @param retentionYearlySet 積立年休設定
	 * @param emptYearlyRetentionSetMap 雇用積立年休設定マップ
	 * @return 積立年休の集計結果
	 */
	public AggrResultOfReserveLeave lapsedGrantDigest(
			String companyId,
			String employeeId,
			RsvLeaAggrPeriodWork aggrPeriodWork,
			List<TmpReserveLeaveMngWork> tmpReserveLeaveMngs,
			boolean isGetNextMonthData,
			AggrResultOfReserveLeave aggrResult,
			GetUpperLimitSetting getUpperLimitSetting,
			CalcDeadlineForGrantDate calcDeadlineForGrantDate,
			AnnualPaidLeaveSetting annualPaidLeaveSet,
			Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap) {
		
		// 期間終了日時点の積立年休情報を付与消滅前に退避するかチェック
		if (aggrPeriodWork.isNextDayAfterPeriodEnd() && !isGetNextMonthData){
			
			// 「積立年休の集計結果．積立年休情報（期間終了日時点）」　←　処理中の「積立年休情報」
			aggrResult.setAsOfPeriodEnd(this.clone());
		}
		
		// 付与前退避処理
		this.saveStateBeforeGrant(aggrPeriodWork);
		
		// 年月日を更新　←　開始日
		this.ymd = aggrPeriodWork.getPeriod().start();

		// 消滅処理
		aggrResult = this.lapsedProcess(aggrPeriodWork, aggrResult);
		
		// 付与処理
		aggrResult = this.grantProcess(companyId, employeeId, aggrPeriodWork, aggrResult,
				getUpperLimitSetting, calcDeadlineForGrantDate, retentionYearlySet, emptYearlyRetentionSetMap);
		
		// 上限を超過した積立年休を消滅させる
		this.lapsedExcessReserveLeave(aggrPeriodWork);
		
		// 期間終了日翌日時点の期間かチェック
		if (!aggrPeriodWork.isNextDayAfterPeriodEnd()){
			
			// 消化処理
			aggrResult = this.digestProcess(
					companyId, employeeId, aggrPeriodWork, tmpReserveLeaveMngs, aggrResult, annualPaidLeaveSet);
			
			// 年月日を更新　←　終了日
			this.ymd = aggrPeriodWork.getPeriod().end();
			
			// 「積立年休の集計結果」を返す
			return aggrResult;
		}

		// 期間終了日時点の積立年休情報を消化後に退避するかチェック
		if (isGetNextMonthData){
			
			// 「積立年休の集計結果．積立年休情報（期間終了日時点）」　←　処理中の「積立年休情報」
			aggrResult.setAsOfPeriodEnd(this.clone());
		}
		
		// 「積立年休の集計結果．積立年休情報（期間終了日の翌日開始時点）」　←　処理中の「積立年休情報」
		aggrResult.setAsOfStartNextDayOfPeriodEnd(this.clone());
		
		// 「積立年休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 付与前退避処理
	 * @param aggrPeriodWork 処理中の積立年休集計期間WORK
	 */
	private void saveStateBeforeGrant(RsvLeaAggrPeriodWork aggrPeriodWork){
		
		// 「年休集計期間WORK.付与フラグ」をチェック
		if (!aggrPeriodWork.isGrantAtr()) return;
		
		// 「積立年休情報．付与後フラグ」をチェック
		if (this.isAfterGrantAtr()) return;
		
		// 現在の積立年休（マイナスあり）の残数を付与前として退避する
		val withMinus = this.remainingNumber.getReserveLeaveWithMinus();
		withMinus.setRemainingNumberBeforeGrant(withMinus.getRemainingNumber().clone());

		// 現在の積立年休（マイナスなし）の残数を付与前として退避する
		val noMinus = this.remainingNumber.getReserveLeaveNoMinus();
		noMinus.setRemainingNumberBeforeGrant(noMinus.getRemainingNumber().clone());
	}
	
	/**
	 * 消滅処理
	 * @param aggrPeriodWork 処理中の積立年休集計期間WORK
	 * @param aggrResult 積立年休の集計結果
	 * @return 積立年休の集計結果
	 */
	private AggrResultOfReserveLeave lapsedProcess(
			RsvLeaAggrPeriodWork aggrPeriodWork,
			AggrResultOfReserveLeave aggrResult){
		
		// 消滅フラグを取得
		if (!aggrPeriodWork.isLapsedAtr()) return aggrResult;
		
		// 「付与残数データ」を取得
		val itrGrantRemainingNumber = this.grantRemainingList.listIterator();
		while (itrGrantRemainingNumber.hasNext()){
			val grantRemainingNumber = itrGrantRemainingNumber.next();
			
			// 期限日が積立年休集計期間WORK.期間.開始日の前日でなければ、消滅処理しない
			if (!grantRemainingNumber.getDeadline().equals(aggrPeriodWork.getPeriod().start().addDays(-1))){
				continue;
			}
			
			// 積立年休不足ダミーフラグがtrueなら、消滅処理しない
			if (grantRemainingNumber.isDummyAtr() == true) continue;
			
			// 処理中の付与残数データを期限切れにする
			grantRemainingNumber.setExpirationStatus(LeaveExpirationStatus.EXPIRED);
			
			// 未消化数を更新
			val targetUndigestNumber = this.remainingNumber.getReserveLeaveNoMinus().getUndigestedNumber();
			val remainingNumber = grantRemainingNumber.getDetails().getRemainingNumber();
			targetUndigestNumber.addDays(remainingNumber.v());
		}
		
		// 積立年休情報残数を更新
		this.updateRemainingNumber();
		
		// 積立年休情報を「積立年休の集計結果．積立年休情報（消滅）」に追加
		if (!aggrResult.getLapsed().isPresent()) aggrResult.setLapsed(Optional.of(new ArrayList<>()));
		aggrResult.getLapsed().get().add(this.clone());
		
		// 「積立年休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 付与処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriodWork 積立年休集計期間WORK
	 * @param aggrResult 積立年休の集計結果
	 * @param getUpperLimitSetting 社員の保持年数を取得
	 * @param calcDeadlineForGrantDate 付与日から期限日を計算
	 * @param retentionYearlySet 積立年休設定
	 * @param emptYearlyRetentionSetMap 雇用積立年休設定マップ
	 * @return 積立年休の集計結果
	 */
	private AggrResultOfReserveLeave grantProcess(
			String companyId,
			String employeeId,
			RsvLeaAggrPeriodWork aggrPeriodWork,
			AggrResultOfReserveLeave aggrResult,
			GetUpperLimitSetting getUpperLimitSetting,
			CalcDeadlineForGrantDate calcDeadlineForGrantDate,
			Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap) {
		
		// 「付与フラグ」をチェック
		if (!aggrPeriodWork.isGrantAtr()) return aggrResult;
		
		// 付与日から期限日を計算
		if (!aggrPeriodWork.getReserveLeaveGrant().isPresent()) return aggrResult;
		val reserveLeaveGrant = aggrPeriodWork.getReserveLeaveGrant().get();
		val grantDate = reserveLeaveGrant.getGrantYmd();
		val upperLimitSet = getUpperLimitSetting.algorithm(
				companyId, employeeId, grantDate, retentionYearlySet, emptYearlyRetentionSetMap);
		val deadline = calcDeadlineForGrantDate.algorithm(grantDate, upperLimitSet);
		
		// 付与日数を取得
		double grantDays = reserveLeaveGrant.getGrantDays().v();
		
		// 「積立年休付与残数データ」を作成する
		val newRemainData = new ReserveLeaveGrantRemaining(ReserveLeaveGrantRemainingData.createFromJavaType(
				"",
				employeeId,
				grantDate,
				deadline,
				LeaveExpirationStatus.AVAILABLE.value,
				GrantRemainRegisterType.MONTH_CLOSE.value,
				grantDays,
				0.0,
				null,
				grantDays));
		newRemainData.setDummyAtr(false);
		
		// 作成した「積立年休付与残数データ」を付与残数データリストに追加
		this.grantRemainingList.add(newRemainData);

		// 付与後フラグ　←　true
		this.afterGrantAtr = true;
		
		// 付与情報に付与時の情報をセット
		double infoDays = 0.0;
		if (this.grantInfo.isPresent()) infoDays = this.grantInfo.get().getGrantDays().v();
		this.grantInfo = Optional.of(ReserveLeaveGrantInfo.of(
				new ReserveLeaveGrantDayNumber(infoDays + grantDays)));
		
		// 積立年休情報残数を更新
		this.updateRemainingNumber();
		
		// 上限を超過した積立年休を消滅させる
		this.lapsedExcessReserveLeave(aggrPeriodWork);
		
		// 「積立年休情報（付与時点）」に「積立年休情報」を追加
		if (!aggrResult.getAsOfGrant().isPresent()) aggrResult.setAsOfGrant(Optional.of(new ArrayList<>()));
		aggrResult.getAsOfGrant().get().add(this.clone());
		
		// 「積立年休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 上限を超過した積立年休を消滅させる
	 * @param aggrPeriodWork 処理中の積立年休集計期間WORK
	 */
	private void lapsedExcessReserveLeave(
			RsvLeaAggrPeriodWork aggrPeriodWork){
		
		// 上限日数と積立年休残日数を比較
		Integer maxDays = aggrPeriodWork.getMaxDays().v();
		val noMinus = this.remainingNumber.getReserveLeaveNoMinus();
		double totalRemain = noMinus.getRemainingNumber().getTotalRemainingDays().v();
		if (maxDays.doubleValue() < totalRemain){
			
			// 上限を超過した日数を計算　→　上限超過日数
			double excessDays = totalRemain - maxDays.doubleValue();
			
			// 付与残数データを取得　（付与日　昇順、期限日　昇順）
			List<ReserveLeaveGrantRemaining> remainingDatas = new ArrayList<>();
			for (val grantRemainingData : this.grantRemainingList){
				if (grantRemainingData.getExpirationStatus() != LeaveExpirationStatus.AVAILABLE) continue;
				remainingDatas.add(grantRemainingData);
			}
			Collections.sort(remainingDatas, new Comparator<ReserveLeaveGrantRemaining>() {
				@Override
				public int compare(ReserveLeaveGrantRemaining o1, ReserveLeaveGrantRemaining o2) {
					int compGrant = o1.getGrantDate().compareTo(o2.getGrantDate());
					if (compGrant != 0) return compGrant;
					return o1.getDeadline().compareTo(o2.getDeadline());
				}
			});
			for (val remainingData : remainingDatas){

				// 積立年休残日数と上限超過日数を比較
				val details = remainingData.getDetails();
				if (excessDays <= details.getRemainingNumber().v()){
					details.addDaysToRemainingNumber(-excessDays);
					details.addDaysToOverLimitDays(excessDays);
					excessDays = 0.0;
				}
				else {
					excessDays -= details.getRemainingNumber().v();
					details.addDaysToOverLimitDays(details.getRemainingNumber().v());
					details.addDaysToRemainingNumber(-details.getRemainingNumber().v());
				}
				
				// 上限超過日数が残っているかチェック
				if (excessDays <= 0.0) break;
			}
			
			// 積立年休情報残数を更新
			this.updateRemainingNumber();
		}
	}
	
	/**
	 * 消化処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriodWork 積立年休集計期間WORK
	 * @param tmpReserveLeaveMngs 暫定積立年休管理データリスト
	 * @param aggrResult 積立年休の集計結果
	 * @param annualPaidLeaveSet 年休設定
	 * @return 積立年休の集計結果
	 */
	private AggrResultOfReserveLeave digestProcess(
			String companyId,
			String employeeId,
			RsvLeaAggrPeriodWork aggrPeriodWork,
			List<TmpReserveLeaveMngWork> tmpReserveLeaveMngs,
			AggrResultOfReserveLeave aggrResult,
			AnnualPaidLeaveSetting annualPaidLeaveSet){
		
		// 「暫定積立年休管理データリスト」を取得する
		List<TmpReserveLeaveMngWork> targetList = new ArrayList<>();
		for (val tmpReserveLeaveMng : tmpReserveLeaveMngs){
			if (!aggrPeriodWork.getPeriod().contains(tmpReserveLeaveMng.getYmd())) continue;
			targetList.add(tmpReserveLeaveMng);
		}
		targetList.sort((a, b) -> a.getYmd().compareTo(b.getYmd()));
		for (val tmpReserveLeaveMng : targetList){
			
			// 積立年休を消化する
			{
				// 積立年休使用数WORK
				double useDaysWork = tmpReserveLeaveMng.getUseDays().v();
				// 積立年休使用残数WORK
				double remainDaysWork = tmpReserveLeaveMng.getUseDays().v();
				
				// 積立年休付与残数を取得
				List<ReserveLeaveGrantRemaining> targetRemainingDatas = new ArrayList<>();
				for (val remainingData : this.grantRemainingList){
					if (tmpReserveLeaveMng.getYmd().before(remainingData.getGrantDate())) continue;
					if (tmpReserveLeaveMng.getYmd().after(remainingData.getDeadline())) continue;
					targetRemainingDatas.add(remainingData);
				}
				
				// 取得設定をチェック
				if (annualPaidLeaveSet.getAcquisitionSetting().annualPriority == AnnualPriority.FIFO){

					// 当年付与分から消化する　（付与日　降順(DESC)）
					targetRemainingDatas.sort((a, b) -> -a.getGrantDate().compareTo(b.getGrantDate()));
				}
				else {
					
					// 繰越分から消化する　（付与日　昇順(ASC)）
					targetRemainingDatas.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
				}
				
				for (val targetRemainingData : targetRemainingDatas){
					
					// 積立年休を指定日数消化する
					remainDaysWork = targetRemainingData.digest(remainDaysWork, false);
				}
				
				// 消化しきれなかった積立年休を消化する
				if (remainDaysWork > 0.0)
				{
					// 「積立年休付与残数データ」を作成する
					val dummyRemainData = new ReserveLeaveGrantRemaining(ReserveLeaveGrantRemainingData.createFromJavaType(
							"",
							employeeId,
							tmpReserveLeaveMng.getYmd(),
							tmpReserveLeaveMng.getYmd(),
							LeaveExpirationStatus.AVAILABLE.value,
							GrantRemainRegisterType.MONTH_CLOSE.value,
							0.0,
							0.0,
							null,
							0.0));
					dummyRemainData.setDummyAtr(true);
					
					// 積立年休を指定日数消化する
					remainDaysWork = dummyRemainData.digest(remainDaysWork, true);
					
					// 付与残数データに追加
					this.grantRemainingList.add(dummyRemainData);
				}
				
				// 実積立年休（年休（マイナスあり））に使用数を加算する
				this.remainingNumber.getReserveLeaveWithMinus().addUsedNumber(
						useDaysWork, aggrPeriodWork.isAfterGrant());
				
				// 積立年休情報残数を更新
				this.updateRemainingNumber();
			}
		}
		
		// 残数不足エラーをチェックする
		{
			// 残数がマイナスかチェック
			val withMinus = this.remainingNumber.getReserveLeaveWithMinus();
			if (withMinus.getRemainingNumber().getTotalRemainingDays().v() < 0.0){
				if (this.afterGrantAtr){
					
					// 「積立年休不足エラー（付与後）」を追加
					aggrResult.addError(ReserveLeaveError.SHORTAGE_RSVLEA_AFTER_GRANT);
				}
				else {
					
					// 「積立年休不足エラー（付与前）」を追加
					aggrResult.addError(ReserveLeaveError.SHORTAGE_RSVLEA_BEFORE_GRANT);
				}
			}
		}
		
		// 「積立年休の集計結果」を返す
		return aggrResult;
	}
	
	/**
	 * 不足分を付与残数データとして作成・削除する
	 * @param isOutputForShortage 不足分付与残数データ出力区分
	 * @param isCreate 作成するかどうか
	 */
	public void createShortageData(Optional<Boolean> isOutputForShortage, boolean isCreate){

		if (isCreate){
			
			// 「不足分付与残数データ出力区分」をチェック
			boolean isOutput = false;
			if (isOutputForShortage.isPresent()) isOutput = isOutputForShortage.get();
			if (isOutput){

				// ダミーとして作成した「付与残数データ」を合計
				ReserveLeaveGrantRemaining dummyData = null;
				double usedDays = 0.0;
				double remainDays = 0.0;
				for (val grantRemaining : this.grantRemainingList){
					if (!grantRemaining.isDummyAtr()) continue;
					if (dummyData == null) dummyData = grantRemaining;
					usedDays += grantRemaining.getDetails().getUsedNumber().getDays().v();
					remainDays += grantRemaining.getDetails().getRemainingNumber().v();
				}

				if (dummyData != null){
					// 「積立年休付与残数データ」を作成する
					val dummyRemainData = new ReserveLeaveGrantRemaining(ReserveLeaveGrantRemainingData.createFromJavaType(
							"",
							dummyData.getEmployeeId(),
							dummyData.getGrantDate(),
							dummyData.getDeadline(),
							LeaveExpirationStatus.AVAILABLE.value,
							GrantRemainRegisterType.MONTH_CLOSE.value,
							0.0,
							usedDays,
							null,
							remainDays));
					dummyRemainData.setDummyAtr(false);
					
					// 付与残数データに追加
					this.grantRemainingList.add(dummyRemainData);
				}
			}
		}
		
		// 不足分として作成した付与残数データを削除する
		val itrGrantRemaining = this.grantRemainingList.listIterator();
		while (itrGrantRemaining.hasNext()){
			val grantRemaining = itrGrantRemaining.next();
			if (grantRemaining.isDummyAtr()) itrGrantRemaining.remove();
		}
	}
}

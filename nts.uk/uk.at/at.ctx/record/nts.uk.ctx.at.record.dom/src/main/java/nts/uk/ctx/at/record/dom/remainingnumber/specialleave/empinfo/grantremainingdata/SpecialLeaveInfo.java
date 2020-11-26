package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodProc;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService.RequireM5;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RemNumShiftListWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RemNumShiftWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveOverNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUndigestNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.SpecialLeaveUndigestNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialHolidayInterimMngData;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;

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
	private SpecialLeaveRemaining remainingNumber;
	/** 付与残数データ */
	private List<SpecialLeaveGrantRemainingData> grantRemainingList;

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveInfo(){

		this.ymd = GeneralDate.min();
		this.remainingNumber = new SpecialLeaveRemaining();
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
			SpecialLeaveRemaining remainingNumber,
			List<SpecialLeaveGrantRemainingData> grantRemainingNumberList
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

			cloned.grantRemainingList = new ArrayList<>();
			for (val grantRemainingNumber : this.grantRemainingList){
				grantRemainingList.add(grantRemainingNumber.clone());

			}
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveInfo clone error.");
		}
		return cloned;
	}

	public List<SpecialLeaveGrantRemaining> getGrantRemainingNumberList(){
		return this.grantRemainingList.stream().map(c -> (SpecialLeaveGrantRemaining)c)
				.collect(Collectors.toList());
	}

	/**
	 * 特休付与残数を更新
	 */
	public void updateRemainingNumber(boolean afterGrant){
		this.remainingNumber.updateRemainingNumber(
				this.getGrantRemainingNumberList(), afterGrant);
	}

//	List<SpecialLeaveGrantRemaining> remainingDataList,
//	boolean afterGrantAtr){


	/**
	 * 特休の消滅・付与・消化
	 * @param require
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param specialLeaveAggregatePeriodWork 処理中の特休集計期間WORK
	 * @param interimSpecialHolidayMng 暫定特休管理データ
//	 * @param isGetNextMonthData 翌月管理データ取得フラグ
//	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param aggrResult 特休の集計結果
	 * @param specialLeaveCode 特別休暇コード
	 * @return 特休の集計結果
	 */
	public InPeriodOfSpecialLeaveResultInfor lapsedGrantDigest(
			SpecialLeaveManagementService.RequireM5 require,
			String companyId,
			String employeeId,
			SpecialLeaveAggregatePeriodWork specialLeaveAggregatePeriodWork,
			SpecialHolidayInterimMngData specialHolidayInterimMngData,
//			boolean isGetNextMonthData,
//			boolean isCalcAttendanceRate,
			int specialLeaveCode,
			InPeriodOfSpecialLeaveResultInfor aggrResult
			){

		// 付与前退避処理
		this.saveStateBeforeGrant(specialLeaveAggregatePeriodWork);

		// 特別休暇情報．年月日を開始日に更新
		this.ymd = specialLeaveAggregatePeriodWork.getPeriod().start();

		// 消滅処理
		aggrResult = this.lapsedProcess(specialLeaveAggregatePeriodWork, aggrResult);

		// 付与処理
		aggrResult = this.grantProcess(require, companyId, employeeId,
				specialLeaveAggregatePeriodWork, aggrResult, specialLeaveCode);

		// 消化処理
		aggrResult = this.digestProcess(
				require, companyId, employeeId,
				specialLeaveAggregatePeriodWork, specialHolidayInterimMngData, aggrResult);

		// 残数不足エラーをチェックする -----------------------------
		{
			// 特休残数がマイナスかチェック
			val withMinus = this.remainingNumber.getSpecialLeaveWithMinus();
			if (withMinus.getRemainingNumberInfo().getRemainingNumber().isMinus()){
				if (specialLeaveAggregatePeriodWork.isAfterGrant()){
					// 「日単位特休不足エラー（付与後）」を追加
					aggrResult.addError(SpecialLeaveError.AFTERGRANT);
				}
				else {
					// 「日単位特休不足エラー（付与前）」を追加
					aggrResult.addError(SpecialLeaveError.BEFOREGRANT);
				}
			}
		}

		// 期間終了退避処理  -----------------------------

		// 期間終了日時点の特休情報を消化後に退避するかチェック
		// 処理中の「特別休暇集計期間WORK．終了日の期間かどうか」=true
		if (specialLeaveAggregatePeriodWork.isDayBeforePeriodEnd()){

			// 「特別休暇の集計結果．特別休暇情報(期間終了日時点)」←処理中の「特別休暇情報」
			aggrResult.setAsOfPeriodEnd(this.clone());
		}

		// 期間終了日翌日時点の期間かチェック
		if (specialLeaveAggregatePeriodWork.isNextDayAfterPeriodEnd()){

			// 「特別休暇の集計結果．特別休暇情報(期間終了日の翌日開始時点)」←処理中の「特別休暇情報」
			aggrResult.setAsOfStartNextDayOfPeriodEnd(this.clone());
		}

		// 終了時点更新処理 -------------------------------------

		// 期間終了日翌日時点の期間かチェック
		// 処理中の「特別休暇集計期間WORK．終了日の翌日の期間かどうか」= true
		if (specialLeaveAggregatePeriodWork.isNextDayAfterPeriodEnd()){
			// 何もしない
		} else {
			// 「特別休暇の集計結果．特別休暇エラー情報」に受け取った特別休暇エラーを全て追加
			// ※既に「特別休暇エラー情報」に存在する特別休暇エラーは追加不要。
			// ooooo要修正！！

			// 年月日を更新　←　終了日
			this.ymd = specialLeaveAggregatePeriodWork.getPeriod().end();
		}

		// 「特休の集計結果」を返す
		return aggrResult;
	}

	/**
	 * 付与前退避処理
	 * @param specialLeaveAggregatePeriodWork 処理中の特休集計期間WORK
	 */
	private void saveStateBeforeGrant(
			SpecialLeaveAggregatePeriodWork aggregatePeriodWork){

		// パラメータ「特別休暇集計期間WORK．期間の開始日に付与があるかどうか」をチェック
		if (!aggregatePeriodWork.getGrantWork().isGrantAtr()) return;

//		// 「特休情報．付与後フラグ」をチェック
//		if (this.isAfterGrantAtr()) return;

		// 初回付与か判断する
//		if ( aggregatePeriodWork.getGrantWork().getGrantNumber() != 1 ){
//			return;
//		}
		if ( aggregatePeriodWork.getGrantWork().getSpecialLeaveGrant().isPresent() ){
			if ( aggregatePeriodWork.getGrantWork().getSpecialLeaveGrant().get().getTimes().v() != 1 ){
				return;
			}
		}

		// 現在の特休（マイナスあり）の残数を付与前として退避する
		val withMinus = this.remainingNumber.getSpecialLeaveWithMinus();
		withMinus.saveStateBeforeGrant();

		// 現在の特休（マイナスなし）の残数を付与前として退避する
		val noMinus = this.remainingNumber.getSpecialLeaveNoMinus();
		noMinus.saveStateAfterGrant();
	}

	/**
	 * 消滅処理
	 * @param aggregatePeriodWork 処理中の特休集計期間WORK
	 * @param aggrResult 特休の集計結果
	 * @return 特休の集計結果
	 */
	private InPeriodOfSpecialLeaveResultInfor lapsedProcess(
			SpecialLeaveAggregatePeriodWork aggregatePeriodWork,
			InPeriodOfSpecialLeaveResultInfor aggrResult){

		// 消滅フラグを取得
		if (!aggregatePeriodWork.getLapsedWork().isLapsedAtr()) return aggrResult;

		// 特別休暇を消滅させる --------------------------

		// 「特別休暇情報．付与残数データ」を取得
		// 【条件】
		// 期限日=特別休暇集計期間WORK．期間．開始日の前日
		// 年休不足ダミーフラグ=false
		// 【ソート】
		// 付与日(ASC)
		// ※期限が切れた翌日開始時点で消滅する

		this.grantRemainingList.sort((a,b)->a.getGrantDate().compareTo(b.getGrantDate()));
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

			// 未消化数を更新
			Optional<SpecialLeaveUndigestNumber> targetUndigestNumber
				= this.remainingNumber.getSpecialLeaveUndigestNumber();
			if ( !targetUndigestNumber.isPresent()){
				this.remainingNumber.setSpecialLeaveUndigestNumber(
						Optional.of(new SpecialLeaveUndigestNumber()));
				targetUndigestNumber = this.remainingNumber.getSpecialLeaveUndigestNumber();
			}
			val remainingNumber = grantRemainingNumber.getDetails().getRemainingNumber();

			int minites = 0;
			if ( remainingNumber.getMinutes().isPresent() ){
				minites = remainingNumber.getMinutes().get().v();
			}
			LeaveUndigestNumber leaveUndigestNumber
				= new LeaveUndigestNumber(remainingNumber.getDays().v(), minites);

			// 特別休暇情報残数．未消化←処理中の「付与残数データ．残数」を加算
			targetUndigestNumber.get().add(leaveUndigestNumber);
		}

		// 特別休暇情報残数を更新
		this.updateRemainingNumber(aggregatePeriodWork.isAfterGrant());

		// 特休情報を「特休の集計結果．特休情報（消滅）」に追加
		if (!aggrResult.getLapsed().isPresent()){
			aggrResult.setLapsed(Optional.of(new ArrayList<>()));
		}
		aggrResult.getLapsed().get().add(this.clone());

		// 「特休の集計結果」を返す
		return aggrResult;
	}

	/**
	 * 付与処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggregatePeriodWork 特休集計期間WORK
	 * @param aggrResult 特休の集計結果
	 * @param specialLeaveCode 特別休暇コード
	 * @return 特休の集計結果
	 */
	private InPeriodOfSpecialLeaveResultInfor grantProcess(
			SpecialLeaveManagementService.RequireM5 require,
			String companyId,
			String employeeId,
			SpecialLeaveAggregatePeriodWork aggregatePeriodWork,
			InPeriodOfSpecialLeaveResultInfor aggrResult,
			int specialLeaveCode){

		// 「付与フラグ」をチェック
		if (!aggregatePeriodWork.getGrantWork().isGrantAtr()) return aggrResult;

		// 特別休暇を付与する
		SpecialLeaveInfo specialLeaveInfo = new SpecialLeaveInfo();

		// 特別休暇付与残数データを作成する --------------------------

		// パラメータ「次回特別休暇付与.期限日」を取得
		GeneralDate deadline = GeneralDate.max();
		if ( aggregatePeriodWork.getGrantWork().getSpecialLeaveGrant().isPresent() ){
			deadline = aggregatePeriodWork.getGrantWork().getSpecialLeaveGrant().get().getDeadLine();

			// 「特別休暇付与残数データ」を作成
			val newRemainData = new SpecialLeaveGrantRemaining(
					SpecialLeaveGrantRemainingData.createFromJavaType(
					"",
					companyId,
					employeeId,
					aggregatePeriodWork.getPeriod().start(),
					deadline,
					LeaveExpirationStatus.AVAILABLE.value,
					GrantRemainRegisterType.MONTH_CLOSE.value,
					aggregatePeriodWork.getGrantWork().getSpecialLeaveGrant().get().getGrantDays().v(), // 付与日数
					aggregatePeriodWork.getGrantWork().getSpecialLeaveGrant().get().getTimes().v(),
					0.0, 0,
					0.0, 0.0, 0, 0.0, false, specialLeaveCode));
			newRemainData.setDummyAtr(false);

			// 作成した「特休付与残数データ」を付与残数データリストに追加
			specialLeaveInfo.grantRemainingList.add(newRemainData);
		}

		// 付与上限まで調整 --------------------------

		// 上限設定されているか確認

		// 特別休暇コードから設定を取得（会社設定）
		Optional<SpecialHoliday> specialHolidayOpt
			= require.specialHoliday(companyId, specialLeaveCode);

		// 繰越上限日数 > 0
		if ( specialHolidayOpt.isPresent() ){

			// 繰越上限日数
			int limitCarryoverDays = specialHolidayOpt.get().getGrantPeriodic().getLimitCarryoverDays().v();
			if ( limitCarryoverDays > 0 ){

				// 合計
				LeaveRemainingNumber leaveRemainingNumber = new LeaveRemainingNumber();

				// 特別休暇付与残数データ.明細.残数の合計を取得
				specialLeaveInfo.getGrantRemainingList().forEach(c->{
					leaveRemainingNumber.add(c.getDetails().getRemainingNumber());
				});

				// 残数合計と上限日数の差分を求める
				double diff = leaveRemainingNumber.getDays().v() - limitCarryoverDays;

				// 差分を残数から引き、上限超過消滅日数に加算
				if ( diff > 0 ){

					// 付与日でソート
					List<SpecialLeaveGrantRemainingData> list = specialLeaveInfo.getGrantRemainingList().stream()
						.sorted((a,b)->a.getGrantDate().compareTo(b.getGrantDate()))
						.collect(Collectors.toList());

					// 付与残数データの一番新しいデータ
					if ( list.size() > 0 ){
						SpecialLeaveGrantRemainingData specialLeaveGrantRemainingData
							= list.get(list.size()-1);

						// 付与残数データの一番新しいデータから上限を超えた差分を引く
						double daysDiff
							= specialLeaveGrantRemainingData.getDetails().getRemainingNumber().getDays().v() - diff;
						int time = 0;
						if ( specialLeaveGrantRemainingData.getDetails().getRemainingNumber().getMinutes().isPresent() ){
							time = specialLeaveGrantRemainingData.getDetails().getRemainingNumber().getMinutes().get().v();
						}

						// 付与残数データ.残数←　付与残数データ.残数　ー　差分
						specialLeaveGrantRemainingData.getDetails().setRemainingNumber(
								new LeaveRemainingNumber(daysDiff, time));

						// 付与残数データ.使用数.上限超過消滅日数←　付与残数データ.使用数.上限超過消滅日数　＋　差分
						specialLeaveGrantRemainingData.getDetails().getUsedNumber().setLeaveOverLimitNumber(
								Optional.of(new LeaveOverNumber(daysDiff, 0))); // oooooo
					}
				}
			}
		}






//		//
//		// 「特休上限データ」を取得
//		val annLeaMaxDataOpt = require.SpecialLeaveMaxData(employeeId);

		// 特別休暇情報残数を更新
		specialLeaveInfo.updateRemainingNumber(aggregatePeriodWork.isAfterGrant());

		// 「特別休暇情報(付与時点)」に「特別休暇情報」を追加
		if ( !aggrResult.getAsOfGrant().isPresent() ){
			aggrResult.setAsOfGrant(Optional.of(new ArrayList<SpecialLeaveInfo>()));
		}
		aggrResult.getAsOfGrant().get().add(specialLeaveInfo);

		// 「特休の集計結果」を返す
		return aggrResult;

//
//
//
//		// 付与日から期限日を計算
//		if (!aggregatePeriodWork.getSpecialLeaveGrant().isPresent()) return aggrResult;
//		val annualLeaveGrant = aggregatePeriodWork.getSpecialLeaveGrant().get();
//		val grantDate = annualLeaveGrant.getGrantDate();
//		//val deadline = this.annualPaidLeaveSet.calcDeadline(grantDate);
//		val deadline = annualLeaveGrant.getDeadLine();
//
//		// 付与日数を確認する
//		double grantDays = 0.0;
//		if (aggregatePeriodWork.getSpecialLeaveGrant().isPresent()){
//			grantDays = aggregatePeriodWork.getSpecialLeaveGrant().get().getGrantDays().v().doubleValue();
//		}
//
//		// 次回特休付与を確認する
//		Double prescribedDays = null;
//		Double deductedDays = null;
//		Double workingDays = null;
//		Double attendanceRate = 0.0;
//		if (aggregatePeriodWork.getSpecialLeaveGrant().isPresent()){
//			val nextAnnLeaGrant = aggregatePeriodWork.getSpecialLeaveGrant().get();
//			if (nextAnnLeaGrant.getPrescribedDays().isPresent()){
//				prescribedDays = nextAnnLeaGrant.getPrescribedDays().get().v();
//			}
//			if (nextAnnLeaGrant.getDeductedDays().isPresent()){
//				deductedDays = nextAnnLeaGrant.getDeductedDays().get().v();
//			}
//			if (nextAnnLeaGrant.getWorkingDays().isPresent()){
//				workingDays = nextAnnLeaGrant.getWorkingDays().get().v();
//			}
//			if (nextAnnLeaGrant.getAttendanceRate().isPresent()){
//				attendanceRate = nextAnnLeaGrant.getAttendanceRate().get().v().doubleValue();
//			}
//		}
//
////		// 「特休付与残数データ」を作成する
////		val newRemainData = new SpecialLeaveGrantRemaining(SpecialLeaveGrantRemainingData.createFromJavaType(
////				"",
////				companyId, employeeId, grantDate, deadline,
////				LeaveExpirationStatus.AVAILABLE.value, GrantRemainRegisterType.MONTH_CLOSE.value,
////				grantDays, null,
////				0.0, null, null,
////				grantDays, null,
////				0.0,
////				prescribedDays,
////				deductedDays,
////				workingDays));
////		newRemainData.setDummyAtr(false);
////
////		// 作成した「特休付与残数データ」を付与残数データリストに追加
////		this.grantRemainingList.add(newRemainData);
//
////		// 付与後フラグ　←　true
////		this.afterGrantAtr = true;
//
//		// 特休情報．付与情報に付与時の情報をセット
//		double oldGrantDays = 0.0;
//		if (this.grantInfo.isPresent()){
//			oldGrantDays = this.grantInfo.get().getGrantDays().v().doubleValue();
//		}
//		this.grantInfo = Optional.of(SpecialLeaveGrant.of(
//				new SpecialLeaveGrantDayNumber(oldGrantDays + grantDays),
//				new YearlyDays(workingDays == null ? 0.0 : workingDays),
//				new YearlyDays(prescribedDays == null ? 0.0 : prescribedDays),
//				new YearlyDays(deductedDays == null ? 0.0 : deductedDays),
//				new MonthlyDays(0.0),
//				new MonthlyDays(0.0),
//				new AttendanceRate(attendanceRate)));
//
//		// 特休情報残数を更新
//		this.updateRemainingNumber(aggregatePeriodWork.isAfterGrant());
//
//		// 「特休情報（付与時点）」に「特休情報」を追加
//		if (!aggrResult.getAsOfGrant().isPresent()) aggrResult.setAsOfGrant(Optional.of(new ArrayList<>()));
//		aggrResult.getAsOfGrant().get().add(this.clone());
//
//		// 「特休の集計結果」を返す
//		return aggrResult;
	}

	/**
	 * 消化処理
	 * @param require
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggregatePeriodWork 特休集計期間WORK
	 * @param specialHolidayInterimMngData 暫定特休管理データ
	 * @param aggrResult 特休の集計結果
	 * @return 特休の集計結果
	 */
	private InPeriodOfSpecialLeaveResultInfor digestProcess(
			LeaveRemainingNumber.RequireM3 require,
			String companyId,
			String employeeId,
			SpecialLeaveAggregatePeriodWork aggregatePeriodWork,
			SpecialHolidayInterimMngData specialHolidayInterimMngData,
			InPeriodOfSpecialLeaveResultInfor aggrResult){

		// 集計期間の翌日を集計する時は、消化処理は行わない
		if ( aggregatePeriodWork.isNextDayAfterPeriodEnd() ){
			return aggrResult;
		}

		// 暫定残数管理データ
		List<InterimSpecialHolidayMng> listInterimSpecialHolidayMng
			 = specialHolidayInterimMngData.getLstSpecialInterimMng();

		// 時間休暇消化日一覧（List）
		List<GeneralDate> digestDateList = new ArrayList<GeneralDate>();

		// パラメータ「List<特別休暇暫定管理データ」を取得する
		// 【条件】 対象日 >= 特別休暇集計期間WORK．期間．開始日
		// 		AND 対象日 <= 特別休暇集計期間WORK．期間．終了日
		// 【ソート】 対象日
		listInterimSpecialHolidayMng.sort((a, b) -> a.getYmd().compareTo(b.getYmd()));
		for (val interimSpecialHolidayMng : listInterimSpecialHolidayMng){
			if (!aggregatePeriodWork.getPeriod().contains(interimSpecialHolidayMng.getYmd())) continue;

			// 特休を消化する
			{
//				// 特休使用数WORK
//				ManagementDays useDaysWork = new ManagementDays(interimSpecialHolidayMng.getUseDays().v());
//				// 特休使用残数WORK
//				ManagementDays remainDaysWork = new ManagementDays(interimSpecialHolidayMng.getUseDays().v());

				// 特休付与残数を取得
				List<LeaveGrantRemainingData> targetRemainingDatas
					= new ArrayList<LeaveGrantRemainingData>();

				for (val remainingData : this.grantRemainingList){
					if (interimSpecialHolidayMng.getYmd().before(remainingData.getGrantDate())) continue;
					if (interimSpecialHolidayMng.getYmd().after(remainingData.getDeadline())) continue;
					targetRemainingDatas.add(remainingData);
				}

				// 休暇残数を指定使用数消化する ----------------------------------

				// 「休暇残数シフトリストWORK」一時変数を作成
				RemNumShiftListWork remNumShiftListWork = new RemNumShiftListWork();

				// 取得した「付与残数」でループ
				targetRemainingDatas.forEach(a->{

					// 使用数変数作成
					double days = 0.0;
					if ( interimSpecialHolidayMng.getUseDays().isPresent() ){
						days = interimSpecialHolidayMng.getUseDays().get().v();
					}
					int minutes = 0;
					if ( interimSpecialHolidayMng.getUseTimes().isPresent() ){
						minutes = interimSpecialHolidayMng.getUseTimes().get().v();
					}
					LeaveUsedNumber leaveUsedNumber = new LeaveUsedNumber();
					leaveUsedNumber.setDays(new LeaveUsedDayNumber(days));
					leaveUsedNumber.setMinutes(Optional.of(new LeaveUsedTime(minutes)));

					boolean isForcibly = true;

					// 休暇付与残数を追加する
					remNumShiftListWork.AddLeaveGrantRemainingData(a);

					// 休暇残数を指定使用数消化する
					LeaveGrantRemainingData.digest(
							require,
							targetRemainingDatas,
							remNumShiftListWork,
							leaveUsedNumber,
							employeeId,
							aggregatePeriodWork.getPeriod().start(),
							isForcibly);

					// 時間休暇消化数を求める
					// 消化できた時間を求める
					// 消化できた時間　←特別休暇使用数．使用時間 ー INPUT.消化できなかった休暇使用数．時間

					// 特別休暇使用数．使用時間
					int usedTime = 0;
					if ( a.getDetails().getUsedNumber().getMinutes().isPresent() ){
						usedTime = a.getDetails().getUsedNumber().getMinutes().get().v();
					}
					// 消化できなかった休暇使用数．時間
					int unDigestTime = 0;
					if ( remNumShiftListWork.getUnusedNumber().getMinutes().isPresent() ){
						unDigestTime = remNumShiftListWork.getUnusedNumber().getMinutes().get().v();
					}
					usedTime -= unDigestTime;

					// 時間休暇消化日一覧に追加をするか

					// 消化できた時間があるか （消化できた時間　＞０）
					if ( 0 < usedTime ){
						// 消化日を追加
						digestDateList.add(interimSpecialHolidayMng.getYmd());
					}

					// 残数（現在）を消化後の状態にする
					this.updateRemainingNumber(aggregatePeriodWork.isAfterGrant());

				});

//				// 実特休（特休（マイナスあり））に使用数を加算する
//				this.remainingNumber.getSpecialLeaveWithMinus().addUsedNumber(
//						useDaysWork.v(), aggregatePeriodWork.isAfterGrant());
//
//				// 特休情報残数を更新
//				this.updateRemainingNumber();

			}
		}


		// 「特休の集計結果」を返す
		return aggrResult;
	}

	/**
	 * 付与残数データから特別休暇不足分の特別休暇付与残数を削除
	 */
	public void deleteDummy(){
		// 「特別休暇付与残数．特別休暇不足ダミーフラグ」=trueの特別休暇付与残数をListから削除
		List<SpecialLeaveGrantRemaining> noDummyList
			= this.getGrantRemainingNumberList().stream()
				.filter(c->!c.isDummyAtr())
				.collect(Collectors.toList());
		this.setGrantRemainingList(noDummyList);
	}
}



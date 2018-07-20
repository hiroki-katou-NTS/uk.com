package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggregatePeriodWork;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 処理：期間中の年休残数を取得
 * @author shuichu_ishida
 */
public class GetAnnLeaRemNumWithinPeriodProc {

	/** 年休設定 */
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	/** 年休付与残数データ */
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
	/** 年休上限データ */
	private AnnLeaMaxDataRepository annLeaMaxDataRepo;
	/** 社員に対応する締め開始日を取得する */
	private GetClosureStartForEmployee getClosureStartForEmployee;
	/** 次回年休付与日を計算 */
	private CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantDate;
	/** 暫定年休管理データを作成する */
	private CreateTempAnnualLeaveManagement createTempAnnualLeaveMng;
	/** 期間中の年休残数を取得 */
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
	
	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 集計期間 */
	private DatePeriod aggrPeriod;
	/** モード */
	private TempAnnualLeaveMngMode mode;
	/** 翌月管理データ取得フラグ */
	private boolean isGetNextMonthData;
	/** 出勤率計算フラグ */
	private boolean isCalcAttendanceRate;
	/** 上書きフラグ */
	private Optional<Boolean> isOverWriteOpt;
	/** 上書き用の暫定年休管理データ */
	private Optional<List<TempAnnualLeaveManagement>> forOverWriteListOpt;
	/** 前回の年休の集計結果 */
	private Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt;
	/** 年休集計期間WORKリスト */
	private List<AggregatePeriodWork> aggregatePeriodWorks;
	/** 年休付与残数データリスト */
	private List<AnnualLeaveGrantRemaining> grantRemainingDatas;

	public GetAnnLeaRemNumWithinPeriodProc(
			AnnualPaidLeaveSettingRepository annualPaidLeaveSet,
			AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo,
			AnnLeaMaxDataRepository annLeaMaxDataRepo,
			GetClosureStartForEmployee getClosureStartForEmployee,
			CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantDate,
			CreateTempAnnualLeaveManagement createTempAnnualLeaveMng,
			GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod) {
		
		this.annualPaidLeaveSet = annualPaidLeaveSet;
		this.annLeaGrantRemDataRepo = annLeaGrantRemDataRepo;
		this.annLeaMaxDataRepo = annLeaMaxDataRepo;
		this.getClosureStartForEmployee = getClosureStartForEmployee;
		this.calcNextAnnualLeaveGrantDate = calcNextAnnualLeaveGrantDate;
		this.createTempAnnualLeaveMng = createTempAnnualLeaveMng;
		this.getAnnLeaRemNumWithinPeriod = getAnnLeaRemNumWithinPeriod;
	}

	/**
	 * 期間中の年休残数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode モード
	 * @param criteriaDate 基準日
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param isOverWriteOpt 上書きフラグ
	 * @param forOverWriteListOpt 上書き用の暫定年休管理データ
	 * @param prevAnnualLeaveOpt 前回の年休の集計結果
	 * @return 年休の集計結果
	 */
	public Optional<AggrResultOfAnnualLeave> algorithm(
			String companyId,
			String employeeId,
			DatePeriod aggrPeriod,
			TempAnnualLeaveMngMode mode,
			GeneralDate criteriaDate,
			boolean isGetNextMonthData,
			boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWriteOpt,
			Optional<List<TempAnnualLeaveManagement>> forOverWriteListOpt,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt) {
	
		return this.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate, isOverWriteOpt, forOverWriteListOpt,
				prevAnnualLeaveOpt, false, Optional.empty(), Optional.empty());
	}
	
	/**
	 * 期間中の年休残数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode モード
	 * @param criteriaDate 基準日
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param isOverWriteOpt 上書きフラグ
	 * @param forOverWriteListOpt 上書き用の暫定年休管理データ
	 * @param prevAnnualLeaveOpt 前回の年休の集計結果
	 * @param noCheckStartDate 集計開始日を締め開始日とする　（締め開始日を確認しない）
	 * @param companySets 月別集計で必要な会社別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @return 年休の集計結果
	 */
	public Optional<AggrResultOfAnnualLeave> algorithm(
			String companyId,
			String employeeId,
			DatePeriod aggrPeriod,
			TempAnnualLeaveMngMode mode,
			GeneralDate criteriaDate,
			boolean isGetNextMonthData,
			boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWriteOpt,
			Optional<List<TempAnnualLeaveManagement>> forOverWriteListOpt,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt,
			boolean noCheckStartDate,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.aggrPeriod = aggrPeriod;
		this.mode = mode;
		this.isGetNextMonthData = isGetNextMonthData;
		this.isCalcAttendanceRate = isCalcAttendanceRate;
		this.isOverWriteOpt = isOverWriteOpt;
		this.forOverWriteListOpt = forOverWriteListOpt;
		this.prevAnnualLeaveOpt = prevAnnualLeaveOpt;
		
		// 年休の使用区分を取得する
		boolean isManageAnnualLeave = false;
		AnnualPaidLeaveSetting annualLeaveSet = null;
		if (companySets.isPresent()){
			annualLeaveSet = companySets.get().getAnnualLeaveSet();
		}
		else {
			annualLeaveSet = this.annualPaidLeaveSet.findByCompanyId(companyId);
		}
		if (annualLeaveSet != null) isManageAnnualLeave = annualLeaveSet.isManaged();
		if (!isManageAnnualLeave) return Optional.empty();

		AggrResultOfAnnualLeave aggrResult = new AggrResultOfAnnualLeave();
		
		// 年休付与残数データ　取得
		if (monthlyCalcDailys.isPresent()){
			this.grantRemainingDatas = monthlyCalcDailys.get().getGrantRemainingDatas();
		}
		else {
			this.grantRemainingDatas =
					this.annLeaGrantRemDataRepo.findNotExp(employeeId).stream()
							.map(c -> new AnnualLeaveGrantRemaining(c)).collect(Collectors.toList());
		}
		
		// 集計開始日時点の年休情報を作成
		AnnualLeaveInfo annualLeaveInfo = this.createInfoAsOfPeriodStart(noCheckStartDate);
		
		// 次回年休付与日を計算
		GeneralDate calcEnd = aggrPeriod.end();
		if (calcEnd.before(GeneralDate.max())) calcEnd = calcEnd.addDays(1);
		val calcPeriod = new DatePeriod(aggrPeriod.start(), calcEnd);
		val nextAnnualLeaveGrantList = this.calcNextAnnualLeaveGrantDate.algorithm(
				companyId, employeeId, Optional.of(calcPeriod));
		
		// 年休集計期間を作成
		this.createAggregatePeriod(nextAnnualLeaveGrantList);
		
		// 暫定年休管理データを作成する
		val tempAnnualLeaveMngs = this.createTempAnnualLeaveMng.algorithm(companyId, employeeId, aggrPeriod, mode,
				companySets, monthlyCalcDailys);
		
		for (val aggregatePeriodWork : this.aggregatePeriodWorks){

			// 年休の消滅・付与・消化
			aggrResult = annualLeaveInfo.lapsedGrantDigest(companyId, employeeId, aggregatePeriodWork,
					tempAnnualLeaveMngs, isGetNextMonthData, isCalcAttendanceRate, aggrResult, annualLeaveSet);
		}
		
		// 年休不足分として作成した年休付与データを削除する
		aggrResult = this.deleteDummyRemainingDatas(aggrResult);
		
		// 「年休の集計結果」を返す
		return Optional.of(aggrResult);
	}
	
	/**
	 * 集計開始日時点の年休情報を作成
	 * @param noCheckStartDate 集計開始日を締め開始日とする　（締め開始日を確認しない）
	 * @return 年休情報
	 */
	private AnnualLeaveInfo createInfoAsOfPeriodStart(
			boolean noCheckStartDate){
	
		AnnualLeaveInfo emptyInfo = new AnnualLeaveInfo();
		emptyInfo.setYmd(this.aggrPeriod.start());
		
		// 「前回の年休情報」を確認　（前回の年休の集計結果．年休情報（期間終了日の翌日開始時点））
		AnnualLeaveInfo prevAnnualLeaveInfo = null;
		if (this.prevAnnualLeaveOpt.isPresent()){
			prevAnnualLeaveInfo = this.prevAnnualLeaveOpt.get().getAsOfStartNextDayOfPeriodEnd();
		}
		
		// 「開始日」と「年休情報．年月日」を比較
		boolean isSameInfo = false;
		if (prevAnnualLeaveInfo != null){
			if (this.aggrPeriod.start() == prevAnnualLeaveInfo.getYmd()){
				isSameInfo = true;
			}
		}
		if (isSameInfo){
			
			// 「前回の年休情報」を取得　→　取得内容をもとに年休情報を作成
			return this.createInfoFromRemainingData(
					prevAnnualLeaveInfo.getGrantRemainingList(), prevAnnualLeaveInfo.getMaxData());
		}
		
		// 「集計開始日を締め開始日とする」をチェック　（締め開始日としない時、締め開始日を確認する）
		boolean isAfterClosureStart = false;
		Optional<GeneralDate> closureStartOpt = Optional.empty();
		if (!noCheckStartDate){
			
			//　社員に対応する締め開始日を取得する
			closureStartOpt = this.getClosureStartForEmployee.algorithm(this.employeeId);
			if (closureStartOpt.isPresent()){
				
				// 締め開始日＜集計開始日　か確認する
				if (closureStartOpt.get().before(this.aggrPeriod.start())) isAfterClosureStart = true;
			}
		}
		
		if (isAfterClosureStart){
			// 締め開始日<集計開始日　の時
			
			// 開始日までの年休残数を計算　（締め開始日～集計開始日前日）
			val aggrResultOpt = this.getAnnLeaRemNumWithinPeriod.algorithm(
					this.companyId, this.employeeId,
					new DatePeriod(closureStartOpt.get(), this.aggrPeriod.start().addDays(-1)),
					this.mode,
					this.aggrPeriod.start().addDays(-1),
					this.isGetNextMonthData,
					this.isCalcAttendanceRate,
					this.isOverWriteOpt,
					this.forOverWriteListOpt,
					Optional.empty());
			if (!aggrResultOpt.isPresent()) return emptyInfo;
			val aggrResult = aggrResultOpt.get();
			
			// 年休情報（期間終了日の翌日開始時点）を取得
			val asOfPeriodEnd = aggrResult.getAsOfPeriodEnd();
			
			// 取得内容をもとに年休情報を作成
			return this.createInfoFromRemainingData(asOfPeriodEnd.getGrantRemainingList(),
					asOfPeriodEnd.getMaxData());
		}

		// 締め開始日>=集計開始日　or 締め開始日がnull　の時
		
		// 「年休上限データ」を取得
		val annLeaMaxDataOpt = this.annLeaMaxDataRepo.get(this.employeeId);
		if (!annLeaMaxDataOpt.isPresent()) return emptyInfo;
		val annLeaMaxData = annLeaMaxDataOpt.get();

		// 取得内容をもとに年休情報を作成
		return this.createInfoFromRemainingData(this.grantRemainingDatas, annLeaMaxData);
	}
	
	/**
	 * 年休付与残数データから年休情報を作成
	 * @param grantRemainingDataList 付与残数データリスト
	 * @param maxData 上限データ
	 * @return 年休情報
	 */
	private AnnualLeaveInfo createInfoFromRemainingData(
			List<AnnualLeaveGrantRemaining> grantRemainingDataList,
			AnnualLeaveMaxData maxData){
		
		AnnualLeaveInfo returnInfo = new AnnualLeaveInfo();
		returnInfo.setYmd(this.aggrPeriod.start());

		// 年休情報．年休付与情報　←　パラメータ「付与残数データ」
		List<AnnualLeaveGrantRemaining> targetDatas = new ArrayList<>();
		for (val grantRemainingData : grantRemainingDataList){
			if (grantRemainingData.getExpirationStatus() == LeaveExpirationStatus.EXPIRED) continue;
			targetDatas.add(grantRemainingData);
		}
		targetDatas.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
		returnInfo.setGrantRemainingList(targetDatas);
		
		// 年休情報残数を更新
		returnInfo.updateRemainingNumber();
		
		// 年休情報を返す
		return returnInfo;
	}
	
	/**
	 * 年休集計期間を作成
	 * @param nextAnnualLeaveGrantList 次回年休付与リスト
	 * @return 年休集計期間WORKリスト
	 */
	private void createAggregatePeriod(List<NextAnnualLeaveGrant> nextAnnualLeaveGrantList){
		
		this.aggregatePeriodWorks = new ArrayList<>();
		
		// 処理単位分割日リスト
		Map<GeneralDate, DividedDayEachProcess> dividedDayMap = new HashMap<>();
		
		// 期間終了日翌日
		GeneralDate nextDayOfPeriodEnd = this.aggrPeriod.end();
		if (nextDayOfPeriodEnd.before(GeneralDate.max())) nextDayOfPeriodEnd = nextDayOfPeriodEnd.addDays(1);
		
		// 「年休付与残数データ」を取得　（期限日　昇順、付与日　昇順）
		List<AnnualLeaveGrantRemainingData> remainingDatas = new ArrayList<>();
		remainingDatas.addAll(this.grantRemainingDatas);
		Collections.sort(remainingDatas, new Comparator<AnnualLeaveGrantRemainingData>() {
			@Override
			public int compare(AnnualLeaveGrantRemainingData o1, AnnualLeaveGrantRemainingData o2) {
				int compDeadline = o1.getDeadline().compareTo(o2.getDeadline());
				if (compDeadline != 0) return compDeadline;
				return o1.getGrantDate().compareTo(o2.getGrantDate());
			}
		});
		
		// 取得した「年休付与残数データ」をすべて「処理単位分割日リスト」に追加
		for (val remainingData : remainingDatas){
			val deadline = remainingData.getDeadline();
			if (!this.aggrPeriod.contains(deadline)) continue;
			
			val nextDayOfDeadline = deadline.addDays(1);
			dividedDayMap.putIfAbsent(nextDayOfDeadline, new DividedDayEachProcess(nextDayOfDeadline));
			dividedDayMap.get(nextDayOfDeadline).setLapsedAtr(true);
		}
		
		// 「次回年休付与リスト」をすべて「処理単位分割日リスト」に追加
		for (val nextAnnualLeaveGrant : nextAnnualLeaveGrantList){
			val grantDate = nextAnnualLeaveGrant.getGrantDate();
			if (grantDate.beforeOrEquals(this.aggrPeriod.start().addDays(1))) continue;
			if (grantDate.after(nextDayOfPeriodEnd)) continue;
			
			dividedDayMap.putIfAbsent(grantDate, new DividedDayEachProcess(grantDate));
			dividedDayMap.get(grantDate).setGrantAtr(true);
			dividedDayMap.get(grantDate).setNextAnnualLeaveGrant(Optional.of(nextAnnualLeaveGrant));
		}
		
		// 期間終了日翌日の「処理単位分割日」を取得・追加　→　フラグ設定
		dividedDayMap.putIfAbsent(nextDayOfPeriodEnd, new DividedDayEachProcess(nextDayOfPeriodEnd));
		dividedDayMap.get(nextDayOfPeriodEnd).setNextDayAfterPeriodEnd(true);
		
		// 「処理単位分割日」をソート
		List<DividedDayEachProcess> dividedDayList = new ArrayList<>();
		dividedDayList.addAll(dividedDayMap.values());
		dividedDayList.sort((a, b) -> a.getYmd().compareTo(b.getYmd()));
		
		// 「年休集計期間WORK」を作成
		AggregatePeriodWork startWork = new AggregatePeriodWork();
		val startWorkEnd = dividedDayList.get(0).getYmd().addDays(-1);
		startWork.setPeriod(new DatePeriod(this.aggrPeriod.start(), startWorkEnd));
		this.aggregatePeriodWorks.add(startWork);
		
		// 付与後フラグ
		boolean isAfterGrant = false;
		
		for (int index = 0; index < dividedDayList.size(); index++){
			val nowDividedDay = dividedDayList.get(index);
			DividedDayEachProcess nextDividedDay = null;
			if (index + 1 < dividedDayList.size()) nextDividedDay = dividedDayList.get(index + 1);
			
			// 付与フラグをチェック
			if (nowDividedDay.isGrantAtr()) isAfterGrant = true;
			
			// 年休集計期間WORKを作成し、Listに追加
			GeneralDate workPeriodEnd = nextDayOfPeriodEnd;
			if (nextDividedDay != null) workPeriodEnd = nextDividedDay.getYmd();
			AggregatePeriodWork nowWork = AggregatePeriodWork.of(
					new DatePeriod(nowDividedDay.getYmd(), workPeriodEnd),
					nowDividedDay.isNextDayAfterPeriodEnd(),
					nowDividedDay.isGrantAtr(),
					isAfterGrant,
					nowDividedDay.isLapsedAtr(),
					nowDividedDay.getNextAnnualLeaveGrant());
			this.aggregatePeriodWorks.add(nowWork);
		}
	}
	
	/**
	 * 年休不足分として作成した年休付与データを削除する
	 * @param result 年休の集計結果
	 */
	private AggrResultOfAnnualLeave deleteDummyRemainingDatas(AggrResultOfAnnualLeave result){
		
		// 期間終了日時点の不足分付与残数データを削除する
		val itrAsOfPeriodEndData = result.getAsOfPeriodEnd().getGrantRemainingList().listIterator();
		while (itrAsOfPeriodEndData.hasNext()){
			val remainData = itrAsOfPeriodEndData.next();
			if (remainData.isDummyAtr()) itrAsOfPeriodEndData.remove();
		}
		
		// 期間終了日の翌日開始時点の不足分付与残数データを削除する
		val itrAsOfEndNextData = result.getAsOfStartNextDayOfPeriodEnd().getGrantRemainingList().listIterator();
		while (itrAsOfEndNextData.hasNext()){
			val remainData = itrAsOfEndNextData.next();
			if (remainData.isDummyAtr()) itrAsOfEndNextData.remove();
		}
		
		// 付与時点の不足分付与残数データを削除する
		if (result.getAsOfGrant().isPresent()){
			for (val asOfGrant : result.getAsOfGrant().get()){
				val itrAsOfGrant = asOfGrant.getGrantRemainingList().listIterator();
				while (itrAsOfGrant.hasNext()){
					val remainData = itrAsOfGrant.next();
					if (remainData.isDummyAtr()) itrAsOfGrant.remove();
				}
			}
		}
		
		// 消滅時点の不足分付与残数データを削除する
		if (result.getLapsed().isPresent()){
			for (val lapsed : result.getLapsed().get()){
				val itrLapsed = lapsed.getGrantRemainingList().listIterator();
				while (itrLapsed.hasNext()){
					val remainData = itrLapsed.next();
					if (remainData.isDummyAtr()) itrLapsed.remove();
				}
			}
		}
		
		// 年休の集計結果を返す
		return result;
	}
}

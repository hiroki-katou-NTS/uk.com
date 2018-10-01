package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.GrantWork;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.MaxSettingPeriodWork;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.NextReserveLeaveGrant;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.RsvLeaAggrPeriodWork;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export.CalcDeadlineForGrantDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export.GetUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：期間中の積立年休残数を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetRsvLeaRemNumWithinPeriodImpl implements GetRsvLeaRemNumWithinPeriod {

	/** 年休設定 */
	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	/** 積立年休付与残数データ */
	@Inject
	private RervLeaGrantRemDataRepository rsvLeaGrantRemDataRepo;
	/** 社員に対応する締め開始日を取得する */
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmployee;
	/** 期間中の積立年休残数を取得する */
	@Inject
	private GetRsvLeaRemNumWithinPeriod getRsvLeaRemNumWithinPeriod;
	/** 月次処理用の暫定残数管理データを作成する */
	@Inject
	private InterimRemainOffMonthProcess interimRemOffMonth;
	/** 暫定残数管理データ */
	@Inject
	private InterimRemainRepository interimRemainRepo;
	/** 暫定積立年休管理データ */
	@Inject
	private TmpResereLeaveMngRepository tmpReserveLeaveMng;
	/** 社員の保持年数を取得 */
	@Inject
	private GetUpperLimitSetting getUpperLimitSetting;
	/** 付与日から期限日を計算 */
	@Inject
	private CalcDeadlineForGrantDate calcDeadlineForGrantDate;
	/** 締め状態管理 */
	@Inject
	private ClosureStatusManagementRepository closureSttMngRepo;
	/** 所属雇用履歴の取得 */
	@Inject
	private ShareEmploymentAdapter employmentAdapter;
	
	/** 期間中の積立年休残数を取得する */
	@Override
	public Optional<AggrResultOfReserveLeave> algorithm(GetRsvLeaRemNumWithinPeriodParam param) {
		return this.algorithm(param, Optional.empty(), Optional.empty());
	}
	
	/** 期間中の積立年休残数を取得する　（月別集計用） */
	@Override
	public Optional<AggrResultOfReserveLeave> algorithm(
			GetRsvLeaRemNumWithinPeriodParam param,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {

		String companyId = param.getCompanyId();
		String employeeId = param.getEmployeeId();
		
		// 年休の使用区分を取得する
		boolean isManageAnnualLeave = false;
		AnnualPaidLeaveSetting annualLeaveSet = null;
		Optional<RetentionYearlySetting> retentionYearlySet = Optional.empty();
		Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap = Optional.empty();
		if (companySets.isPresent()){
			annualLeaveSet = companySets.get().getAnnualLeaveSet();
			retentionYearlySet = companySets.get().getRetentionYearlySet();
			emptYearlyRetentionSetMap = Optional.of(companySets.get().getEmptYearlyRetentionSetMap());
		}
		else {
			annualLeaveSet = this.annualPaidLeaveSet.findByCompanyId(companyId);
		}
		if (annualLeaveSet != null) isManageAnnualLeave = annualLeaveSet.isManaged();
		if (!isManageAnnualLeave) return Optional.empty();
		
		AggrResultOfReserveLeave aggrResult = new AggrResultOfReserveLeave();

		// 積立年休付与残数データ　取得
		List<ReserveLeaveGrantRemaining> rsvGrantRemainingDatas = new ArrayList<>();
		if (monthlyCalcDailys.isPresent()){
			rsvGrantRemainingDatas = monthlyCalcDailys.get().getRsvGrantRemainingDatas();
		}
		else {
			rsvGrantRemainingDatas =
					this.rsvLeaGrantRemDataRepo.findNotExp(employeeId, null).stream()
							.map(c -> new ReserveLeaveGrantRemaining(c)).collect(Collectors.toList());
		}
		
		// 集計開始日時点の積立年休情報を作成
		ReserveLeaveInfo reserveLeaveInfo = this.createInfoAsOfPeriodStart(
				param, companySets, monthlyCalcDailys, rsvGrantRemainingDatas);
		
		// 上限設定の期間を計算
		List<MaxSettingPeriodWork> maxSetPeriods = this.calcMaxSettingPeriod(
				param, retentionYearlySet, emptYearlyRetentionSetMap);
		
		// 積立年休付与を計算
		List<GrantWork> calcGrant = this.calcGrant(param.getLapsedAnnualLeaveInfos(), annualLeaveSet, maxSetPeriods);
		
		// 積立年休集計期間の作成
		List<RsvLeaAggrPeriodWork> aggrPeriodWorks = this.createAggregatePeriod(
				param.getAggrPeriod(), calcGrant, maxSetPeriods, rsvGrantRemainingDatas);
		
		// 暫定積立年休管理データを取得する
		List<TmpReserveLeaveMngWork> tmpReserveLeaveMngs = this.getTmpReserveLeaveMngs(param);
		
		for (val aggrPeriodWork : aggrPeriodWorks){
			
			// 積立年休の消滅・付与・消化
			aggrResult = reserveLeaveInfo.lapsedGrantDigest(companyId, employeeId, aggrPeriodWork,
					tmpReserveLeaveMngs, param.isGetNextMonthData(), aggrResult,
					this.getUpperLimitSetting, this.calcDeadlineForGrantDate,
					annualLeaveSet, retentionYearlySet, emptYearlyRetentionSetMap);
		}
		
		// 積立年休不足分を付与残数データとして作成する　→　積立年休不足分として作成した積立年休付与を削除する
		aggrResult.getAsOfPeriodEnd().createShortageData(param.getIsOutputForShortage(), true);
		aggrResult.getAsOfStartNextDayOfPeriodEnd().createShortageData(param.getIsOutputForShortage(), false);
		if (aggrResult.getAsOfGrant().isPresent()){
			for (val asOfGrant : aggrResult.getAsOfGrant().get()){
				asOfGrant.createShortageData(param.getIsOutputForShortage(), false);
			}
		}
		if (aggrResult.getLapsed().isPresent()){
			for (val lapsed : aggrResult.getLapsed().get()){
				lapsed.createShortageData(param.getIsOutputForShortage(), false);
			}
		}
		
		// 「積立年休の集計結果」を返す
		return Optional.of(aggrResult);
	}
	
	/**
	 * 集計開始日時点の積立年休情報を作成
	 * @param param パラメータ
	 * @param companySets 月別集計で必要な会社別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @param rsvGrantRemainingDatas 積立年休付与残数データリスト
	 * @return 積立年休情報
	 */
	private ReserveLeaveInfo createInfoAsOfPeriodStart(
			GetRsvLeaRemNumWithinPeriodParam param,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys,
			List<ReserveLeaveGrantRemaining> rsvGrantRemainingDatas) {
		
		GeneralDate aggrStart = param.getAggrPeriod().start();		// 集計開始日
		
		ReserveLeaveInfo emptyInfo = new ReserveLeaveInfo();
		emptyInfo.setYmd(aggrStart);
		
		// 「前回の積立年休情報」を確認　（前回の積立年休の集計結果．積立年休情報（期間終了日の翌日開始時点））
		ReserveLeaveInfo prevReserveLeaveInfo = null;
		if (param.getPrevReserveLeave().isPresent()){
			prevReserveLeaveInfo = param.getPrevReserveLeave().get().getAsOfStartNextDayOfPeriodEnd();
		}
		
		// 「開始日」と「年休情報．年月日」を比較
		boolean isSameInfo = false;
		if (prevReserveLeaveInfo != null){
			if (aggrStart == prevReserveLeaveInfo.getYmd()){
				isSameInfo = true;
			}
		}
		if (isSameInfo){
			
			// 「前回の積立年休情報」を取得　→　取得内容をもとに積立年休情報を作成
			return this.createInfoFromRemainingData(aggrStart, prevReserveLeaveInfo.getGrantRemainingList());
		}
		
		// 「集計開始日を締め開始日とする」をチェック　（締め開始日としない時、締め開始日を確認する）
		boolean isAfterClosureStart = false;
		Optional<GeneralDate> closureStartOpt = Optional.empty();
		boolean noCheckStartDate = false;
		if (param.getIsNoCheckStartDate().isPresent()) noCheckStartDate = param.getIsNoCheckStartDate().get();
		if (!noCheckStartDate){

			// 休暇残数を計算する締め開始日を取得する
			GeneralDate closureStart = null;	// 締め開始日
			{
				// 最新の締め終了日翌日を取得する
				Optional<ClosureStatusManagement> sttMng = this.closureSttMngRepo.getLatestByEmpId(param.getEmployeeId());
				if (sttMng.isPresent()){
					closureStart = sttMng.get().getPeriod().end().addDays(1);
					closureStartOpt = Optional.of(closureStart);
				}
				else {
					
					//　社員に対応する締め開始日を取得する
					closureStartOpt = this.getClosureStartForEmployee.algorithm(param.getEmployeeId());
					if (closureStartOpt.isPresent()) closureStart = closureStartOpt.get();
				}
			}
			
			// 取得した締め開始日と「集計開始日」を比較
			if (closureStart != null){
				
				// 締め開始日＜集計開始日　か確認する
				if (closureStart.before(aggrStart)) isAfterClosureStart = true;
			}
		}
		
		if (isAfterClosureStart){
			// 締め開始日<集計開始日　の時
			
			// 開始日までの積立年休残数を計算　（締め開始日～集計開始日前日）
			val aggrResultOpt = this.getRsvLeaRemNumWithinPeriod.algorithm(
					new GetRsvLeaRemNumWithinPeriodParam(
							param.getCompanyId(),
							param.getEmployeeId(),
							new DatePeriod(closureStartOpt.get(), aggrStart.addDays(-1)),
							param.getMode(),
							aggrStart.addDays(-1),
							param.isGetNextMonthData(),
							param.getLapsedAnnualLeaveInfos(),
							param.getIsOverWrite(),
							param.getForOverWriteList(),
							Optional.of(false),
							Optional.of(true),
							Optional.empty()),
					companySets,
					monthlyCalcDailys);
			if (!aggrResultOpt.isPresent()) return emptyInfo;
			val aggrResult = aggrResultOpt.get();
			
			// 積立年休情報（期間終了日の翌日開始時点）を取得
			val asOfPeriodEnd = aggrResult.getAsOfPeriodEnd();
			
			// 取得内容をもとに積立年休情報を作成
			return this.createInfoFromRemainingData(aggrStart, asOfPeriodEnd.getGrantRemainingList());
		}

		// 締め開始日>=集計開始日　or 締め開始日がnull　の時

		// 「積立年休付与残数データ」を取得
		List<ReserveLeaveGrantRemaining> beforeClosureDatas = new ArrayList<>();
		GeneralDate closureStart = aggrStart;
		if (closureStartOpt.isPresent()) closureStart = closureStartOpt.get();
		for (val rsvGrantRemainingData : rsvGrantRemainingDatas){
			if (rsvGrantRemainingData.getExpirationStatus() == LeaveExpirationStatus.EXPIRED) continue;
			if (rsvGrantRemainingData.getGrantDate().after(closureStart)) continue;
			if (rsvGrantRemainingData.getDeadline().before(closureStart)) continue;
			beforeClosureDatas.add(rsvGrantRemainingData);
		}
		
		// 取得内容をもとに年休情報を作成
		return this.createInfoFromRemainingData(aggrStart, beforeClosureDatas);
	}
	
	/**
	 * 積立年休付与残数データから積立年休情報を作成
	 * @param ymd 年月日
	 * @param grantRemainingDataList 付与残数データリスト
	 * @return 積立年休情報
	 */
	private ReserveLeaveInfo createInfoFromRemainingData(
			GeneralDate ymd,
			List<ReserveLeaveGrantRemaining> grantRemainingDataList){
		
		ReserveLeaveInfo returnInfo = new ReserveLeaveInfo();
		returnInfo.setYmd(ymd);

		// 積立年休情報．積立年休付与情報　←　パラメータ「積立年休付与残数データ」
		List<ReserveLeaveGrantRemaining> targetDatas = new ArrayList<>();
		for (val grantRemainingData : grantRemainingDataList){
			if (grantRemainingData.getExpirationStatus() == LeaveExpirationStatus.EXPIRED) continue;
			targetDatas.add(grantRemainingData);
		}
		targetDatas.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
		returnInfo.setGrantRemainingList(targetDatas);
		
		// 積立年休情報残数を更新
		returnInfo.updateRemainingNumber();
		
		// 積立年休情報を返す
		return returnInfo;
	}
	
	/**
	 * 上限設定の期間を計算
	 * @param param パラメータ
	 * @param retentionYearlySet 積立年休設定
	 * @param emptYearlyRetentionSetMap 雇用積立年休設定マップ
	 * @return 積立年休上限設定期間WORKリスト
	 */
	private List<MaxSettingPeriodWork> calcMaxSettingPeriod(
			GetRsvLeaRemNumWithinPeriodParam param,
			Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap) {

		List<MaxSettingPeriodWork> results = new ArrayList<>();
		
		// 「集計開始日」を取得　→　上限チェック年月日
		GeneralDate checkYmd = param.getAggrPeriod().start();
		
		// 「上限チェック年月日」が集計期間内かチェック
		GeneralDate checkEnd = param.getAggrPeriod().end().addDays(1);
		while (checkYmd.beforeOrEquals(checkEnd)){
			
			// 「所属雇用履歴」を取得
			val employmentOpt = this.employmentAdapter.findEmploymentHistory(
					param.getCompanyId(), param.getEmployeeId(), checkYmd);
			if (!employmentOpt.isPresent()) break;
			val employment = employmentOpt.get();
			
			// 積立年休の上限設定を取得
			val upperLimitSet = this.getUpperLimitSetting.algorithm(param.getCompanyId(), param.getEmployeeId(),
					checkYmd, retentionYearlySet, emptYearlyRetentionSetMap);
			
			// 積立年休上限設定WORKの期間を設定
			MaxSettingPeriodWork maxSettingPeriodWork = MaxSettingPeriodWork.of(
					new DatePeriod(checkYmd, employment.getPeriod().end()), upperLimitSet);

			// 積立年休上限設定WORKリストに追加
			results.add(maxSettingPeriodWork);
			
			// 上限チェック年月日　←　雇用履歴．期間．終了日+1
			checkYmd = employment.getPeriod().end();
			if (checkYmd.afterOrEquals(GeneralDate.max())) break;
			checkYmd = checkYmd.addDays(1);
		}
		
		// 積立年休上限設定期間WORKリストを返す
		return results;
	}
	
	/**
	 * 積立年休付与を計算
	 * @param lapsedAnnualLeaveInfos 年休付与消滅時リスト
	 * @param annualLeaveSet 年休設定
	 * @param maxSetPeriods 積立年休上限設定期間WORKリスト
	 * @return 積立年休付与WORKリスト
	 */
	private List<GrantWork> calcGrant(
			List<AnnualLeaveInfo> lapsedAnnualLeaveInfos,
			AnnualPaidLeaveSetting annualLeaveSet,
			List<MaxSettingPeriodWork> maxSetPeriods){
	
		List<GrantWork> results = new ArrayList<>();
		
		for (val annualLeaveInfo : lapsedAnnualLeaveInfos){
			
			// 対象の上限設定期間WORKを取得
			MaxSettingPeriodWork targetMaxSet = null;
			for (val maxSetPeriod : maxSetPeriods){
				if (!maxSetPeriod.getPeriod().contains(annualLeaveInfo.getYmd())) continue;
				targetMaxSet = maxSetPeriod;
				break;
			}
			
			// 上限日数をチェック
			if (targetMaxSet == null) continue;
			if (targetMaxSet.getMaxSetting().getMaxDaysCumulation().v() == 0) continue;
			
			// 1回分の積立年休付与を計算
			{
				// 付与日数
				double grantDays = 0.0;
				
				// 付与残数データを取得
				for (val grantRemaining : annualLeaveInfo.getGrantRemainingList()){
					if (grantRemaining.getDeadline().compareTo(annualLeaveInfo.getYmd().addDays(-1)) != 0) continue;
					if (grantRemaining.getExpirationStatus() != LeaveExpirationStatus.EXPIRED) continue;
					
					// 付与日数に年休情報の残日数を加算
					grantDays += grantRemaining.getDetails().getRemainingNumber().getDays().v();
				}
				
				// 積立年休付与WORKを作成　→　端数処理
				GrantWork grantWork = GrantWork.of(annualLeaveInfo.getYmd(), new ReserveLeaveGrantDayNumber(grantDays));
				grantWork.roundGrantDays(annualLeaveSet);
				
				// 積立年休付与WORKを返す
				results.add(grantWork);
			}
		}
		
		return results;
	}
	
	/**
	 * 積立年休集計期間を作成
	 * @param period 期間
	 * @param nextReserveLeaveGrantList 次回積立年休付与リスト
	 * @param maxSetPeriods 積立年休上限設定期間WORKリスト
	 * @param grantRemainingDataList 付与残数データリスト
	 * @return 積立年休集計期間WORKリスト
	 */
	private List<RsvLeaAggrPeriodWork> createAggregatePeriod(
			DatePeriod period,
			List<GrantWork> nextReserveLeaveGrantList,
			List<MaxSettingPeriodWork> maxSetPeriods,
			List<ReserveLeaveGrantRemaining> grantRemainingDataList){
		
		List<RsvLeaAggrPeriodWork> results = new ArrayList<>();
		
		// 処理単位分割日リスト
		Map<GeneralDate, RsvLeaDividedDay> dividedDayMap = new HashMap<>();
		
		// 期間終了日翌日
		GeneralDate nextDayOfPeriodEnd = period.end();
		if (nextDayOfPeriodEnd.before(GeneralDate.max())) nextDayOfPeriodEnd = nextDayOfPeriodEnd.addDays(1);
		
		// 「積立年休付与残数データ」を取得　（期限日　昇順、付与日　昇順）
		List<ReserveLeaveGrantRemaining> remainingDatas = new ArrayList<>();
		for (val grantRemainingData : grantRemainingDataList){
			if (!period.contains(grantRemainingData.getDeadline())) continue;
			if (grantRemainingData.getExpirationStatus() != LeaveExpirationStatus.AVAILABLE) continue;
			remainingDatas.add(grantRemainingData);
		}
		Collections.sort(remainingDatas, new Comparator<ReserveLeaveGrantRemaining>() {
			@Override
			public int compare(ReserveLeaveGrantRemaining o1, ReserveLeaveGrantRemaining o2) {
				int compDeadline = o1.getDeadline().compareTo(o2.getDeadline());
				if (compDeadline != 0) return compDeadline;
				return o1.getGrantDate().compareTo(o2.getGrantDate());
			}
		});
		
		// 取得した「積立年休付与残数データ」をすべて「処理単位分割日リスト」に追加
		for (val remainingData : remainingDatas){
			val nextDayOfDeadline = remainingData.getDeadline().addDays(1);
			dividedDayMap.putIfAbsent(nextDayOfDeadline, new RsvLeaDividedDay(nextDayOfDeadline));
			dividedDayMap.get(nextDayOfDeadline).setLapsedAtr(true);
		}
		
		// 「次回積立年休付与リスト」を「処理単位分割日リスト」に追加
		for (val nextReserveLeaveGrant : nextReserveLeaveGrantList){
			val grantDate = nextReserveLeaveGrant.getGrantYmd();
			if (grantDate.beforeOrEquals(period.start().addDays(1))) continue;
			if (grantDate.after(nextDayOfPeriodEnd)) continue;
			
			if (dividedDayMap.containsKey(grantDate)){
				dividedDayMap.get(grantDate).setGrantAtr(true);
				continue;
			}
			dividedDayMap.putIfAbsent(grantDate, new RsvLeaDividedDay(grantDate));
			dividedDayMap.get(grantDate).setGrantAtr(true);
			dividedDayMap.get(grantDate).setNextReserveLeaveGrant(Optional.of(NextReserveLeaveGrant.of(
					nextReserveLeaveGrant.getGrantYmd(),
					nextReserveLeaveGrant.getGrantDays(),
					GeneralDate.max())));
		}
		
		// 「積立年休上限設定期間WORKリスト」を全て「処理単位分割日リスト」に追加
		for (val maxSetPeriod : maxSetPeriods){
			val maxSetStart = maxSetPeriod.getPeriod().start();
			dividedDayMap.putIfAbsent(maxSetStart, new RsvLeaDividedDay(maxSetStart));
		}
		
		// 期間終了日翌日の「処理単位分割日」を取得・追加　→　フラグ設定
		dividedDayMap.putIfAbsent(nextDayOfPeriodEnd, new RsvLeaDividedDay(nextDayOfPeriodEnd));
		dividedDayMap.get(nextDayOfPeriodEnd).setNextDayAfterPeriodEnd(true);
		
		// 「処理単位分割日」をソート
		List<RsvLeaDividedDay> dividedDayList = new ArrayList<>();
		dividedDayList.addAll(dividedDayMap.values());
		dividedDayList.sort((a, b) -> a.getYmd().compareTo(b.getYmd()));
		
		// 「積立年休集計期間WORK」を作成
		if (dividedDayList.get(0).getYmd().after(period.start())){
			RsvLeaAggrPeriodWork startWork = new RsvLeaAggrPeriodWork();
			val startWorkEnd = dividedDayList.get(0).getYmd().addDays(-1);
			startWork.setPeriod(new DatePeriod(period.start(), startWorkEnd));
			results.add(startWork);
		}
		
		// 付与後フラグ
		boolean isAfterGrant = false;
		
		for (int index = 0; index < dividedDayList.size(); index++){
			val nowDividedDay = dividedDayList.get(index);
			RsvLeaDividedDay nextDividedDay = null;
			if (index + 1 < dividedDayList.size()) nextDividedDay = dividedDayList.get(index + 1);
			
			// 付与フラグをチェック
			if (nowDividedDay.isGrantAtr()) isAfterGrant = true;
			
			// 集計期間をチェック
			GeneralDate workPeriodEnd = nextDayOfPeriodEnd;
			if (nextDividedDay != null) workPeriodEnd = nextDividedDay.getYmd().addDays(-1);
			DatePeriod workPeriod = new DatePeriod(nowDividedDay.getYmd(), workPeriodEnd);
			
			// 上限日数をチェック
			MaxDaysRetention maxDays = new MaxDaysRetention(0);
			for (val maxSetPeriod : maxSetPeriods){
				if (maxSetPeriod.getPeriod().contains(workPeriod.start())){
					maxDays = maxSetPeriod.getMaxSetting().getMaxDaysCumulation();
					break;
				}
			}
			
			// 積立年休集計期間WORKを作成し、Listに追加
			RsvLeaAggrPeriodWork nowWork = RsvLeaAggrPeriodWork.of(
					workPeriod,
					nowDividedDay.isNextDayAfterPeriodEnd(),
					nowDividedDay.isGrantAtr(),
					isAfterGrant,
					nowDividedDay.isLapsedAtr(),
					maxDays,
					nowDividedDay.getNextReserveLeaveGrant());
			results.add(nowWork);
		}
		
		// 積立年休集計期間WORKリストを返す
		return results;
	}
	
	/**
	 * 暫定積立年休管理データを取得する
	 * @param param パラメータ
	 * @return 暫定積立年休管理データWORKリスト
	 */
	private List<TmpReserveLeaveMngWork> getTmpReserveLeaveMngs(GetRsvLeaRemNumWithinPeriodParam param){
		
		List<TmpReserveLeaveMngWork> results = new ArrayList<>();
		
		// 「モード」をチェック
		if (param.getMode() == TempAnnualLeaveMngMode.MONTHLY){
			// 月次モード
			
			// 「月次処理用の暫定残数管理データを作成する」を実行する
			val dailyInterimRemainMngDataMap = this.interimRemOffMonth.monthInterimRemainData(
					param.getCompanyId(), param.getEmployeeId(), param.getAggrPeriod());
			
			// 受け取った「日別暫定管理データ」を積立年休のみに絞り込む
			for (val dailyInterimRemainMngData : dailyInterimRemainMngDataMap.values()){
				if (!dailyInterimRemainMngData.getResereData().isPresent()) continue;
				if (dailyInterimRemainMngData.getRecAbsData().size() <= 0) continue;
				val master = dailyInterimRemainMngData.getRecAbsData().get(0);
				val data = dailyInterimRemainMngData.getResereData().get();
				results.add(TmpReserveLeaveMngWork.of(master, data));
			}
		}
		if (param.getMode() == TempAnnualLeaveMngMode.OTHER){
			// その他モード
			
			// 「暫定積立年休管理データ」を取得する
			val interimRemains = this.interimRemainRepo.getRemainBySidPriod(
					param.getEmployeeId(), param.getAggrPeriod(), RemainType.FUNDINGANNUAL);
			for (val interimRemain : interimRemains){
				val tmpReserveLeaveMngOpt = this.tmpReserveLeaveMng.getById(interimRemain.getRemainManaID());
				if (!tmpReserveLeaveMngOpt.isPresent()) continue;
				val tmpReserveLeaveMng = tmpReserveLeaveMngOpt.get();
				results.add(TmpReserveLeaveMngWork.of(interimRemain, tmpReserveLeaveMng));
			}
		}
		
		// 「上書きフラグ」をチェック
		if (param.getIsOverWrite().isPresent()){
			if (param.getIsOverWrite().get()){
				
				// 上書き用データがある時、使用する
				if (param.getForOverWriteList().isPresent()){
					val overWrites = param.getForOverWriteList().get();
					for (val overWrite : overWrites){
						// 重複データを削除
						ListIterator<TmpReserveLeaveMngWork> itrResult = results.listIterator();
						while (itrResult.hasNext()){
							TmpReserveLeaveMngWork target = itrResult.next();
							if (target.equals(overWrite)) itrResult.remove();
						}
						// 上書き用データを追加
						results.add(overWrite);
					}
				}
			}
		}
		
		results.sort((a, b) -> a.getYmd().compareTo(b.getYmd()));
		return results;
	}
}

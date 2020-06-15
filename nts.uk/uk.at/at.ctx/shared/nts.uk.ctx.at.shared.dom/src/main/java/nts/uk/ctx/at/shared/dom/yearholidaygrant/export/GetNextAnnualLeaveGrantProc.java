package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RepositoriesRequiredByRemNum;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.UseSimultaneousGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 処理：次回年休付与を取得する
 * @author shuichi_ishida
 */
public class GetNextAnnualLeaveGrantProc {

	/** 年休付与テーブル設定 */
	private YearHolidayRepository yearHolidayRepo;
	/** 勤続年数テーブル */
	private LengthServiceRepository lengthServiceRepo;
	/** 年休付与テーブル */
	private GrantYearHolidayRepository grantYearHolidayRepo;
	/** 次回年休付与を取得する(複数社員用) */
	private GetNextAnnualLeaveGrantProcKdm002 getNextAnnualLeaveGrantProcMulti;
	
	/** 一斉付与日 */
	private Optional<Integer> simultaneousGrantMDOpt;
	/** 次回年休付与リスト */
	private List<NextAnnualLeaveGrant> nextAnnualLeaveGrantList;
	/** 勤続年数テーブルリスト */
	private List<LengthServiceTbl> lengthServiceTbls;
	
	public GetNextAnnualLeaveGrantProc(
			YearHolidayRepository yearHolidayRepo,
			LengthServiceRepository lengthServiceRepo,
			GrantYearHolidayRepository grantYearHolidayRepo,
			GetNextAnnualLeaveGrantProcKdm002 getNextAnnualLeaveGrantProcMulti) {
		
		this.yearHolidayRepo = yearHolidayRepo;
		this.lengthServiceRepo = lengthServiceRepo;
		this.grantYearHolidayRepo = grantYearHolidayRepo;
		this.getNextAnnualLeaveGrantProcMulti = getNextAnnualLeaveGrantProcMulti;
	}
	
	/**
	 * 次回年休付与を取得する
	 * @param companyId 会社ID
	 * @param grantTableCode 年休付与テーブル設定コード
	 * @param entryDate 入社年月日
	 * @param criteriaDate 年休付与基準日
	 * @param period 期間
	 * @param simultaneousGrantDateOpt 一斉付与日
	 * @param isSingleDay 単一日フラグ
	 * @return 次回年休付与リスト
	 */
	public List<NextAnnualLeaveGrant> algorithm(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNumOpt,
			String companyId,
			String grantTableCode,
			GeneralDate entryDate,
			GeneralDate criteriaDate,
			DatePeriod period,
			boolean isSingleDay){

		return this.algorithm(
				repositoriesRequiredByRemNumOpt, companyId, grantTableCode, 
				entryDate, criteriaDate, period, isSingleDay,
				Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	/**
	 * 次回年休付与を取得する （※付与年月日、期限日をセットするだけに変更）
	 * @param repositoriesRequiredByRemNum ロードデータ（キャッシュ用）
	 * @param companyId 会社ID
	 * @param grantTableCode 年休付与テーブル設定コード
	 * @param entryDate 入社年月日
	 * @param criteriaDate 年休付与基準日
	 * @param period 期間
	 * @param simultaneousGrantDateOpt 一斉付与日
	 * @param isSingleDay 単一日フラグ
	 * @param grantHdTblSetParam 年休付与テーブル設定
	 * @param lengthServiceTblsParam 勤続年数テーブルリスト
	 * @param closureStartDate 締め開始日
	 * @return 次回年休付与リスト
	 */
	public List<NextAnnualLeaveGrant> algorithm(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNumOpt,
			String companyId,
			String grantTableCode,
			GeneralDate entryDate,
			GeneralDate criteriaDate,
			DatePeriod period,
			boolean isSingleDay,
			Optional<GrantHdTblSet> grantHdTblSetParam,
			Optional<List<LengthServiceTbl>> lengthServiceTblsParam,
			Optional<GeneralDate> closureStartDate){
		
		this.nextAnnualLeaveGrantList = new ArrayList<>();
		
		boolean isPeriodNull = false;
		
//		パラメータ「期間」がNULLの場合
//		期間．開始日←取得した「締め開始日」の翌日
//		期間．終了日←取得した「締め開始日」の翌日の2年後
//
//		※パラメータ「期間.終了日」がNULLの場合
//		期間．終了日←パラメータ「期間.開始日」の2年後
		
		// ooooo 期間がNullかつ締め日がNullのときはどうするか？

		if ( period.start() == null && period.end() == null ){
			isPeriodNull = true;
			
			if ( closureStartDate.isPresent() ){
				period = new DatePeriod(
					closureStartDate.get().addDays(1)
					, closureStartDate.get().addDays(1).addYears(2));
			}
		}
		// パラメータ「期間.終了日」がNULLの場合
		else if ( period.end() == null ){ 
			isPeriodNull = true;
			
			period = new DatePeriod(
				period.start()
				, period.start().addYears(2));
		}
		
//		// 「年休付与テーブル設定」を取得する
//		Optional<GrantHdTblSet> grantHdTblSetOpt = Optional.empty();
//		if (grantHdTblSetParam.isPresent()){
//			grantHdTblSetOpt = grantHdTblSetParam;
//		}
//		else {
//			grantHdTblSetOpt = this.yearHolidayRepo.findByCode(companyId, grantTableCode);
//		}
//		if (!grantHdTblSetOpt.isPresent()) return nextAnnualLeaveGrantList;
//		val grantHdTblSet = grantHdTblSetOpt.get();
//
//		// 一斉付与日　確認
//		this.simultaneousGrantMDOpt = Optional.empty();
//		if (grantHdTblSet.getUseSimultaneousGrant() == UseSimultaneousGrant.USE){
//			this.simultaneousGrantMDOpt = Optional.of(grantHdTblSet.getSimultaneousGrandMonthDays());
//		}
//		
		
		// 「勤続年数テーブル」を取得する
		if (lengthServiceTblsParam.isPresent()){
			this.lengthServiceTbls = lengthServiceTblsParam.get();
		}
		else {
			this.lengthServiceTbls = this.lengthServiceRepo.findByCode(companyId, grantTableCode);
		}
		if (this.lengthServiceTbls.size() <= 0) return nextAnnualLeaveGrantList;
		
		// 年休付与年月日を計算
		if ( this.getNextAnnualLeaveGrantProcMulti != null){
			this.getNextAnnualLeaveGrantProcMulti.calcAnnualLeaveGrantDate(
					entryDate, criteriaDate, this.simultaneousGrantMDOpt, this.lengthServiceTbls,
					period, isSingleDay, this.nextAnnualLeaveGrantList);
		}
//		for (val nextAnnualLeaveGrant : this.nextAnnualLeaveGrantList){
//			
//			// 付与回数をもとに年休付与テーブルを取得
//			val grantTimes = nextAnnualLeaveGrant.getTimes().v();
//			val grantHdTblOpt = this.grantYearHolidayRepo.find(companyId, 1, grantTableCode, grantTimes);
//			
//			// 次回年休付与に付与日数・半日年休上限回数・時間年休上限日数をセット
//			if (!grantHdTblOpt.isPresent()) continue;
//			val grantHdTbl = grantHdTblOpt.get();
//			nextAnnualLeaveGrant.setGrantDays(grantHdTbl.getGrantDays());
//			nextAnnualLeaveGrant.setHalfDayAnnualLeaveMaxTimes(grantHdTbl.getLimitDayYear());
//			nextAnnualLeaveGrant.setTimeAnnualLeaveMaxDays(grantHdTbl.getLimitTimeHd());
//		}
		
		// 期間がNULLであった場合は取得した付与年月日の最初の1件にする
		if ( isPeriodNull ){
			
			if ( 0 < nextAnnualLeaveGrantList.size() ){
				// ソート　ASC 付与年月日
				nextAnnualLeaveGrantList.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
				NextAnnualLeaveGrant aNextAnnualLeaveGrant = nextAnnualLeaveGrantList.get(0);
				
				// 最初の1件にする
				this.nextAnnualLeaveGrantList.clear();
				this.nextAnnualLeaveGrantList.add(aNextAnnualLeaveGrant);
			}
		}
		
		// 年休設定
		AnnualPaidLeaveSetting annualPaidLeaveSet;
		if ( !repositoriesRequiredByRemNumOpt.isPresent() ){
			repositoriesRequiredByRemNumOpt = Optional.of(new RepositoriesRequiredByRemNum());
		}
		annualPaidLeaveSet = repositoriesRequiredByRemNumOpt.get().getAnnualPaidLeaveSet();
		
		for (val nextAnnualLeaveGrant : this.nextAnnualLeaveGrantList){
			
			// 付与日から期限日を計算
			val deadLine = annualPaidLeaveSet.calcDeadline(
					nextAnnualLeaveGrant.getGrantDate());
			
			// 期限日をセットする
			nextAnnualLeaveGrant.setDeadLine(deadLine);
		}
		
//		val annualLeaveGrant = aggregatePeriodWork.getAnnualLeaveGrant().get();
//		val grantDate = annualLeaveGrant.getGrantDate();
//		val deadline = this.annualPaidLeaveSet.calcDeadline(grantDate);
		
		// 次回年休付与を返す
		return this.nextAnnualLeaveGrantList;
	}
}

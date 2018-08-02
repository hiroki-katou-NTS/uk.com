package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantReferenceDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantSimultaneity;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.UseSimultaneousGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 処理：次回年休付与を取得する
 * @author shuichu_ishida
 */
public class GetNextAnnualLeaveGrantProc {

	/** 年休付与テーブル設定 */
	private YearHolidayRepository yearHolidayRepo;
	/** 勤続年数テーブル */
	private LengthServiceRepository lengthServiceRepo;
	/** 年休付与テーブル */
	private GrantYearHolidayRepository grantYearHolidayRepo;
	
	/** 入社年月日 */
	private GeneralDate entryDate;
	/** 年休付与基準日 */
	private GeneralDate criteriaDate;
	/** 期間 */
	private DatePeriod period;
	/** 一斉付与日 */
	private Optional<Integer> simultaneousGrantMDOpt;
	/** 単一日フラグ */
	private boolean isSingleDay;
	/** 次回年休付与リスト */
	private List<NextAnnualLeaveGrant> nextAnnualLeaveGrantList;
	/** 勤続年数テーブルリスト */
	private List<LengthServiceTbl> lengthServiceTbls;
	/** 前回付与日 */
	private GeneralDate previousDate;
	
	public GetNextAnnualLeaveGrantProc(
			YearHolidayRepository yearHolidayRepo,
			LengthServiceRepository lengthServiceRepo,
			GrantYearHolidayRepository grantYearHolidayRepo) {
		
		this.yearHolidayRepo = yearHolidayRepo;
		this.lengthServiceRepo = lengthServiceRepo;
		this.grantYearHolidayRepo = grantYearHolidayRepo;
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
			String companyId,
			String grantTableCode,
			GeneralDate entryDate,
			GeneralDate criteriaDate,
			DatePeriod period,
			boolean isSingleDay){

		return this.algorithm(companyId, grantTableCode, entryDate, criteriaDate, period, isSingleDay,
				Optional.empty(), Optional.empty());
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
	 * @param grantHdTblSetParam 年休付与テーブル設定
	 * @param lengthServiceTblsParam 勤続年数テーブルリスト
	 * @return 次回年休付与リスト
	 */
	public List<NextAnnualLeaveGrant> algorithm(
			String companyId,
			String grantTableCode,
			GeneralDate entryDate,
			GeneralDate criteriaDate,
			DatePeriod period,
			boolean isSingleDay,
			Optional<GrantHdTblSet> grantHdTblSetParam,
			Optional<List<LengthServiceTbl>> lengthServiceTblsParam){
		
		this.nextAnnualLeaveGrantList = new ArrayList<>();
		
		this.entryDate = entryDate;
		this.criteriaDate = criteriaDate;
		this.period = period;
		this.isSingleDay = isSingleDay;
		
		// 「年休付与テーブル設定」を取得する
		Optional<GrantHdTblSet> grantHdTblSetOpt = Optional.empty();
		if (grantHdTblSetParam.isPresent()){
			grantHdTblSetOpt = grantHdTblSetParam;
		}
		else {
			grantHdTblSetOpt = this.yearHolidayRepo.findByCode(companyId, grantTableCode);
		}
		if (!grantHdTblSetOpt.isPresent()) return nextAnnualLeaveGrantList;
		val grantHdTblSet = grantHdTblSetOpt.get();

		// 一斉付与日　確認
		this.simultaneousGrantMDOpt = Optional.empty();
		if (grantHdTblSet.getUseSimultaneousGrant() == UseSimultaneousGrant.USE){
			this.simultaneousGrantMDOpt = Optional.of(grantHdTblSet.getSimultaneousGrandMonthDays());
		}
		
		// 「勤続年数テーブル」を取得する
		if (lengthServiceTblsParam.isPresent()){
			this.lengthServiceTbls = lengthServiceTblsParam.get();
		}
		else {
			this.lengthServiceTbls = this.lengthServiceRepo.findByCode(companyId, grantTableCode);
		}
		if (this.lengthServiceTbls.size() <= 0) return nextAnnualLeaveGrantList;
		
		// 年休付与年月日を計算
		this.calcAnnualLeaveGrantDate();
		for (val nextAnnualLeaveGrant : this.nextAnnualLeaveGrantList){
			
			// 付与回数をもとに年休付与テーブルを取得
			val grantTimes = nextAnnualLeaveGrant.getTimes().v();
			val grantHdTblOpt = this.grantYearHolidayRepo.find(companyId, 1, grantTableCode, grantTimes);
			
			// 次回年休付与に付与日数・半日年休上限回数・時間年休上限日数をセット
			if (!grantHdTblOpt.isPresent()) continue;
			val grantHdTbl = grantHdTblOpt.get();
			nextAnnualLeaveGrant.setGrantDays(grantHdTbl.getGrantDays());
			nextAnnualLeaveGrant.setHalfDayAnnualLeaveMaxTimes(grantHdTbl.getLimitDayYear());
			nextAnnualLeaveGrant.setTimeAnnualLeaveMaxDays(grantHdTbl.getLimitTimeHd());
		}
		
		// 次回年休付与を返す
		return this.nextAnnualLeaveGrantList;
	}
	
	/**
	 * 年休付与年月日を計算
	 */
	private void calcAnnualLeaveGrantDate(){
		
		this.previousDate = null;
		for (val lengthServiceTbl : this.lengthServiceTbls){
			
			// 勤続年数から付与日を計算
			val nextAnnualLeaveGrant = this.calcGrantDateFromLengthService(lengthServiceTbl, Optional.empty());
			
			// 前回付与日　←　次回年休付与．付与年月日
			if (this.previousDate == null){
				this.previousDate = nextAnnualLeaveGrant.getGrantDate();
			}
			else if (this.previousDate.before(nextAnnualLeaveGrant.getGrantDate())){
				this.previousDate = nextAnnualLeaveGrant.getGrantDate();
			}
			
			// 期間中の年休付与かチェック
			val grantDate = nextAnnualLeaveGrant.getGrantDate();
			if (grantDate.after(this.period.end())){
				// 期間より後
				return;
			}
			else if (this.period.contains(grantDate)){
				// 期間内
				
				// 「次回年休付与リスト」に追加
				this.nextAnnualLeaveGrantList.add(nextAnnualLeaveGrant);
				
				// 「単一日フラグ」をチェック
				if (this.isSingleDay) return;
			}
		}
		
		// 勤続年数データの最終データの年数に+1しながら、付与日を求める　（上限 99 年）
		val lastLengthServiceTbl = this.lengthServiceTbls.get(this.lengthServiceTbls.size() - 1);
		Integer calcYears = lastLengthServiceTbl.getYear().v();
		while (calcYears < 100){
			
			// 勤続年数から付与日を計算
			val nextAnnualLeaveGrant = this.calcGrantDateFromLengthService(
					lastLengthServiceTbl, Optional.of(calcYears));
			
			// 前回付与日　←　次回年休付与．付与年月日
			if (this.previousDate == null){
				this.previousDate = nextAnnualLeaveGrant.getGrantDate();
			}
			else if (this.previousDate.before(nextAnnualLeaveGrant.getGrantDate())){
				this.previousDate = nextAnnualLeaveGrant.getGrantDate();
			}
			
			// 期間中の年休付与かチェック
			val grantDate = nextAnnualLeaveGrant.getGrantDate();
			if (grantDate.after(this.period.end())){
				// 期間より後
				return;
			}
			else if (this.period.contains(grantDate)){
				// 期間内
				
				// 「次回年休付与リスト」に追加
				this.nextAnnualLeaveGrantList.add(nextAnnualLeaveGrant);
				
				// 「単一日フラグ」をチェック
				if (this.isSingleDay) return;
			}
			
			calcYears++;
		}
	}
	
	/**
	 * 勤続年数から付与日を計算
	 * @param lengthServiceTbl 勤続年数
	 * @param calcYears 計算年数
	 * @return 次回年休付与
	 */
	private NextAnnualLeaveGrant calcGrantDateFromLengthService(
			LengthServiceTbl lengthServiceTbl, Optional<Integer> calcYears){
		
		NextAnnualLeaveGrant nextAnnualLeaveGrant = new NextAnnualLeaveGrant();
		
		// 「付与基準日」をチェック　→　付与計算基準日
		GeneralDate calcCriteria = this.entryDate;
		if (lengthServiceTbl.getStandGrantDay() == GrantReferenceDate.YEAR_HD_REFERENCE_DATE){
			calcCriteria = this.criteriaDate;
		}

		// 年休付与日を計算　→　付与年月日（＝勤続年月の年休付与日）
		GeneralDate grantDate = calcCriteria.addYears(lengthServiceTbl.getYear().v());
		if (calcYears.isPresent()) grantDate = calcCriteria.addYears(calcYears.get());
		grantDate = grantDate.addMonths(lengthServiceTbl.getMonth().v());
		
		// 「一斉付与する」をチェック　および　「一斉付与日」が存在するかチェック
		if (lengthServiceTbl.getAllowStatus() == GrantSimultaneity.USE &&
			this.simultaneousGrantMDOpt.isPresent()){

			// 「前回付与日」が存在するかチェック　→　計算開始日を計算
			GeneralDate calcStart = this.entryDate.addDays(1);
			if (this.previousDate != null){
				calcStart = this.previousDate.addDays(1);
			}

			// 一斉付与年月を取得
			val simultaneousGrantMD = this.simultaneousGrantMDOpt.get();
			Integer simulMonth = simultaneousGrantMD / 100;
			Integer simulDay = simultaneousGrantMD % 100;
			
			// 勤続年月時点の当年一斉付与日・前年一斉付与日の計算
			GeneralDate currentSimul = GeneralDate.ymd(grantDate.year(), simulMonth, 1).addMonths(1);
			GeneralDate previousSimul = currentSimul.addYears(-1);
			currentSimul = currentSimul.addDays(-1);
			previousSimul = previousSimul.addDays(-1);
			if (currentSimul.day() > simulDay){
				currentSimul = GeneralDate.ymd(currentSimul.year(), currentSimul.month(), simulDay);
			}
			if (previousSimul.day() > simulDay){
				previousSimul = GeneralDate.ymd(previousSimul.year(), previousSimul.month(), simulDay);
			}
			
			// 「計算開始日」～「勤続年月の年休付与日」に「一斉付与日」があるかチェック　→　ある時、一斉付与の年休付与日を計算（該当の最も遅い日）
			DatePeriod checkPeriod = new DatePeriod(calcStart, grantDate);
			if (checkPeriod.contains(currentSimul)){
				grantDate = currentSimul;
			}
			else if (checkPeriod.contains(previousSimul)){
				grantDate = previousSimul;
			}
		}
		
		// 次回年休付与にセット（付与年月日、回数）
		nextAnnualLeaveGrant.setGrantDate(grantDate);
		nextAnnualLeaveGrant.setTimes(new GrantNum(lengthServiceTbl.getGrantNum().v()));
		
		return nextAnnualLeaveGrant;
	}
}

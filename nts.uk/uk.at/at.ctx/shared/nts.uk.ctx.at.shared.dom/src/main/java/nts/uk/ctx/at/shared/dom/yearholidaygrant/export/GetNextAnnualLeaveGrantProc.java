package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.UseSimultaneousGrant;

/**
 * 処理：次回年休付与を取得する
 * @author shuichi_ishida
 */
public class GetNextAnnualLeaveGrantProc {
	
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
	public static List<NextAnnualLeaveGrant> algorithm(RequireM1 require, CacheCarrier cacheCarrier,
			String companyId, String grantTableCode, GeneralDate entryDate, GeneralDate criteriaDate,
			DatePeriod period, boolean isSingleDay, Optional<GrantHdTblSet> grantHdTblSetParam,
			Optional<List<LengthServiceTbl>> lengthServiceTblsParam){
		
		List<NextAnnualLeaveGrant> nextAnnualLeaveGrantList = new ArrayList<>();
		
		// 「年休付与テーブル設定」を取得する
		Optional<GrantHdTblSet> grantHdTblSetOpt = Optional.empty();
		if (grantHdTblSetParam.isPresent()){
			grantHdTblSetOpt = grantHdTblSetParam;
		}
		else {
			grantHdTblSetOpt = require.grantHdTblSet(companyId, grantTableCode);
		}
		if (!grantHdTblSetOpt.isPresent()) return nextAnnualLeaveGrantList;
		val grantHdTblSet = grantHdTblSetOpt.get();

		// 一斉付与日　確認
		Optional<Integer> simultaneousGrantMDOpt = Optional.empty();
		if (grantHdTblSet.getUseSimultaneousGrant() == UseSimultaneousGrant.USE){
			simultaneousGrantMDOpt = Optional.of(grantHdTblSet.getSimultaneousGrandMonthDays());
		}
		
		List<LengthServiceTbl> lengthServiceTbls;
		// 「勤続年数テーブル」を取得する
		if (lengthServiceTblsParam.isPresent()){
			lengthServiceTbls = lengthServiceTblsParam.get();
		}
		else {
			lengthServiceTbls = require.lengthServiceTbl(companyId, grantTableCode);
		}
		if (lengthServiceTbls.size() <= 0) return nextAnnualLeaveGrantList;
		
		// 年休付与年月日を計算
		GetNextAnnualLeaveGrantProcKdm002.calcAnnualLeaveGrantDate(entryDate, criteriaDate, simultaneousGrantMDOpt,
				lengthServiceTbls, period, isSingleDay, nextAnnualLeaveGrantList);
		
		for (val nextAnnualLeaveGrant : nextAnnualLeaveGrantList){
			
			// 付与回数をもとに年休付与テーブルを取得
			val grantTimes = nextAnnualLeaveGrant.getTimes().v();
			val grantHdTblOpt = require.grantHdTbl(companyId, 1, grantTableCode, grantTimes);
			
			// 次回年休付与に付与日数・半日年休上限回数・時間年休上限日数をセット
			if (!grantHdTblOpt.isPresent()) continue;
			val grantHdTbl = grantHdTblOpt.get();
			nextAnnualLeaveGrant.setGrantDays(grantHdTbl.getGrantDays());
			nextAnnualLeaveGrant.setHalfDayAnnualLeaveMaxTimes(grantHdTbl.getLimitDayYear());
			nextAnnualLeaveGrant.setTimeAnnualLeaveMaxDays(grantHdTbl.getLimitTimeHd());
		}
		
		// 次回年休付与を返す
		return nextAnnualLeaveGrantList;
	}
	
	public static interface RequireM1 {

		Optional<GrantHdTblSet> grantHdTblSet(String companyId, String yearHolidayCode);

		List<LengthServiceTbl> lengthServiceTbl(String companyId, String yearHolidayCode);

		Optional<GrantHdTbl> grantHdTbl(String companyId, int conditionNo, String yearHolidayCode, int grantNum);
	}
}

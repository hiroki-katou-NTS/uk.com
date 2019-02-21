package nts.uk.ctx.at.record.dom.vacation.obligannleause;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaUsedDays;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetPeriodFromPreviousToNextGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.AnnLeaGrantInfoOutput;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnLeaUseService;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnualLeaveUse;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：ドメインサービス：年休使用義務
 * @author shuichi_ishida
 */
@Stateless
public class ObligedAnnLeaUseServiceImpl implements ObligedAnnLeaUseService {

	/** 社員の前回付与日から次回付与日までの年休使用日数を取得 */
	@Inject
	private GetAnnLeaUsedDays getAnnLeaUsedDays;
	/** 前回付与日から次回付与日までの期間を取得 */
	@Inject
	private GetPeriodFromPreviousToNextGrantDate getPeriodFromPreviousToNextGrantDate;
	/** 次回年休付与を計算 */
	@Inject
	private CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantNum;
	
	/** 使用義務日数の取得 */
	@Override
	public Optional<AnnualLeaveUsedDayNumber> getObligedUseDays(String companyId, boolean distributeAtr,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		
		AnnualLeaveUsedDayNumber result = null;
		
		// 按分が必要かどうか判断
		if (this.checkNeedForProportion(distributeAtr, obligedAnnualLeaveUse)) {
			
			// 年休使用日数の期間按分
			val resultOpt = this.distributePeriod(distributeAtr, obligedAnnualLeaveUse);
			if (resultOpt.isPresent()) result = resultOpt.get();
		}
		else {
			
			// 年休使用義務日数　←　義務日数
			result = new AnnualLeaveUsedDayNumber(obligedAnnualLeaveUse.getObligDays().v());
		}

		// 使用義務日数を返す
		return Optional.ofNullable(result);
	}
	
	/** 義務日数計算期間内の年休使用数を取得 */
	@Override
	public Optional<AnnualLeaveUsedDayNumber> getAnnualLeaveUsedDays(String companyId, String employeeId,
			GeneralDate criteria, ReferenceAtr referenceAtr, boolean distributeAtr,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		
		Optional<AnnualLeaveUsedDayNumber> result = Optional.empty();
		
		// 按分が必要かどうか判断
		if (this.checkNeedForProportion(distributeAtr, obligedAnnualLeaveUse)) {
			
			// 社員の前回付与日から次回付与日までの年休使用日数を取得
			result = this.getAnnLeaUsedDays.ofGrantPeriod(employeeId, criteria, referenceAtr);
		}
		else {
			
			// 期間を計算
			val periodOpt = this.calcPeriod(employeeId, distributeAtr, obligedAnnualLeaveUse);
			if (!periodOpt.isPresent()) return result;
			
			// 指定した期間の年休使用数を取得する
			result = this.getAnnLeaUsedDays.ofPeriod(employeeId, periodOpt.get(), referenceAtr);
		}
		
		// 年休使用数を返す
		return result;
	}
	
	/** 按分が必要かどうか判断 */
	@Override
	public boolean checkNeedForProportion(boolean distributeAtr, ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		
		// 現在の付与期間と重複する付与期間を持つ残数履歴データを取得
		val annLeaGrantInfoOutput = this.getRemainDatasAtDupGrantPeriod(distributeAtr, obligedAnnualLeaveUse);
		
		// 付与残数が1件以上存在するか確認
		return (annLeaGrantInfoOutput.getGrantRemainList().size() >= 1);
	}
	
	/** 現在の付与期間と重複する付与期間を持つ残数履歴データを取得 */
	@Override
	public AnnLeaGrantInfoOutput getRemainDatasAtDupGrantPeriod(boolean distributeAtr,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		
		String employeeId = obligedAnnualLeaveUse.getEmployeeId();
		AnnLeaGrantInfoOutput result = new AnnLeaGrantInfoOutput(employeeId);
		
		// 仮設定（設計不備）
		YearMonth ym = YearMonth.of(2019, 2);
		GeneralDate criteria = GeneralDate.ymd(2019, 2, 21);
		
		// 指定した月を基準に、前回付与日から次回付与日までの期間を取得
		val periodOpt = this.getPeriodFromPreviousToNextGrantDate.getPeriodGrantDate(
				AppContexts.user().companyId(), employeeId, ym, criteria);
		if (!periodOpt.isPresent()) return result;
		val period = periodOpt.get();
		
		// 前回付与日から次回付与日までの期間をチェックする　（１年以上なら、空の結果を返す）
		GeneralDate checkYmd = period.start().addYears(1);
		if (checkYmd.beforeOrEquals(period.end())) return result;
		
		// 期間按分使用区分をチェックする
		if (distributeAtr == false) return result;
		
		// 付与残数を取得
		for (val grantRemain : obligedAnnualLeaveUse.getGrantRemainList()) {
			
			// 付与日から1年後の期間を計算
			GeneralDate grantDate = grantRemain.getGrantDate();
			DatePeriod grantPeriod = new DatePeriod(grantDate, grantDate.addYears(1).addDays(-1));
			
			// 次回年休付与日までの期間と重複する期間があるか確認
			if (grantPeriod.start().beforeOrEquals(period.end()) &&
				grantPeriod.end().afterOrEquals(period.start())) {
				
				// 付与情報へ追加
				result.getGrantRemainList().add(grantRemain);
			}
		}
		
		// 年休付与情報を返す
		return result;
	}
	
	/** 年休使用義務日数の期間按分 */
	@Override
	public Optional<AnnualLeaveUsedDayNumber> distributePeriod(boolean distributeAtr,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		
		String employeeId = obligedAnnualLeaveUse.getEmployeeId();
		AnnualLeaveUsedDayNumber result = null;
		
		// 期間を計算
		val periodOpt = this.calcPeriod(employeeId, distributeAtr, obligedAnnualLeaveUse);
		if (!periodOpt.isPresent()) return Optional.empty();
		DatePeriod period = periodOpt.get();
		
		// 月数を計算
		double numMonth = 0.0;
		{
			// 期間を月数に変換する
			Integer calcNumMon = period.yearMonthsBetween().size();	// 月数
			Integer calcFracDays = 0;								// 端数日数
			GeneralDate checkEnd = period.start().addMonths(calcNumMon-1);		// 計算基準日
			if (period.end().afterOrEquals(checkEnd)) {
				// 期間終了日が計算基準日以降なら、端数日数あり
				DatePeriod fracPeriod = new DatePeriod(checkEnd, period.end());
				calcFracDays = fracPeriod.datesBetween().size();
			}
			else if (period.end().addDays(-1).equals(checkEnd)) {
				// 期間終了日が計算基準日前日なら、端数日数なし
				calcFracDays = 0;
			}
			else {
				// 期間終了日が計算基準日前日以前なら、１か月減らした月数で端数計算
				calcNumMon--;
				checkEnd = period.start().addMonths(calcNumMon-1);
				DatePeriod fracPeriod = new DatePeriod(checkEnd, period.end());
				calcFracDays = fracPeriod.datesBetween().size();
			}
			
			// 端数分を月数に変換する　（最終月の暦日数を計算）
			Integer fracMonDays = 31;
			if (calcFracDays > 0) {
				// 基準終了日からその翌月前日までの日数
				DatePeriod fracMonPeriod = new DatePeriod(checkEnd, checkEnd.addMonths(1));
				fracMonDays = fracMonPeriod.datesBetween().size() - 1;
			}
			
			// 月数の端数分を最終月の暦日数で割る
			double numFrac = 0.0;
			if (calcFracDays > 0 && fracMonDays > 0) {
				numFrac = calcFracDays.doubleValue() / fracMonDays.doubleValue();
			}
			
			// 月数の合計を返す
			numMonth = calcNumMon.doubleValue() + numFrac;
		}
		
		// 年休使用義務日数を計算
		Double calcObligDays = numMonth / 12.0 * obligedAnnualLeaveUse.getObligDays().v();
		calcObligDays = Math.ceil(calcObligDays.doubleValue() * 2.0) / 2.0;		// 0.5日切り上げ
		result = new AnnualLeaveUsedDayNumber(calcObligDays);

		// 年休使用義務日数を返す
		return Optional.ofNullable(result);
	}
	
	/**
	 * 期間を計算
	 * @param employeeId 社員ID
	 * @param distributeAtr 期間按分使用区分
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 期間
	 */
	private Optional<DatePeriod> calcPeriod(String employeeId, boolean distributeAtr,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse){
		
		DatePeriod result = null;
		
		// 現在の付与期間と重複する付与期間を持つ残数履歴データを取得
		val annLeaGrantInfoOutput = this.getRemainDatasAtDupGrantPeriod(distributeAtr, obligedAnnualLeaveUse);
		
		// 開始日　←　付与残数の一番早い付与日
		GeneralDate startDate = null;
		for (val grantRemain : annLeaGrantInfoOutput.getGrantRemainList()) {
			if (startDate == null) {
				startDate = grantRemain.getGrantDate();
				continue;
			}
			if (startDate.after(grantRemain.getGrantDate())) {
				startDate = grantRemain.getGrantDate();
			}
		}
		
		// 次回年休付与日を取得する
		val nextAnnualLeaveGrantList = this.calcNextAnnualLeaveGrantNum.algorithm(
				AppContexts.user().companyId(), employeeId, Optional.empty());
		
		// 終了日　←　次回年休付与日
		GeneralDate endDate = null;
		if (nextAnnualLeaveGrantList.size() > 0) {
			endDate = nextAnnualLeaveGrantList.get(0).getGrantDate();
		}
		
		// 期間を返す
		if (startDate != null && endDate != null) {
			result = new DatePeriod(startDate, endDate);
		}
		return Optional.ofNullable(result);
	}
}

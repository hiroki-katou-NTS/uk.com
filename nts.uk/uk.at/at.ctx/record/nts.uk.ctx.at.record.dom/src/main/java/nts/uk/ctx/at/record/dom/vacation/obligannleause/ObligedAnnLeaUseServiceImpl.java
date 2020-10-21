package nts.uk.ctx.at.record.dom.vacation.obligannleause;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaUsedDays;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetPeriodFromPreviousToNextGrantDate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.AnnLeaGrantInfoOutput;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.AnnLeaUsedDaysOutput;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnLeaUseService;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnualLeaveUse;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

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
	/** 年休付与残数データ */
	@Inject
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
	@Inject 
	private RecordDomRequireService requireService;
	
	/** 使用義務日数の取得 */
	@Override
	public Optional<AnnualLeaveUsedDayNumber> getObligedUseDays(String companyId, boolean distributeAtr,
			GeneralDate criteria, ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		
		AnnualLeaveUsedDayNumber result = null;
		
		// 按分が必要かどうか判断
		if (this.checkNeedForProportion(distributeAtr, criteria, obligedAnnualLeaveUse)) {
			
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
	public AnnLeaUsedDaysOutput getAnnualLeaveUsedDays(String companyId, String employeeId,
			GeneralDate criteria, ReferenceAtr referenceAtr, boolean distributeAtr,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		
		AnnLeaUsedDaysOutput result = new AnnLeaUsedDaysOutput();
		
		// 按分が必要かどうか判断
		DatePeriod period = null;
		if (this.checkNeedForProportion(distributeAtr, criteria, obligedAnnualLeaveUse) == false) {
			
			// 年休使用義務日数の按分しない場合の期間を取得
			val periodOpt = this.getPeriodForNotProportion(criteria, obligedAnnualLeaveUse);
			if (periodOpt.isPresent()) period = periodOpt.get();
		}
		else {
			
			// 期間を計算
			val periodOpt = this.calcPeriod(employeeId, distributeAtr, criteria, obligedAnnualLeaveUse);
			if (periodOpt.isPresent()) period = periodOpt.get();
		}
		if (period == null) return result;
		
		// 指定した期間の年休使用数を取得する
		Optional<AnnualLeaveUsedDayNumber> AnnLeaUsedDaysOpt =
				this.getAnnLeaUsedDays.ofPeriod(employeeId, period, referenceAtr);
		
		// 年休使用数を返す
		result.setDays(AnnLeaUsedDaysOpt);
		
		// 期間を返す
		result.setPeriod(Optional.ofNullable(period));
		
		return result;
	}
	
	/** 按分が必要かどうか判断 */
	@Override
	public boolean checkNeedForProportion(boolean distributeAtr, GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		
		// 期間按分使用区分を確認
		if (distributeAtr == false) return false;
		
		// 付与期間と重複する付与期間を持つ残数履歴データを取得
		val annLeaGrantInfoOutput = this.getRemainDatasAtDupGrantPeriod(criteria, obligedAnnualLeaveUse);
		
		// 付与残数が1件以上存在するか確認
		return (annLeaGrantInfoOutput.getGrantRemainList().size() >= 1);
	}
	
	/** 年休使用義務日数の按分しない場合の期間を取得 */
	@Override
	public Optional<DatePeriod> getPeriodForNotProportion(GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {

		// 年休使用義務日数の按分しない期間の付与日数を取得
		Optional<AnnualLeaveGrantRemainingData> remainDataOpt = this.getGrantInfoForNotProportion(
				criteria, obligedAnnualLeaveUse);
		if (!remainDataOpt.isPresent()) return Optional.empty();
		AnnualLeaveGrantRemainingData remainData = remainDataOpt.get();
		
		// 期間を作成
		GeneralDate startDate = remainData.getGrantDate();
		return Optional.of(new DatePeriod(startDate, startDate.addYears(1).addDays(-1)));
	}
	
	/** 年休使用義務日数の按分しない期間の付与日数を取得 */
	@Override
	public Optional<AnnualLeaveGrantRemainingData> getGrantInfoForNotProportion(GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {

		// 付与期間と重複する付与期間を持つ残数履歴データを取得
		AnnLeaGrantInfoOutput output = this.getRemainDatasAtDupGrantPeriod(criteria, obligedAnnualLeaveUse);
		
		// 付与日が一番古い年月日のドメインを取り出す　→　取り出した年休付与残数データを返す
		return output.getGrantRemainList().stream().min((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
	}
	
	/** 付与期間と重複する付与期間を持つ残数履歴データを取得 */
	@Override
	public AnnLeaGrantInfoOutput getRemainDatasAtDupGrantPeriod(GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		String employeeId = obligedAnnualLeaveUse.getEmployeeId();
		AnnLeaGrantInfoOutput result = new AnnLeaGrantInfoOutput(employeeId);
		
		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, criteria);
		if (closure == null) return result;
		YearMonth currentMonth = closure.getClosureMonth().getProcessingYm();
		
		// 指定した月を基準に、前回付与日から次回付与日までの期間を取得
		val periodOpt = this.getPeriodFromPreviousToNextGrantDate.getPeriodGrantDate(
				AppContexts.user().companyId(), employeeId, currentMonth, criteria, null , null );
		if (!periodOpt.isPresent()) return result;
		val period = periodOpt.get();
		
		// 前回付与日から次回付与日までの期間をチェックする　（１年以上なら、空の結果を返す）
		GeneralDate checkYmd = period.start().addYears(1).addDays(-1);	// ちょうど1年経つ日
		if (period.end().afterOrEquals(checkYmd)) {
			
			// 1年以上の時、前回付与した「年休付与残数データ」を返す
			for (val grantRemain : obligedAnnualLeaveUse.getGrantRemainList()) {
				GeneralDate grantDate = grantRemain.getGrantDate();
				if (grantDate.compareTo(period.start()) == 0) {
					result.getGrantRemainList().add(grantRemain);
				}
			}
			return result;
		}
		
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
		val periodOpt = this.calcPeriod(employeeId, distributeAtr, GeneralDate.today(), obligedAnnualLeaveUse);
		if (!periodOpt.isPresent()) return Optional.empty();
		DatePeriod period = periodOpt.get();
		
		// 月数を計算
		double numMonth = 0.0;
		{
			// 期間を月数に変換する　（期間は、期間開始日～期間終了日翌日にして算出）
			DatePeriod checkPeriod = new DatePeriod(period.start(), period.end().addDays(1));
			Integer calcNumMon = checkPeriod.yearMonthsBetween().size() - 1;	// 月数
			Integer calcFracDays = 0;											// 端数日数
			GeneralDate checkEnd = checkPeriod.start().addMonths(calcNumMon);	// 計算基準日
			if (checkPeriod.end().after(checkEnd)) {
				// 期間終了日翌日が計算基準日より後なら、端数日数あり
				DatePeriod fracPeriod = new DatePeriod(checkEnd, checkPeriod.end());
				calcFracDays = fracPeriod.datesBetween().size() - 1;
			}
			else if (checkPeriod.end().equals(checkEnd)) {
				// 期間終了日翌日が計算基準日と同じなら、端数日数なし
				calcFracDays = 0;
			}
			else {
				// 期間終了日翌日が計算基準日より前なら、１か月減らした月数で端数計算
				calcNumMon--;
				checkEnd = checkPeriod.start().addMonths(calcNumMon);
				DatePeriod fracPeriod = new DatePeriod(checkEnd, checkPeriod.end());
				calcFracDays = fracPeriod.datesBetween().size() - 1;
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
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 期間
	 */
	private Optional<DatePeriod> calcPeriod(String employeeId, boolean distributeAtr, GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse){
		
		DatePeriod result = null;
		
		// 付与期間と重複する付与期間を持つ残数履歴データを取得
		val annLeaGrantInfoOutput = this.getRemainDatasAtDupGrantPeriod(criteria, obligedAnnualLeaveUse);
		
		// 取得したListをソート
		List<AnnualLeaveGrantRemainingData> grantRemainList = annLeaGrantInfoOutput.getGrantRemainList();
		grantRemainList.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));

		if (grantRemainList.size() <= 0) return Optional.empty();
		
		// 年休累積付与情報
		double cumuGrantDays = 0.0;		
		DatePeriod cumuPeriod = null;
		
		for (AnnualLeaveGrantRemainingData grantRemain : grantRemainList) {

			// 閾値
			double threshold = obligedAnnualLeaveUse.getObligDays().v();
			
			// 累積付与の計算
			{
				// 付与数　←　残数データ．明細．付与数．日数
				double grantDays = grantRemain.getDetails().getRemainingNumber().getDays().v();
				
				// 「年休付与残数データ」を取得
				GeneralDate startDate = grantRemain.getGrantDate().addYears(-1).addDays(1);
				GeneralDate endDate = grantRemain.getGrantDate().addDays(-1);
				List<AnnualLeaveGrantRemainingData> remainDatas = this.annLeaGrantRemDataRepo.findByPeriod(
						employeeId, startDate, endDate);
				remainDatas.sort((a, b) -> -a.getGrantDate().compareTo(b.getGrantDate()));	// DESC
				
				GeneralDate lastGrantDate = null;
				for (AnnualLeaveGrantRemainingData remainData : remainDatas) {
					
					// 付与数　←　取得した年休付与残数データ．明細．付与数．日数
					grantDays = remainData.getDetails().getRemainingNumber().getDays().v();
					lastGrantDate = remainData.getGrantDate();
					
					// 閾値と付与数を比較
					if (threshold <= grantDays) break;
				}
				
				// 年休累積付与情報を作成　→　年休累積付与情報を返す
				if (lastGrantDate == null) {
					cumuPeriod = new DatePeriod(
							grantRemain.getGrantDate(), grantRemain.getGrantDate().addYears(1).addDays(-1));
				}
				else {
					cumuPeriod = new DatePeriod(
							lastGrantDate, grantRemain.getGrantDate().addYears(1).addDays(-1));
				}
				cumuGrantDays = grantDays;
			}
			
			if (cumuGrantDays >= obligedAnnualLeaveUse.getObligDays().v()) break;
		}
		if (cumuPeriod == null) return Optional.empty();
		
		// 開始日　←　年休累積付与情報．期間．開始日
		GeneralDate startDate = cumuPeriod.start();

		// 指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		val periodOpt = this.getPeriodFromPreviousToNextGrantDate.getPeriodYMDGrant(
				AppContexts.user().companyId(), employeeId, criteria, null, null );
		if (!periodOpt.isPresent()) return Optional.empty();
		val period = periodOpt.get();
		
		// 取得した期間が１年未満かどうか判断
		GeneralDate endDate = period.end();
		if (period.start().addYears(1).addDays(-1).after(period.end())) {	// 1年未満の時
			endDate = period.end().addYears(1);
		}
		
		// 期間を返す
		if (startDate != null && endDate != null) {
			result = new DatePeriod(startDate, endDate);
		}
		return Optional.ofNullable(result);
	}
}

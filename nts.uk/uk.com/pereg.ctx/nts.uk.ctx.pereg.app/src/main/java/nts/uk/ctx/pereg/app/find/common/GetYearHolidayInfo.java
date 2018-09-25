package nts.uk.ctx.pereg.app.find.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.export.GetCalcStartForNextLeaveGrant;
import nts.uk.ctx.at.shared.dom.adapter.employee.AffCompanyHistSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.CalcNextAnnLeaGrantInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetYearHolidayInfo {
	
	@Inject 
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	@Inject
	private CalcNextAnnLeaGrantInfo getNextAnnLeaGrantInfo;
	
	@Inject
	private GetCalcStartForNextLeaveGrant getCalcStartForNextLeaveGrant;
	/**
	 * アルゴリズム「次回年休情報を取得する」
　	 * パラメータ＝社員ID：画面で選択している社員ID
　	 * パラメータ＝年休付与基準日：IS00279の値
	 * パラメータ＝年休付与テーブル：IS00280の値
	 * パラメータ＝労働条件の期間：NULL
	 * パラメータ＝契約時間：NULL
	 * パラメータ＝入社年月日：NULL
	 * パラメータ＝退職年月日：NULL
	 * @return 次回年休付与日, 次回年休付与日数, 次回時間年休付与上限
	 */
	public YearHolidayInfoResult getYearHolidayInfo(AnnLeaEmpBasicInfo annLea){
		YearHolidayInfoResult result = new YearHolidayInfoResult(null, null, Optional.empty());

		// 次回休暇付与を計算する開始日を取得する
		GeneralDate baseDate = getCalcStartForNextLeaveGrant.algorithm(annLea.getSid());

		if (baseDate == null){
			return result;
		}
		GeneralDate entryDate = null;

		Optional<LimitedTimeHdTime> contractTime = Optional.empty();

		// Set entry date
		if (annLea.getEntryDate() != null && baseDate.afterOrEquals(annLea.getEntryDate())) {
			entryDate = annLea.getEntryDate();

		} else {
			// ドメインモデル「所属会社履歴（社員別）」を取得し、入社年月日を取得する
			AffCompanyHistSharedImport affComHist = empEmployeeAdapter.GetAffComHisBySidAndBaseDate(annLea.getSid(),
					baseDate);

			entryDate = affComHist.getEntryDate().orElse(null);
			
			// ドメインモデル「所属会社履歴（社員別）」を取得し直し、入社年月日を取得する
			if (entryDate == null){
				AffCompanyHistSharedImport defaultValue = empEmployeeAdapter.GetAffComHisBySid(AppContexts.user().companyId(),annLea.getSid());
				entryDate = defaultValue.getEntryDate().orElse(null);
			}
						
		}
		
		if (entryDate == null){
			return result;
		}
		
		// Set contract time
		if (annLea.getPeriodCond() != null && annLea.getContractTime() != null && annLea.getPeriodCond().start() != null
				&& annLea.getPeriodCond().end() != null && baseDate.afterOrEquals(annLea.getPeriodCond().start())
				&& baseDate.beforeOrEquals(annLea.getPeriodCond().end())) {
			contractTime = Optional.ofNullable(new LimitedTimeHdTime(annLea.getContractTime()));
		} else {
			// アルゴリズム「社員の労働条件を取得する」を実行し、契約時間を取得する
			Optional<WorkingConditionItem> workCond = workingConditionItemRepository
					.getBySidAndStandardDate(annLea.getSid(), baseDate);
	
			if (workCond.isPresent()) {
				contractTime = workCond.get().getContractTime().v() == null ? Optional.empty()
						: Optional.ofNullable(new LimitedTimeHdTime(workCond.get().getContractTime().v()));
			}
		}
		// 次回年休付与情報を取得する
		Optional<NextAnnualLeaveGrant> nextAnnualLeave = getNextAnnLeaGrantInfo.algorithm(
				AppContexts.user().companyId(), baseDate, entryDate, annLea.getGrantDate(), annLea.getGrantTable(),
				contractTime);

		if (nextAnnualLeave.isPresent()) {
			result = new YearHolidayInfoResult(nextAnnualLeave.get().getGrantDate(),
					nextAnnualLeave.get().getGrantDays().v(),
					nextAnnualLeave.get().getTimeAnnualLeaveMaxTime().map(i -> i.v()));
		}
		return result;
	}
}
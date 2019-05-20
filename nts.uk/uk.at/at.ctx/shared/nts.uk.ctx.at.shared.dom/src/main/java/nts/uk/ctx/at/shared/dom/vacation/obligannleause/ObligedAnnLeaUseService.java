package nts.uk.ctx.at.shared.dom.vacation.obligannleause;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：年休使用義務
 * @author shuichi_ishida
 */
public interface ObligedAnnLeaUseService {

	/**
	 * 使用義務日数の取得
	 * @param companyId 会社ID
	 * @param distributeAtr 期間按分使用区分
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休使用義務日数
	 */
	Optional<AnnualLeaveUsedDayNumber> getObligedUseDays(String companyId, boolean distributeAtr,
			GeneralDate criteria, ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 義務日数計算期間内の年休使用数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param referenceAtr 参照先区分
	 * @param distributeAtr 期間按分使用区分
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休使用数Output
	 */
	AnnLeaUsedDaysOutput getAnnualLeaveUsedDays(String companyId, String employeeId,
			GeneralDate criteria, ReferenceAtr referenceAtr, boolean distributeAtr,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 按分が必要かどうか判断
	 * @param distributeAtr 期間按分使用区分
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return　true:必要、false:不要
	 */
	boolean checkNeedForProportion(boolean distributeAtr, GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse);

	/**
	 * 年休使用義務日数の按分しない場合の期間を取得
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 期間
	 */
	Optional<DatePeriod> getPeriodForNotProportion(GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 年休使用義務日数の按分しない期間の付与日数を取得
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休付与残数データ
	 */
	Optional<AnnualLeaveGrantRemainingData> getGrantInfoForNotProportion(GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 付与期間と重複する付与期間を持つ残数履歴データを取得
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休付与情報Output
	 */
	AnnLeaGrantInfoOutput getRemainDatasAtDupGrantPeriod(GeneralDate criteria,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 年休使用義務日数の期間按分
	 * @param distributeAtr 期間按分使用区分
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休使用義務日数
	 */
	Optional<AnnualLeaveUsedDayNumber> distributePeriod(boolean distributeAtr,
			ObligedAnnualLeaveUse obligedAnnualLeaveUse);
}

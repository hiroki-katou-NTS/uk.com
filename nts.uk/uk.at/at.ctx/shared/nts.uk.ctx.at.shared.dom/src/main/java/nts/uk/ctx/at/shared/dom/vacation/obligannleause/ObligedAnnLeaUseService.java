package nts.uk.ctx.at.shared.dom.vacation.obligannleause;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：年休使用義務
 * @author shuichi_ishida
 */
public interface ObligedAnnLeaUseService {

	/**
	 * 年休使用日数が使用義務日数を満たしているかチェックする
	 * @param obligedUseDays
	 * @param annualLeaveUsedDays
	 * @return
	 */
	boolean checkObligedUseDays(ObligedUseDays obligedUseDays, AnnLeaUsedDaysOutput annualLeaveUsedDays);

	/**
	 * 使用義務日数の取得
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休使用義務日数
	 */
	Optional<ObligedUseDays> getObligedUseDays(GeneralDate criteria, ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 義務日数計算期間内の年休使用数を取得
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休使用数Output
	 */
	AnnLeaUsedDaysOutput getAnnualLeaveUsedDays(GeneralDate criteria, ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 按分が必要かどうか判断
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return　true:必要、false:不要
	 */
//	boolean checkNeedForProportion(GeneralDate criteria, ObligedAnnualLeaveUse obligedAnnualLeaveUse);

	/**
	 * 年休使用義務日数の按分しない場合の期間を取得
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 期間
	 */
//	Optional<DatePeriod> getPeriodForNotProportion(GeneralDate criteria, ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 年休使用義務日数の按分しない期間の付与日数を取得
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休付与残数データ
	 */
//	Optional<AnnualLeaveGrantRemainingData> getGrantInfoForNotProportion(GeneralDate criteria, ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 付与期間と重複する付与期間を持つ残数履歴データを取得
	 * @param criteria 基準日
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休付与情報Output
	 */
//	AnnLeaGrantInfoOutput getRemainDatasAtDupGrantPeriod(GeneralDate criteria, ObligedAnnualLeaveUse obligedAnnualLeaveUse);
	
	/**
	 * 年休使用義務日数の期間按分
	 * @param obligedAnnualLeaveUse 年休使用義務日数
	 * @return 年休使用義務日数
	 */
//	Optional<AnnualLeaveUsedDayNumber> distributePeriod(ObligedAnnualLeaveUse obligedAnnualLeaveUse);
}

package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;

public interface MonthlyDayoffRemainDataRepository {
	/**
	 * ドメインモデル「代休月別残数データ」を取得
	 * @param employeeId社員ID=パラメータ「社員ID」
	 * @param ym 年月=処理中の年月
	 * @param status 締め処理状態
	 * @return
	 */
	List<MonthlyDayoffRemainData> getDayOffDataBySidYmStatus(String employeeId, YearMonth ym, ClosureStatus status);

}

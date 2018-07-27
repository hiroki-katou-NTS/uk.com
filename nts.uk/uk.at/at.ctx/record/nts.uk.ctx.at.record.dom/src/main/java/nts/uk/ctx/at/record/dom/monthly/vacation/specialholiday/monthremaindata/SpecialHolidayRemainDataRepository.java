package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;

public interface SpecialHolidayRemainDataRepository {
	/**
	 * ドメインモデル「特別休暇月別残数データ」を取得
	 * @param sid
	 * @param ym 年月=処理中の年月
	 * @param status 締め処理状態=status
	 * @return
	 */
	List<SpecialHolidayRemainData> getByYmStatus(String sid, YearMonth ym, ClosureStatus status);
}

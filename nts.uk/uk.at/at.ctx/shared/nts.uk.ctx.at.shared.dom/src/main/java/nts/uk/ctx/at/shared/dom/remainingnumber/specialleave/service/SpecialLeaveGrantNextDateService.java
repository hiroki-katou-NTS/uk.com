package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SpecialLeaveGrantNextDateService {
	/**
	 * 次回特別休暇付与日を取得する
	 * @param param
	 * @return
	 */
	Optional<GrantDaysInfor> getNumberOfGrantDays(SpecialLeaveGrantNextDateInput param);
	/**
	 * 次回特休付与計算開始日を計算する
	 * @param closureDate 締め開始日
	 * @param inputDate 入社年月日
	 * @return
	 */
	GeneralDate getStartDateOfSpecialNextDay(GeneralDate closureDate, GeneralDate inputDate);
	/**
	 * 次回特別休暇付与日を取得
	 * @param param
	 * @return
	 */
	Optional<GrantDaysInfor> getGrantDataOfNextDay(SpeGrantNextDateByGetInput param);
}

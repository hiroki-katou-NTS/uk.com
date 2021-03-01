package nts.uk.ctx.at.shared.dom.application.appabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author thanhnx
 *         UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.休暇申請画面描画情報
 *         休暇申請画面描画情報
 *
 */

@Getter
@AllArgsConstructor
public class VacationRequestInfoShare {

	// 休暇申請の種類
	private HolidayAppTypeShare holidayApplicationType;

	// 休暇申請の種類
	private SupplementInfoVacationShare info;
}

package nts.uk.ctx.at.request.dom.application.appabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.休暇申請画面描画情報
 * 休暇申請画面描画情報
 *
 */

@Getter
@AllArgsConstructor
public class VacationRequestInfo {

    // 休暇申請の種類
    private HolidayAppType holidayApplicationType;
    
    // 休暇申請の種類
    private SupplementInfoVacation info;
}

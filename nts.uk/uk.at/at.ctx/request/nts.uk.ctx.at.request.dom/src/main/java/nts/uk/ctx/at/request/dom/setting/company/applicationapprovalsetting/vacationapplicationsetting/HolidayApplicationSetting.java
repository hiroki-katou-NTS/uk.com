package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.UnregisterableCheckAtr;

import java.util.List;

/**
 * refactor 4 refactor4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.休暇申請設定
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HolidayApplicationSetting extends AggregateRoot {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 休暇申請種類表示名
     */
    private List<HolidayApplicationTypeDisplayName> holidayApplicationTypeDisplayName;

    /**
     * 半日年休の使用上限チェック
     */
    private UnregisterableCheckAtr halfDayAnnualLeaveUsageLimitCheck;

    public static HolidayApplicationSetting create(String companyId, List<HolidayApplicationTypeDisplayName> displayNames, Integer halfDayAnnualLeaveUsageLimitCheck) {
        return new HolidayApplicationSetting(
                companyId,
                displayNames,
                EnumAdaptor.valueOf(halfDayAnnualLeaveUsageLimitCheck, UnregisterableCheckAtr.class));
    }
}

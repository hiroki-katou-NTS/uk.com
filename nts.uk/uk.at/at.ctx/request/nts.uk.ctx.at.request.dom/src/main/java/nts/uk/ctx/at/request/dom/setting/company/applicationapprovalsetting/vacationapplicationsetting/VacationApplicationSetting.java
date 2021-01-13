package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.UnregisterableCheckAtr;

/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.休暇申請設定.休暇申請設定
 * 休暇申請設定
 * 
 */
@AllArgsConstructor
@Getter
public class VacationApplicationSetting extends Application {
    
    // 会社ID
    private String companyId;
    
    // 登録不可チェック区分
    private UnregisterableCheckAtr checkUpperLimitForHalfDayHolidays;
    
    // 休暇申請種類表示名
    private List<HolidayApplicationTypeDisplayName> vacationDisplayNames;

    public VacationApplicationSetting(Application application) {
        super(application);
    }
}

package nts.uk.ctx.at.request.dom.application.appabsence;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.休暇申請反映情報
 * 休暇申請反映情報
 *
 */
@Data
public class ReflectFreeTimeApp {

    // 時間帯(勤務NO付き)
    private Optional<List<TimeZoneWithWorkNo>> workingHours;
    
    // 時間消化申請
    private Optional<TimeDigestApplication> timeDegestion;
    
    // 勤務情報
    private WorkInformation workInfo;
    
    // するしない区分
    private NotUseAtr workChangeUse;
    
    public ReflectFreeTimeApp(Optional<List<TimeZoneWithWorkNo>> workingHours, 
            Optional<TimeDigestApplication> timeDegestion, 
            WorkInformation workInfo, 
            NotUseAtr workChangeUse) {
        this.workingHours = workingHours == null ? Optional.empty() : workingHours;
        this.timeDegestion = timeDegestion == null ? Optional.empty() : timeDegestion;
        this.workInfo = workInfo;
        this.workChangeUse = workChangeUse;
    }
}

package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4 refactor4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.時間休暇申請
 * 時間休暇申請の反映
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveApplicationReflect extends AggregateRoot {

    private String companyId;

    /**
     * 実績の時間帯へ反映する
     */
    private NotUseAtr reflectActualTimeZone;

    /**
     * 時間休暇の反映先
     */
    private TimeLeaveDestination destination;

    /**
     * 時間休暇を反映する
     */
    private TimeLeaveAppReflectCondition condition;
}

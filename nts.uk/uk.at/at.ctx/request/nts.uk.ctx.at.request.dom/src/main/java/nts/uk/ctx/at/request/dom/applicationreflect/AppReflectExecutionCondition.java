package nts.uk.ctx.at.request.dom.applicationreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請反映.申請反映実行条件
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppReflectExecutionCondition extends AggregateRoot {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 事前申請を勤務予定に反映する
     */
    private NotUseAtr applyBeforeWorkSchedule;

    /**
     * 勤務予定が確定状態でも反映する
     */
    private NotUseAtr evenIfScheduleConfirmed;

    /**
     * 勤務実績が確定状態でも反映する
     */
    private NotUseAtr evenIfWorkRecordConfirmed;

    public static AppReflectExecutionCondition create(String companyId, int applyBeforeSchedule, int evenIfScheduleConfirmed, int evenIfRecordConfirmed) {
        return new AppReflectExecutionCondition(companyId,
                NotUseAtr.valueOf(applyBeforeSchedule),
                NotUseAtr.valueOf(evenIfScheduleConfirmed),
                NotUseAtr.valueOf(evenIfRecordConfirmed));
    }
}

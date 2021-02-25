package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.残業・休日出勤.残業申請
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OtWorkAppReflect {
    /**
     * 事前
     */
    private BeforeOtWorkAppReflect before;

    /**
     * 事後
     */
    private AfterOtWorkAppReflect after;

    private NotUseAtr reflectActualWorkAtr;

    public static OtWorkAppReflect create(int reflectActualWorkAtr, int reflectWorkInfoAtr, int reflectActualOvertimeHourAtr, int reflectBeforeBreak, int workReflect, int reflectPaytime, int reflectDivergence, int reflectBreakOuting) {
        return new OtWorkAppReflect(
                BeforeOtWorkAppReflect.create(reflectWorkInfoAtr, reflectActualOvertimeHourAtr, reflectBeforeBreak),
                AfterOtWorkAppReflect.create(workReflect, reflectPaytime, reflectDivergence, reflectBreakOuting),
                EnumAdaptor.valueOf(reflectActualWorkAtr, NotUseAtr.class)
        );
    }
}

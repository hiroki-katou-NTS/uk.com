package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation;

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
public class OvertimeWorkApplicationReflect {
    /**
     * 事前
     */
    private BeforeOvertimeWorkAppReflect before;

    /**
     * 事後
     */
    private AfterOvertimeWorkAppReflect after;

    private NotUseAtr reflectActualWorkAtr;

    public static OvertimeWorkApplicationReflect create(int reflectActualWorkAtr, int reflectWorkInfoAtr, int reflectActualOvertimeHourAtr, int reflectBeforeBreak, int workReflect, int reflectPaytime, int reflectOptional, int reflectDivergence, int reflectBreakOuting) {
        return new OvertimeWorkApplicationReflect(
                BeforeOvertimeWorkAppReflect.create(reflectWorkInfoAtr, reflectActualOvertimeHourAtr, reflectBeforeBreak),
                AfterOvertimeWorkAppReflect.create(workReflect, reflectPaytime, reflectOptional, reflectDivergence, reflectBreakOuting),
                EnumAdaptor.valueOf(reflectActualWorkAtr, NotUseAtr.class)
        );
    }
}

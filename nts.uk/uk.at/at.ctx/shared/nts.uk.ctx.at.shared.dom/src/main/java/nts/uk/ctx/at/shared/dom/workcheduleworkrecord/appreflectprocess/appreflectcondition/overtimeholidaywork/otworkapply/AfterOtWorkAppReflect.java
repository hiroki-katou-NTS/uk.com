package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.OthersReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 事後残業申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AfterOtWorkAppReflect {
    /**
     * その他項目を反映する
     */
    private OthersReflect othersReflect;

    /**
     * 休憩・外出を申請反映する
     */
    private BreakApplication breakLeaveApplication;

    /**
     * 出退勤を反映する
     */
    private NotUseAtr workReflect;

    public static AfterOtWorkAppReflect create(int workReflect, int reflectPaytime, int reflectDivergence, int reflectBreakOuting) {
        return new AfterOtWorkAppReflect(
                new OthersReflect(
                        EnumAdaptor.valueOf(reflectDivergence, NotUseAtr.class),
//                        EnumAdaptor.valueOf(reflectOptional, NotUseAtr.class),
                        EnumAdaptor.valueOf(reflectPaytime, NotUseAtr.class)
                ),
                new BreakApplication(EnumAdaptor.valueOf(reflectBreakOuting, NotUseAtr.class)),
                EnumAdaptor.valueOf(workReflect, NotUseAtr.class)
        );
    }
}

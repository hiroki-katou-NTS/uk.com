package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.残業・休日出勤.休日出勤申請.休日出勤申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HdWorkAppReflect {
    /**
     * 事前
     */
    private BeforeHdWorkAppReflect before;

    /**
     * 事後
     */
    private AfterHdWorkAppReflect after;

    public static HdWorkAppReflect create(int reflectActualHolidayWorkAtr, int workReflect, int reflectPaytime, int reflectDivergence, int reflectBreakOuting) {
        return new HdWorkAppReflect(
                new BeforeHdWorkAppReflect(EnumAdaptor.valueOf(reflectActualHolidayWorkAtr, NotUseAtr.class)),
                AfterHdWorkAppReflect.create(workReflect, reflectPaytime, reflectDivergence, reflectBreakOuting)
        );
    }
}

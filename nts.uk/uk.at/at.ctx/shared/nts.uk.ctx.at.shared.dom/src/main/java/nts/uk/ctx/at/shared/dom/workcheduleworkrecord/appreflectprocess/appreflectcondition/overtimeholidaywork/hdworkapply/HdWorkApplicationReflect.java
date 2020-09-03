package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.AfterOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.BeforeOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.残業・休日出勤.休日出勤申請.休日出勤申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HdWorkApplicationReflect extends AggregateRoot {
    /**
     * 事前
     */
    private BeforeHdWorkAppReflect before;

    /**
     * 事後
     */
    private AfterHdWorkAppReflect after;

    public static HdWorkApplicationReflect create(int reflectActualHolidayWorkAtr, int workReflect, int reflectPaytime, int reflectOptional, int reflectDivergence, int reflectBreakOuting) {
        return new HdWorkApplicationReflect(
                new BeforeHdWorkAppReflect(EnumAdaptor.valueOf(reflectActualHolidayWorkAtr, NotUseAtr.class)),
                AfterHdWorkAppReflect.create(workReflect, reflectPaytime, reflectOptional, reflectDivergence, reflectBreakOuting)
        );
    }
}

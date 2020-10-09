package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.残業・休日出勤.残業休日出勤申請の反映
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppReflectOtHdWork extends AggregateRoot {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 休日出勤申請
     */
    private HdWorkAppReflect holidayWorkAppReflect;

    /**
     * 残業申請
     */
    private OtWorkAppReflect overtimeWorkAppReflect;

    /**
     * 時間外深夜時間を反映する
     */
    private NotUseAtr nightOvertimeReflectAtr;
}

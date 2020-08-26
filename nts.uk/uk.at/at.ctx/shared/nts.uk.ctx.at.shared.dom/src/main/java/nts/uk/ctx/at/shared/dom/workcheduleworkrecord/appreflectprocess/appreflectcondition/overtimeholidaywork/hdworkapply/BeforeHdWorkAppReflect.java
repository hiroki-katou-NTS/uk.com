package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4 refactor4
 * 事前休日出勤申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BeforeHdWorkAppReflect extends DomainObject {
    /**
     * 休日出勤時間を実績項目へ反映する
     */
    private NotUseAtr reflectActualHolidayWorkAtr;
}

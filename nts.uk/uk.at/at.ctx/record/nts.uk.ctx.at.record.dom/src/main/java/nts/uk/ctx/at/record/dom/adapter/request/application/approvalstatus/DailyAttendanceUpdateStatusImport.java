package nts.uk.ctx.at.record.dom.adapter.request.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

import java.util.Optional;

@Data
@AllArgsConstructor
public class DailyAttendanceUpdateStatusImport {
    /**
     * 実績反映日時
     */
    private Optional<GeneralDateTime> opActualReflectDateTime;

    /**
     * 予定反映日時
     */
    private Optional<GeneralDateTime> opScheReflectDateTime;

    /**
     * 実績反映不可理由
     */
    private Integer opReasonActualCantReflect;

    /**
     * 予定反映不可理由
     */
    private Integer opReasonScheCantReflect;

}

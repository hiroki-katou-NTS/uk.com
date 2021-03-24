package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TmpChildCareNurseMngWorkImport {
    /** 社員ID */
    private String employeeId;
    /** 期間 */
    private DatePeriod period;
    /** 上書きフラグ */
    private Boolean isOverWrite;
    /** 上書き用暫定管理データ */
    private List<TmpChildCareNurseMngWorkImport> tempChildCareDataforOverWriteList;
    /** 作成元区分<Optional> */
    private Optional<CreateAtr> creatorAtr;
    /** 上書き対象期間<Optional> */
    private Optional<GeneralDate> periodOverWrite;
}

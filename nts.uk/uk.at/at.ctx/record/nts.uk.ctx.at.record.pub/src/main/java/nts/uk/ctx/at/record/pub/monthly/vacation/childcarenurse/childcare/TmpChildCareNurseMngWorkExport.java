package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.TmpChildCareNurseMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TmpChildCareNurseMngWorkExport {
    /** 社員ID */
    private String employeeId;
    /** 期間 */
    private DatePeriod period;
    /** 上書きフラグ */
    private Boolean isOverWrite;
    /** 上書き用暫定管理データ */
    private List<TmpChildCareNurseMngWorkExport> tempChildCareDataforOverWriteList;
    /** 作成元区分<Optional> */
    private Optional<CreateAtr> creatorAtr;
    /** 上書き対象期間<Optional> */
    private Optional<GeneralDate> periodOverWrite;

    public TmpChildCareNurseMngWork toDomain() {
        return TmpChildCareNurseMngWork.of(
                employeeId,
                period,
                isOverWrite,
                tempChildCareDataforOverWriteList.stream().map(i -> i.toDomain()).collect(Collectors.toList()),
                creatorAtr,
                periodOverWrite
        );
    }

    public static TmpChildCareNurseMngWorkExport fromDomain(TmpChildCareNurseMngWork domain) {
        return new TmpChildCareNurseMngWorkExport(
                domain.getEmployeeId(),
                domain.getPeriod(),
                domain.getIsOverWrite(),
                domain.getTempChildCareDataforOverWriteList().stream().map(TmpChildCareNurseMngWorkExport::fromDomain).collect(Collectors.toList()),
                domain.getCreatorAtr(),
                domain.getPeriodOverWrite()
        );
    }
}

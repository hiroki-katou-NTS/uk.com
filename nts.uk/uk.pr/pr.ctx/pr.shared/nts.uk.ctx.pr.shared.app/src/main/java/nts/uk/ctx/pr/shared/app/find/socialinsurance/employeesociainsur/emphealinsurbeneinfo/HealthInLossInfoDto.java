package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfo;

@Value
@Data
@AllArgsConstructor
public class HealthInLossInfoDto {

    /**
     * 社員ID
     */
    private String empId;

    /**
     * その他
     */
    private int other;

    /**
     * その他理由
     */
    private String otherReason;

    /**
     * 保険証回収添付枚数
     */
    private Integer caInsurance;

    /**
     * 保険証回収返不能枚数
     */
    private Integer numRecoved;

    /**
     * 資格喪失原因
     */
    private Integer cause;

    public static HealthInLossInfoDto fromDomain(HealthInsLossInfo domain)
    {
        return new HealthInLossInfoDto(domain.getEmpId(),
                domain.getOther(),
                domain.getOtherReason().map(i->i.v()).orElse(null),
                domain.getCaInsurance().map(i->i.v()).orElse(null),
                domain.getNumRecoved().map(i->i.v()).orElse(null),
                domain.getCause().get().value
        );
    }
}

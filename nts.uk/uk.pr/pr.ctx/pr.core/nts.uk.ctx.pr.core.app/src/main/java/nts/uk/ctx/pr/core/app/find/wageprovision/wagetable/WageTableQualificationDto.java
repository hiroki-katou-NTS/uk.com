package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableQualification;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class WageTableQualificationDto {
    /**
     * 資格グループコード
     */
    private String qualificationGroupCode;

    /**
     * 資格グループ名
     */
    private String qualificationGroupName;

    /**
     * 支払方法
     */
    private int paymentMethod;

    /**
     * 対象資格コード
     */
    private List<WageTableQualificationInfoDto> eligibleQualificationCode;

    public static WageTableQualificationDto fromDomain(WageTableQualification wageTableQualification) {
        return new WageTableQualificationDto(
                wageTableQualification.getQualificationGroupCode(),
                wageTableQualification.getQualificationGroupName(),
                wageTableQualification.getPaymentMethod(),
                wageTableQualification.getEligibleQualificationCode().stream().map(x -> new WageTableQualificationInfoDto(
                        x.getId(),
                        x.getQualificationCode(),
                        x.getQualificationName(),
                        x.getWageTablePaymentAmount()
                )).collect(Collectors.toList())
        );
    }
}
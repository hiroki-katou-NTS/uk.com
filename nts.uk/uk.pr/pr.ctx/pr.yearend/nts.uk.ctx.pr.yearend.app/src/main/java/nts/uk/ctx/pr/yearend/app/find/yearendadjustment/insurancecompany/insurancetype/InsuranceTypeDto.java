package nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.insurancetype;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType.InsuranceType;

/**
 * 保険種類情報: DTO
 */
@AllArgsConstructor
@Value
public class InsuranceTypeDto {

    /**
     * 生命保険コード
     */
    private String lifeInsuranceCode;

    /**
     * コード
     */
    private String insuranceTypeCode;

    /**
     * 名称
     */
    private String insuranceTypeName;

    /**
     * 区分
     */
    private int atrOfInsuranceType;


    public static InsuranceTypeDto fromDomain(InsuranceType domain) {
        return new InsuranceTypeDto(domain.getLifeInsuranceCode().v(), domain.getInsuranceTypeCode().v(), domain.getInsuranceTypeName().v(), domain.getAtrOfInsuranceType().value);
    }

}

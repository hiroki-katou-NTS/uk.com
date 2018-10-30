package nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountName;

/**
 * 給与個人別金額名称: DTO
 */
@AllArgsConstructor
@Value
public class SalIndAmountNameDto {

    /**
     * 会社ID
     */
    private String cId;

    /**
     * 個人金額コード
     */
    private String individualPriceCode;

    /**
     * カテゴリ区分
     */
    private int cateIndicator;

    /**
     * 個人金額名称
     */
    private String individualPriceName;


    public static SalIndAmountNameDto fromDomain(SalIndAmountName domain) {
        return new SalIndAmountNameDto(domain.getCId(), domain.getIndividualPriceCode().v(), domain.getCateIndicator().value, domain.getIndividualPriceName().v());
    }

}

package nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname;

import java.util.Optional;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
 * 給与個人別金額名称
 */
@Getter
public class SalIndAmountName extends AggregateRoot {

    /**
     * 会社ID
     */
    private String cId;

    /**
     * 個人金額コード
     */
    private IndividualPriceCode individualPriceCode;

    /**
     * 個人金額名称
     */
    private IndividualPriceName individualPriceName;

    /**
     * カテゴリ区分
     */
    private PerValueCateCls cateIndicator;

    public SalIndAmountName(String cid, String individualPriceCode, int cateIndicator, String individualPriceName) {
        this.cId = cid;
        this.individualPriceCode = new IndividualPriceCode(individualPriceCode);
        this.individualPriceName = new IndividualPriceName(individualPriceName);
        this.cateIndicator = EnumAdaptor.valueOf(cateIndicator, PerValueCateCls.class);
    }

}

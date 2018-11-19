package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 納付先情報
 */
@Getter
public class PayeeInfo extends AggregateRoot {

    /**
     * 履歴ID
     */
    private String histId;

    /**
     * 住民税納付先
     */
    private ResidentTaxPayeeCode residentTaxPayeeCd;

    public PayeeInfo(String histId, String residentTaxPayeeCd) {
        this.residentTaxPayeeCd = new ResidentTaxPayeeCode(residentTaxPayeeCd);
        this.histId = histId;
    }

}

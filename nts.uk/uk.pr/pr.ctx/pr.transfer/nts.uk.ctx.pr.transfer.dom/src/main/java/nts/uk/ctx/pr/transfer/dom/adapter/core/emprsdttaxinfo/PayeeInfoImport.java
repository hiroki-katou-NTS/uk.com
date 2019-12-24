package nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 納付先情報
 */
@Setter
@Getter
@NoArgsConstructor
public class PayeeInfoImport {

    /**
     * 履歴ID
     */
    private String histId;

    /**
     * 住民税納付先
     */
    private String residentTaxPayeeCd;
}

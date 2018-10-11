package nts.uk.ctx.pr.core.pub.emprsdttaxinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 納付先情報
 */
@Setter
@Getter
@NoArgsConstructor
public class PayeeInfoExport {

    /**
     * 履歴ID
     */
    private String histId;

    /**
     * 住民税納付先
     */
    private String residentTaxPayeeCd;

}

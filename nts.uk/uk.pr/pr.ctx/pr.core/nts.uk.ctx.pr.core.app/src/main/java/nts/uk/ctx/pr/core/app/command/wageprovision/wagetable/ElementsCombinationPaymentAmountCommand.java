package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ContentElementAttributeDto;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTablePaymentAmount;

import java.math.BigDecimal;

/**
 * 要素の組み合わせで支払う金額
 */
@Data
@NoArgsConstructor
public class ElementsCombinationPaymentAmountCommand extends DomainObject {

    /**
     * 賃金テーブル支給金額
     */
    private BigDecimal wageTablePaymentAmount;

    /**
     * 要素属性
     */
    private ContentElementAttributeCommand elementAttribute;

    public ElementsCombinationPaymentAmount fromCommandToDomain() {
        return new ElementsCombinationPaymentAmount(new WageTablePaymentAmount(wageTablePaymentAmount), elementAttribute.fromCommandToDomain());
    }

}

package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ContentElementAttribute;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTablePaymentAmount;

import java.math.BigDecimal;

/**
 * 要素の組み合わせで支払う金額
 */
@Data
@NoArgsConstructor
public class ElementsCombinationPaymentAmountDto extends DomainObject {

    /**
     * 賃金テーブル支給金額
     */
    private BigDecimal wageTablePaymentAmount;

    /**
     * 要素属性
     */
    private ContentElementAttributeDto elementAttribute;

    public static ElementsCombinationPaymentAmountDto fromDomainToDto(ElementsCombinationPaymentAmount domain) {
        ElementsCombinationPaymentAmountDto dto = new ElementsCombinationPaymentAmountDto();
        dto.wageTablePaymentAmount = domain.getWageTablePaymentAmount().v();
        dto.elementAttribute = ContentElementAttributeDto.fromDomainToDto(domain.getElementAttribute());
        return dto;
    }

}

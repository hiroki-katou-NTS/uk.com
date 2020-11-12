package nts.uk.ctx.at.request.app.find.application.optitem.optitemdto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OptionalItemPrintDto {

    private String unit;

    private int optionalItemAtr;

    private String optionalItemName;

    private Integer itemNo;

    private BigDecimal times;

    private Integer amount;

    private Integer time;

}

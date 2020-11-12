package nts.uk.ctx.at.request.dom.application.optional;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OptionalItemContent {

    private int optionalItemAtr;

    private String optionalItemName;

    private String unit;

    private BigDecimal times;

    private Integer amount;

    private Integer time;
}
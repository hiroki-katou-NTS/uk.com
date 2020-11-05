package nts.uk.ctx.at.request.app.find.application.optitem.optitemdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OptionalItemDto {
    /**
     * The optional item no.
     */
    private int optionalItemNo;

    /**
     * The optional item name.
     */
    private String optionalItemName;

    private String optionalItemUnit;

    /**
     * The unit.
     */
    private String unit;

    /**
     * The optional item atr.
     */
    private int optionalItemAtr;

    private CalcResultRangeDto calcResultRange;
}

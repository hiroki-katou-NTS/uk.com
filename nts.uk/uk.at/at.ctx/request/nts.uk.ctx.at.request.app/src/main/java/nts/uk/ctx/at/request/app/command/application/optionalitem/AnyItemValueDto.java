package nts.uk.ctx.at.request.app.command.application.optionalitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class AnyItemValueDto {
    private Integer itemNo;

    private BigDecimal times;

    private Integer amount;

    private Integer time;
}

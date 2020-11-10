package nts.uk.ctx.at.request.dom.application.optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OptionalItemPrintContent {
    private String code;

    private List<AnyItemValue> optionalItems;
}

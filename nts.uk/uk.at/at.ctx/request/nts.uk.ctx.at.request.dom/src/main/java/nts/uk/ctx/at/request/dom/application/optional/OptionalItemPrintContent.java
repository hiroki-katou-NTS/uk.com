package nts.uk.ctx.at.request.dom.application.optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OptionalItemPrintContent {
    private String code;
    private List<OptionalItemContent> optionalItemContents;
}

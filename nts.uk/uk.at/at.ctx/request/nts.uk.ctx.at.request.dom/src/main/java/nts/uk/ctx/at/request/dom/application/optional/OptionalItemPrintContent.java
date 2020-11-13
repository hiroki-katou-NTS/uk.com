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
    /**
     * 申請種類名
     */
    private String name;
    private List<OptionalItemContent> optionalItemContents;
}

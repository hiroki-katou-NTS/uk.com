package nts.uk.ctx.at.record.app.find.optitem;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OptionalItemRequestDto {
    private List<Integer> optionalItemNos;
}

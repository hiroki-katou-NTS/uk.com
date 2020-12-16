package nts.uk.ctx.at.request.ws.application.optionalitem;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OptionalItemRequestDto {
    private List<Integer> settingItemNoList;
}
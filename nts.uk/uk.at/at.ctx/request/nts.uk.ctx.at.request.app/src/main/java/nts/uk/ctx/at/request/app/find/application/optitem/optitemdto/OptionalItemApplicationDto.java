package nts.uk.ctx.at.request.app.find.application.optitem.optitemdto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class OptionalItemApplicationDto {


    private String name;

    private String code;

    private List<AnyItemValueDto> optionalItems;

    public OptionalItemApplicationDto(String code, List<AnyItemValueDto> optionalItems) {
        this.code = code;
        this.optionalItems = optionalItems;
    }

    public static OptionalItemApplicationDto fromDomain(OptionalItemApplication domain) {
        List<AnyItemValueDto> optionalItemList = domain.getOptionalItems().stream().map(item ->
                new AnyItemValueDto(item.getItemNo().v(),
                        item.getTimes().isPresent() ? item.getTimes().get().v() : null,
                        item.getAmount().isPresent() ? item.getAmount().get().v() : null,
                        item.getTime().isPresent() ? item.getTime().get().v() : null)
        ).collect(Collectors.toList());
        return new OptionalItemApplicationDto(domain.getCode().v(), optionalItemList);
    }
}

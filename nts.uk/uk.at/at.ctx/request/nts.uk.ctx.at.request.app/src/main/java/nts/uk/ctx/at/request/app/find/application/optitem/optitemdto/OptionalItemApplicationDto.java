package nts.uk.ctx.at.request.app.find.application.optitem.optitemdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.command.application.optionalitem.AnyItemValueDto;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Setter
@Getter
public class OptionalItemApplicationDto {

    private String code;

    private List<AnyItemValueDto> optionalItems;

    public static OptionalItemApplicationDto fromDomain(OptionalItemApplication domain) {
        List<AnyItemValueDto> optionalItemList = domain.getOptionalItems().stream().map(item ->
                new AnyItemValueDto(item.getItemNo().v(), item.getRowTimes(), item.getRowAmount(), item.getRowTime())
        ).collect(Collectors.toList());
        return new OptionalItemApplicationDto(domain.getCode().v(), optionalItemList);
    }

}

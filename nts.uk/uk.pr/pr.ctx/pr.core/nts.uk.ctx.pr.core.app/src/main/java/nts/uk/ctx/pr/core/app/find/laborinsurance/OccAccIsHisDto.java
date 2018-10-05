package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHis;

@AllArgsConstructor
@Value
public class OccAccIsHisDto {
    private String hisId;
    private Integer startYearMonth;
    private Integer endYearMonth;

    public static List<OccAccIsHisDto> fromDomain(OccAccIsHis domain) {
        List<OccAccIsHisDto> occAccIsHisDtoList = domain.getHistory().stream().map(item -> {
            return new OccAccIsHisDto(item.identifier(), Integer.parseInt(item.start().toString()), Integer.parseInt(item.end().toString()));
        }).collect(Collectors.toList());
        return occAccIsHisDtoList;
    }
}

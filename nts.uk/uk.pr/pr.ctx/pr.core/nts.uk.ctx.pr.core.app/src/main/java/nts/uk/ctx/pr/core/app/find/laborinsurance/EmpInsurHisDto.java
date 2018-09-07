package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;

@AllArgsConstructor
@Value
public class EmpInsurHisDto {
	
    private String hisId;
    private Integer startYearMonth;
    private Integer endYearMonth;
	
    public static List<EmpInsurHisDto> fromDomain(EmpInsurHis domain) {
        List<EmpInsurHisDto> empInsurHisDto = domain.getHistory().stream().map(item -> {
            return new EmpInsurHisDto(item.identifier(), Integer.parseInt(item.start().toString()), Integer.parseInt(item.end().toString()));
        }).collect(Collectors.toList());
        return empInsurHisDto;
    }
}

package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRate;

/**
* 労災保険料率
*/
@AllArgsConstructor
@Value
public class OccAccIsPrRateDto {

    private String hisId;
    private int occAccInsurBusNo;
    private int fracClass;
    private BigDecimal empConRatio;

    public static List<OccAccIsPrRateDto> fromDomain(OccAccIsPrRate domain) {
        List<OccAccIsPrRateDto> occAccIsHisDtoList = domain.getEachBusBurdenRatio().stream().map(item -> {
            return new OccAccIsPrRateDto(domain.getHisId(),item.getOccAccInsurBusNo(),item.getFracClass().value,item.getEmpConRatio().v());
        }).collect(Collectors.toList());
        return occAccIsHisDtoList;
    }
    
    

    
}

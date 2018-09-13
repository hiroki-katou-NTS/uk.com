package nts.uk.ctx.pr.core.app.find.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class AccInsurPreRateDto {
    private String hisId;
    private int occAccInsurBusNo;
    private String name;
    private int fracClass;
    private BigDecimal empConRatio;
    public static List<AccInsurPreRateDto> fromDomain(List<OccAccInsurBusDto> occAccInsurBusDtos,List<OccAccIsPrRateDto> occAccIsPrRateDtos) {
        List<AccInsurPreRateDto> accInsurPreRateDtos1 = new ArrayList<AccInsurPreRateDto>();
        for (OccAccInsurBusDto temp : occAccInsurBusDtos) {
            for (OccAccIsPrRateDto temp2: occAccIsPrRateDtos) {
                if(temp.getOccAccInsurBusNo() == temp2.getOccAccInsurBusNo())
                accInsurPreRateDtos1.add(new AccInsurPreRateDto(temp2.getHisId(),temp.getOccAccInsurBusNo(),temp.getName(),temp2.getFracClass(),temp2.getEmpConRatio()));
            }
        }
        return accInsurPreRateDtos1;
    }


}

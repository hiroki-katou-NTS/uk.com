package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class AccInsurPreRateDto {

    private String hisId;
    private int occAccInsurBusNo;
    private String name;
    private int fracClass;
    private BigDecimal empConRatio;
    private int useArt;

    public static List<AccInsurPreRateDto> fromDomain(List<OccAccInsurBusDto> occAccInsurBusDtos,List<OccAccIsPrRateDto> occAccIsPrRateDtos) {
        List<AccInsurPreRateDto> accInsurPreRateDtos1 = new ArrayList<AccInsurPreRateDto>();
        for (OccAccInsurBusDto temp : occAccInsurBusDtos) {
            for (OccAccIsPrRateDto temp2: occAccIsPrRateDtos) {
                if(temp.getOccAccInsurBusNo() == temp2.getOccAccInsurBusNo())
                	accInsurPreRateDtos1.add(new AccInsurPreRateDto(temp2.getHisId(),temp.getOccAccInsurBusNo()+1,temp.getName(),temp2.getFracClass(),temp2.getEmpConRatio(),temp.getToUse()));
            }
        }
        return accInsurPreRateDtos1;
    }


}

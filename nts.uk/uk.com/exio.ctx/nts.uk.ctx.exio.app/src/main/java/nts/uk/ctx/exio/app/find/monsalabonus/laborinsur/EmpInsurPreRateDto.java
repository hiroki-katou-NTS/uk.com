package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurPreRate;

@AllArgsConstructor
@Value
public class EmpInsurPreRateDto {
	
	    private String hisId;
	    private String empPreRateId;
	    private String indBdRatio;
	    private String empContrRatio;
	    private int perFracClass;
	    private int busiOwFracClass;
	    
	    public static EmpInsurPreRateDto fromDomain(EmpInsurPreRate domain) {
			return new EmpInsurPreRateDto(
					domain.getHisId(),
					domain.getEmpPreRateId(),
					domain.getIndBdRatio(),
					domain.getEmpContrRatio(),
					domain.getPerFracClass().value,
					domain.getBusiOwFracClass().value);
		}
}

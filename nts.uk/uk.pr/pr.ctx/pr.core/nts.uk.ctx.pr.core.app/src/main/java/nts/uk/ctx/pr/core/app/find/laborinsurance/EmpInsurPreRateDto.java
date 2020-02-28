package nts.uk.ctx.pr.core.app.find.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatio;

import java.math.BigDecimal;

@AllArgsConstructor
@Value
		
public class EmpInsurPreRateDto {

		private String hisId;
	    private int empPreRateId;
	    private BigDecimal indBdRatio;
	    private BigDecimal empContrRatio;
	    private int perFracClass;
	    private int busiOwFracClass;
	    
	    public static EmpInsurPreRateDto fromDomain(EmpInsurBusBurRatio domain) {
			return new EmpInsurPreRateDto(
					domain.getHisId(),
					domain.getEmpPreRateId().value,
					domain.getIndBdRatio().v(),
					domain.getEmpContrRatio().v(),
					domain.getPerFracClass().value,
					domain.getBusiOwFracClass().value);
		}
}

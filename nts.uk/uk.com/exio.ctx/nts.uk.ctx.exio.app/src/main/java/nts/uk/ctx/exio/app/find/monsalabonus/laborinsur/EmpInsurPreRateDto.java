package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatio;

import java.math.BigDecimal;

@AllArgsConstructor
@Value
public class EmpInsurPreRateDto {
	
	    private int empPreRateId;
	    private BigDecimal indBdRatio;
	    private BigDecimal empContrRatio;
	    private int perFracClass;
	    private int busiOwFracClass;
	    
	    public static EmpInsurPreRateDto fromDomain(EmpInsurBusBurRatio domain) {
			return new EmpInsurPreRateDto(
					domain.getEmpPreRateId().value,
					domain.getIndBdRatio().v(),
					domain.getEmpContrRatio().v(),
					domain.getPerFracClass().value,
					domain.getBusiOwFracClass().value);
		}
}

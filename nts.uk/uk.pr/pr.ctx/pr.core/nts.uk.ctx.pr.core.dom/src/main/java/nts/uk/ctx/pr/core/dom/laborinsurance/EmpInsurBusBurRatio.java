package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
/*
*雇用保険各事業負担率
* */
@Getter
@Setter
public class EmpInsurBusBurRatio {
	
	private String hisId;
    /**
     *
     */
    private EmpInsurRateId empPreRateId;

    /**
     * 個人負担率
     */
    private InsuranceRate indBdRatio;

    /**
     * 事業主負担率
     */
    private InsuranceRate empContrRatio;

    /**
     * 個人端数区分
     */
    private InsuPremiumFractionClassification perFracClass;

    /**
     * 事業主端数区分
     */
    private InsuPremiumFractionClassification busiOwFracClass;

	public EmpInsurBusBurRatio(String hisId, int empPreRateId, BigDecimal indBdRatio, BigDecimal empContrRatio,
			int perFracClass, int busiOwFracClass) {
		super();
		this.hisId = hisId;
		this.empPreRateId = EnumAdaptor.valueOf(empPreRateId, EmpInsurRateId.class);
		this.indBdRatio = new InsuranceRate(indBdRatio);
		this.empContrRatio = new InsuranceRate(empContrRatio);
		this.perFracClass = EnumAdaptor.valueOf(perFracClass, InsuPremiumFractionClassification.class);
		this.busiOwFracClass = EnumAdaptor.valueOf(busiOwFracClass, InsuPremiumFractionClassification.class);
	}
    
    
}

package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 雇用別変形労働集計設定
 */
@Getter
public class EmpDeforLaborMonthActCalSet extends DeforWorkTimeAggrSet {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 雇用コード */
	private EmploymentCode employmentCode;
	
	private EmpDeforLaborMonthActCalSet (EmploymentCode employmentCode) {
		super();
		
		this.employmentCode = employmentCode;
	}
	
	public static EmpDeforLaborMonthActCalSet of (EmploymentCode employmentCode, String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet,
			DeforLaborCalSetting deforLaborCalSetting,
			DeforLaborSettlementPeriod settlementPeriod) {
		
		val domain = new EmpDeforLaborMonthActCalSet(employmentCode);

		domain.set(comId, aggregateTimeSet, excessOutsideTimeSet, 
				deforLaborCalSetting, settlementPeriod);
		
		return domain;
	}

}

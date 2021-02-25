package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 雇用別通常勤務集計設定
 */
@Getter
public class EmpRegulaMonthActCalSet extends RegularWorkTimeAggrSet {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 雇用コード */
	private EmploymentCode employmentCode;
	
	private EmpRegulaMonthActCalSet (EmploymentCode employmentCode) {
		super();
		
		this.employmentCode = employmentCode;
	}
	
	public static EmpRegulaMonthActCalSet of (EmploymentCode employmentCode, String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet) {
		
		val domain = new EmpRegulaMonthActCalSet(employmentCode);

		domain.set(comId, aggregateTimeSet, excessOutsideTimeSet);
		
		return domain;
	}

}
